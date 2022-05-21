package ClientJSON;

import Client.Command;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



public class ClientJSON {
  
  public static final String[][] COMMANDS = new String[][] {
    {"View Customers", "CUSTOMER", "Name"},
    {"Register Vehicle",  "CAR",      "CarType", "LicensePlate", "Owner"},
    {"Park Car",     "PARK",     "Permit Id"},
    {"Get Charges","CHARGES",  "Customer"},
  };
  
  private static final int PORT = 7777;
  private static final String SERVER = "localhost";
  
  private ClientJSON() {}

  public static List<String> runCommand(String command, Map<String, String> data)
      throws IOException, ClassNotFoundException, Exception {
      //get the host and socket link
      InetAddress host = InetAddress.getByName(SERVER);     
      ArrayList<String> response = new ArrayList<>();
      //get the field names from the command
      List<String> fieldNames = new ArrayList<>();
      for(String fieldName:  data.keySet()){
          fieldNames.add(fieldName);
      }
      try (Socket link = new Socket(host, PORT);
        Scanner scanner = new Scanner(link.getInputStream());) {
            //create a commandObject we can serialize to the server
            Command commandObject = new Command(command,command,fieldNames);

            //define a clientOutputStream to the server
            //ObjectOutputStream clientOutputStream = new ObjectOutputStream(link.getOutputStream());
            PrintWriter output = new PrintWriter(link.getOutputStream());

            //write the command to the Server,write the data to the server
            ObjectMapper objectMapper = new ObjectMapper();

            ObjectNode node = objectMapper.createObjectNode();
            node.put("action", "customer");
            node.putPOJO("details", command); 
            node.putPOJO("data", data);
            String json = objectMapper.writeValueAsString(node);


            System.out.println("JSON to Server: " + json);
            System.out.println();
            
            // Send over the wire
            output.println(json);
            output.flush();

            //return the response from the server  
                  // Wait for the server response
            String line = scanner.nextLine();
            response.add(line); 
      }
    return response;
  }
  
  public static Map<String, Command> commands() {
    Map<String, Command> commands = new HashMap<>();
    for (String[] description : COMMANDS) {
      commands.put(description[1], new Command(description[0], description[1],
          Arrays.asList(description).subList(2, description.length)));
    }
    return commands;
  }

   
  public static void main(String[] args) throws IOException, ClassNotFoundException, Exception {
    
    if (args.length == 0 || args[0].equals("LIST")) {
      System.out.println();
      for (String[] description : COMMANDS) {
        System.out.format("%s: %s ", description[0], description[1]);
        for (int i = 2; i < description.length; ++i) {
          System.out.format("%s=value ", description[i].replaceAll(" ", "").toLowerCase());
        }
        System.out.println();
      }
      return;
    }
    
    Command command = commands().get(args[0]);
    if (command == null) {
      System.out.println("Unrecognised command: " + args[0]);
      System.out.print("Known commands: ");
      String comma = "";
      for (String[] description : COMMANDS) {
        System.out.print(comma + description[1]);
        comma = ", ";
      }
      System.out.println();
      return;
    } 
    Map<String, String> values = new LinkedHashMap<>();
    for (String label : command.fieldNames()) {
      for (int i = 0; i < args.length; ++i) {
        if (args[i].startsWith(label.replaceAll(" ", "").toLowerCase())) {
          values.put(label, args[i].replaceAll(".*=", ""));
          break;
        }
      }
    }
    for (String output : ClientJSON.runCommand(args[0], values)) {
      System.out.println(output);
    }
  }
  
//   public static String convertToJson(Command command, Map<String, String> data) 
//      throws JsonProcessingException, IOException, Exception {
//        return getCommandJson(data.get("Name"));
//  }
   
//  private static String getCommandJson(String name) throws JsonProcessingException{
//
//        List<String> args = new ArrayList<>();
//        args.add(name);
//        Command command = new Command("View Customers", "CUSTOMER", args);
//
//        ObjectMapper mapper = new ObjectMapper(); 
//        ObjectNode node = mapper.createObjectNode();
//        node.put("action", "command");
//        node.putPOJO("details", command); 
//        return mapper.writeValueAsString(node);
//  }
}
