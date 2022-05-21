package ServerJSON;

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
import Infrastructure.ParkingOffice;
import Transaction.ParkingTransaction;
import Permit.ParkingPermit;
import Transaction.Money;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class ServerJSON {
  static {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format", "%1$tc %4$-7s (%2$s) %5$s %6$s%n");
  }

  private static final Logger logger = Logger.getLogger(ServerJSON.class.getName());
  private static final ParkingOffice parkingOffice = new ParkingOffice("ParkingOffice1");
  private static final ParkingLot onlyLot = new ParkingLot("The Only Lot", 20L);
  private final int PORT = 7777;
  
  private final ParkingServiceJSON service;
  
  public ServerJSON(ParkingServiceJSON service) {
    this.service = service;
  }
  
  public static void main(String[] args) throws Exception {

    ParkingServiceJSON service = new ParkingServiceJSON(parkingOffice);
    new ServerJSON(service).startServer();
  }
  
  public void startServer() throws IOException, ClassNotFoundException {
    logger.info("Starting server: " + InetAddress.getLocalHost().getHostAddress());
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
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
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private ObjectInputStream serverInputStream;
        private ObjectOutputStream serverOutputStream; 

        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        public void run() 
        {

        }
        

    }

}
