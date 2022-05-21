package Server;

import Client.Command;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;
import Customer.Car;
import Customer.CarType;
import Customer.Customer;

import Infrastructure.ParkingLot;
import Infrastructure.ParkingLotModule;
import Infrastructure.ParkingOffice;
import Infrastructure.ParkingOfficeModule;
import Transaction.ParkingTransaction;
import Permit.ParkingPermit;
import Transaction.Money;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class Server {
  static {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format", "%1$tc %4$-7s (%2$s) %5$s %6$s%n");
  }

  private static final Logger logger = Logger.getLogger(Server.class.getName());
  
  private final int PORT = 7777;
  private static ParkingOffice parkingOffice;
  private static ParkingLot parkingLot;
 
  public static void main(String[] args) throws Exception {
        Injector injectorParkingLot = Guice.createInjector(new ParkingLotModule());
        parkingLot = injectorParkingLot.getInstance(ParkingLot.class);  
        Injector injectorParkingOffice = Guice.createInjector(new ParkingOfficeModule());
        parkingOffice = injectorParkingOffice.getInstance(ParkingOffice.class);    
        new Server().startServer();  

  }
  
  public void startServer() throws IOException, ClassNotFoundException {

    //logger.info("Starting server: " + InetAddress.getLocalHost().getHostAddress());
    try (        
      ServerSocket serverSocket = new ServerSocket(PORT)) {
      serverSocket.setReuseAddress(true);
      while (true) {
        Socket client = serverSocket.accept();
        // Displaying that new client is connected to server
        System.out.println("New client connected"
                                   + client.getInetAddress()
                                         .getHostAddress());
        // create a new thread object
        ClientHandler clientSock = new ClientHandler(client);
  
        // This thread will handle the client separately
        new Thread(clientSock).start();
      }
    }
  }

  // ClientHandler class implements runnable for multithreading
    public static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private ObjectInputStream serverInputStream;
        private ObjectOutputStream serverOutputStream; 


        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        public void run() 
        {

            try {
                //create the server input and output streams
                serverInputStream = new ObjectInputStream(clientSocket.getInputStream());
                serverOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                
                try {
                    //first deserialize and read the command 
                    Object object = serverInputStream.readObject();
                    Command command = (Command)object;
                    
                    //next read the command arguments 
                    Map<String,String> data = (Map<String,String>)serverInputStream.readObject();

                    //switch on the command 
                    switch(command.name()){
                        case "CUSTOMER":
                           synchronized(parkingOffice)
                           {
                               serverOutputStream.writeObject("Registered customers = " + parkingOffice.getCustomers());
                           } 
                            break;  
                        case "CAR":            
                            serverOutputStream.writeObject(processCarCommand(data));
                            break;
                        case "PARK":
                            serverOutputStream.writeObject(processParkCommand(data));
                            break;    
                        case "CHARGES":
                            serverOutputStream.writeObject(processChargesCommand(data));
                        default:
                    }
                      
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
                serverOutputStream.close();
                serverInputStream.close();
                clientSocket.close();        
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        private String processCarCommand(Map<String,String> data) throws Exception{
            List<ParkingPermit> permits;
            synchronized(parkingOffice)
            {
                //first unpack the data on the car, CarType, LicensePlate, Owner
                String[] dataArray = data.values().toArray(new String[3]);
                CarType carType = CarType.valueOf(dataArray[0]);
                String licensePlate = dataArray[1];
                //then see if customer exists, if not register them

                Customer customer = parkingOffice.getCustomerByCustomerName(dataArray[2]);
                if (customer == null){
                    customer = new Customer(dataArray[2]);
                    parkingOffice.register(customer);
                } 
                //register the car and write response back to client
                Car car = new Car(carType, licensePlate, customer);
                parkingOffice.register(car);   
                permits = parkingOffice.getPermits();
            }
            return "Registered vehicles = " + permits;
        }
        
        private String processParkCommand(Map<String,String> data) throws ParseException{
            String chargedAmount;
            ParkingTransaction transaction;
            synchronized(parkingOffice)
            {
                synchronized(parkingLot)
                {
                    //first unpack the data, just parking permit id
                    String[] dataArrayPark = data.values().toArray(new String[1]);
                    String id = dataArrayPark[0];
                    //get the permit from the permit list
                    ParkingPermit permit = parkingOffice.getPermits().get(Integer.parseInt(id));
                    //date is now, that is when the person is parking
                    Date date=java.util.Calendar.getInstance().getTime();
                    //create Parking Transaction
                    transaction = parkingOffice.park(date, permit, parkingLot);
                    //write the parking result back to the client
                    chargedAmount = String.valueOf(transaction.getChargedAmount().getAmount());                            
                }
            }
            return "Parking Transaction, Id = " 
                    + transaction.getParkingPermit().getPermitId()
                    + ", $" + chargedAmount;
        }
        private String processChargesCommand(Map<String,String> data){
            //unpack the data, just the customer name
            String[] dataArrayCharges = data.values().toArray(new String[1]);
            String customerName = dataArrayCharges[0];   
            String totalCharges = "";
            synchronized(parkingOffice)
            {
                //get the Customer object
                Customer chargedCustomer = parkingOffice.getCustomerByCustomerName(customerName);
                //get list of charges by customer
                List<Money> charges = parkingOffice.getParkingCharges(chargedCustomer);
                //write the the charges back to the client
                for(Money money: charges){
                    totalCharges = totalCharges + " " + money.toString();
                }
            }
            return "Parking Charges, customer = " + customerName + ", amount = $" + totalCharges;
        }


    }

}
