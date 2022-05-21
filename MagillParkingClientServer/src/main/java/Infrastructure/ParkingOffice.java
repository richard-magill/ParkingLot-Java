package Infrastructure;

import Transaction.Money;
import Permit.ParkingPermit;
import Customer.Customer;
import Customer.Car;
import Transaction.ParkingTransaction;
import com.google.inject.Inject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParkingOffice {

  private final String name;
  private final List<Customer> customers = new ArrayList<>();
  private final List<String> customerNames = new ArrayList<>();
  private final List<ParkingPermit> permits = new ArrayList<>();
  private final List<ParkingTransaction> transactions = new ArrayList<>();

  @Inject
  public ParkingOffice(String name) {
    this.name = name;
  }

  public String getParkingOfficeName() {
    return name;
  }

  //registers a customer to the list<customer>, prevents duplicates
  public Customer register(Customer customer) {
    //List<String> customerNames = new ArrayList<>();
    for(Customer c: customers){
        customerNames.add(c.getName());
    }
    if (!customerNames.contains(customer.getName())) {
      customers.add(customer);
    }
    return customer;
  }
  
  //return list of all permits
  public List<ParkingPermit> getPermits()
  {
      return permits;
  }
  
  //return list of all customers
  public List<Customer> getCustomers()
  {
      return customers;
  }
  
  //return a specific Customer from the list<customer>
  public Customer getCustomerByCustomerName(String customerName)
  {
      for (Customer customer : customers) {
        if (customer.getName().toLowerCase().equals(customerName.toLowerCase())) {
            return customer;
        }     
    }
      // if nothing is found
     return null;
  }

  
  public ParkingPermit register(Car car) throws Exception {
    for (ParkingPermit permit : permits) {
      if (permit.getCar().getLicensePlate().equals(car.getLicensePlate())) {
        throw new Exception("license plate already registered");
      }
    } 
    if (!customerNames.contains(car.getOwner().getName())) {
      register(car.getOwner());
    }
    ParkingPermit permit = new ParkingPermit(String.valueOf(permits.size()), car);
    permits.add(permit);
    return permit;
  }

  
  public ParkingTransaction park(Date date, ParkingPermit parkingPermit, ParkingLot parkingLot) throws ParseException{
        // get charged amount from parking lot, which uses a FeeStrategyFactory to determine rate
        Money chargedAmount = parkingLot.getRate(parkingPermit.getCar().getType(), date);
        
        //create the transaction and add it to the transaction list
        ParkingTransaction parkingTransaction = new ParkingTransaction(date,parkingPermit, parkingLot,chargedAmount);
        transactions.add(parkingTransaction);
        return parkingTransaction;
  }

  

  
  public List<Money> getParkingCharges(Customer customer) {
    List<Money> chargeList = new ArrayList<Money>();
    for (ParkingTransaction transaction: transactions ){
        if (transaction.getParkingPermit().getCar().getOwner() == customer){
            chargeList.add(transaction.getChargedAmount());
        }
    }
    return chargeList;
  }




}

