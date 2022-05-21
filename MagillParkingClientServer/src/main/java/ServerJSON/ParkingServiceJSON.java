package ServerJSON;


import Customer.Customer;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import Infrastructure.ParkingOffice;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

public class ParkingServiceJSON {
  protected final ParkingOffice parkingOffice;

  public ParkingServiceJSON(ParkingOffice parkingOffice) {
    this.parkingOffice = parkingOffice;
  }

  protected String handleInput(InputStream in) throws IOException {
    @SuppressWarnings("resource")
    // The scanner and input stream will be closed when we disconnect
    Scanner scanner = new Scanner(in);
    String json = scanner.nextLine();
    System.out.println();
    System.out.println("JSON from Client: " + json);
    return performCommand(json);
  }
  
      private String performCommand(String json) throws IOException {
    
    ObjectMapper mapper = new ObjectMapper();

    // Detect what "action" was sent over the wire
    JsonNode jsonNode = mapper.readTree(json);
    String action = jsonNode.get("action").asText().toLowerCase();
    
    ObjectNode response = mapper.createObjectNode();
    switch (action) {
      case "command":
        Customer customer = mapper.readValue(jsonNode.get("details").toString(), Customer.class);
        Customer registeredCustomer = parkingOffice.register(customer);
        response.put("success", true);
        response.put("customer_name", registeredCustomer.getName());
        return mapper.writeValueAsString(response);
      case "car":
        // deserialize car….
      case "park":
        // perform work on the “park” action
      case "charges":
        // perform work on the “charges” action
    }
    
    throw new IllegalArgumentException("Invalid action: " + action);
  }


}
