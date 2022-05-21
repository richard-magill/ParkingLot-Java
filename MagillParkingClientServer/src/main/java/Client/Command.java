package Client;


import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Command implements Serializable{
  private final String name;
  private final String command;
  private final List<String> fieldNames;
  

  public Command(String name, String command,List<String> fieldNames) {
    this.name = name;
    this.command = command;
    this.fieldNames = Collections.unmodifiableList(fieldNames);
  }
  
  public String name() {
    return name;
  }
  
  public List<String> fieldNames() {
    return fieldNames;
  }

  public String execute(Map<String, String> data) throws ClassNotFoundException, Exception {
    try {
      return Client.runCommand(command, data).toString();
    } catch (IOException e) {
      e.printStackTrace();
      return e.getMessage();
    }
  }
}
