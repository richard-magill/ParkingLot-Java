package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class Client {
  
  public static final String[][] COMMANDS = new String[][] {
    {"View Customers", "CUSTOMER"},
    {"Register Vehicle",  "CAR",      "CarType", "LicensePlate", "Owner"},
    {"Park Car",     "PARK",     "Permit Id"},
    {"Get Charges","CHARGES",  "Customer"},
  };
  
  private static final int PORT = 7777;
  private static final String SERVER = "localhost";
  
  public Client() {}

  protected static List<String> runCommand(String command, Map<String, String> data)
      throws IOException, ClassNotFoundException, Exception {
      //get the host and socket link
      InetAddress host = InetAddress.getByName(SERVER);     
      Socket link = new Socket(host, PORT); 
      
      //get the field names from the command
      List<String> fieldNames = new ArrayList<>();
      for(String fieldName:  data.keySet()){
          fieldNames.add(fieldName);
      }
      
      //create a commandObject we can serialize to the server
      Command commandObject = new Command(command,command,fieldNames);
  
      //define a clientOutputStream to the server
      ObjectOutputStream clientOutputStream = new ObjectOutputStream(link.getOutputStream());

      //write the command to the Server,write the data to the server
      clientOutputStream.writeObject(commandObject);
      clientOutputStream.writeObject(data);
      
      //create a clientInputStream from the server to get the response
      ArrayList<String> response = new ArrayList<>();
      ObjectInputStream clientInputStream = new ObjectInputStream(link.getInputStream());
      response.add(clientInputStream.readObject().toString());
      
      //close the streams
      clientOutputStream.close();
      clientInputStream.close();
      
      //return the response from the server  
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
    for (String output : Client.runCommand(args[0], values)) {
      System.out.println(output);
    }
  }


}
