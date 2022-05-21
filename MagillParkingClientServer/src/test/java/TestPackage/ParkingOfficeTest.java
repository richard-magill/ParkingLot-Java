
package TestPackage;


import Customer.Car;
import Customer.CarType;
import Customer.Customer;
import Infrastructure.ParkingLot;
import Infrastructure.ParkingLotModule;
import Infrastructure.ParkingOffice;
import Infrastructure.ParkingOfficeModule;
import Permit.ParkingPermit;
import Transaction.Money;
import Transaction.ParkingTransaction;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParkingOfficeTest {

    private ParkingOffice fakeOffice;
    private  ParkingLot fakeLot;
    
    private static final String BOB = "Bob";
    private static final String LICENSE = "12345";
    private static final Customer fakeCustomer = new Customer(BOB);
    private static final Car fakeCar = new Car(CarType.SUV, LICENSE,  fakeCustomer);
    
    private static final String TOM = "Tom";
    private static final String LICENSE2 = "67890";
    private static final Customer fakeCustomer2 = new Customer(TOM);
    private static final Car fakeCar2 = new Car(CarType.SUV, LICENSE2,  fakeCustomer2);
    
    private static final String JOE = "Joe";
    private static final String LICENSE3 = "44444";
    private static final Customer fakeCustomer3 = new Customer(JOE);
    private static final Car fakeCar3 = new Car(CarType.SUV, LICENSE3,  fakeCustomer3);
    
    @Before
    public void createFakeOffice(){
        com.google.inject.Module testModule = Modules.override(new ParkingOfficeModule())
            .with(new AbstractModule(){             

            @Override
            protected void configure() {
                bind(String.class).toInstance("Fake Parking Office");
            }
        });
        Injector injector = Guice.createInjector(testModule);
        fakeOffice = injector.getInstance(ParkingOffice.class);      
    }
    
    @Before
    public void createFakeLot(){
        com.google.inject.Module testModule = Modules.override(new ParkingLotModule())
            .with(new AbstractModule(){             

            @Override
            protected void configure() {
                bind(String.class).toInstance("Fake Parking Lot");
                bind(Long.class).toInstance(20L);
            }
        });
        Injector injector = Guice.createInjector(testModule);
        fakeLot = injector.getInstance(ParkingLot.class);      
    }
    

    
    @Test
    public void testRegister_Customer() {
        String expResult = BOB;
        String result = fakeOffice.register(fakeCustomer).toString();
        assertEquals(expResult, result);
        System.out.println("test registerCustomer from ParkingOffice class, " +  expResult  + " = " + result);
    }
    
    @Test
    public void test_getCustomers() {
        List<Customer> expResult = new ArrayList<>();
        fakeOffice.register(fakeCustomer);
        expResult.add(fakeCustomer);
        fakeOffice.register(fakeCustomer2);
        expResult.add(fakeCustomer2);
        List<Customer> result = fakeOffice.getCustomers();
        assertEquals(expResult, result);
        System.out.println("test getCustomers from ParkingOffice class, " +  expResult  + " = " + result);
    }
    
    @Test
    public void test_getPermits() throws Exception {
 
        List<ParkingPermit> expResult = new ArrayList<>();
        ParkingPermit permit =  fakeOffice.register(fakeCar);
        expResult.add(permit);
        ParkingPermit permit2 =  fakeOffice.register(fakeCar2);
        expResult.add(permit2);
        List<ParkingPermit> result = fakeOffice.getPermits();
        assertEquals(expResult, result);
        System.out.println("test getPermits from ParkingOffice class, " +  expResult  + " = " + result);
    }
    
    @Test
    public void testRegister_Car() throws Exception {
        String expResult = "ParkingPermitId=0 Owner=Bob CarType=SUV LicensePlate=12345";
        String result = fakeOffice.register(fakeCar).toString();
        assertEquals(expResult, result);
        System.out.println("test registerCar from ParkingOffice class, " +  expResult  + " = " + result);
    }
 

    
    @Test
    public void testGetCustomerByCustomerName(){
        Customer expResult = fakeCustomer;
        fakeOffice.register(fakeCustomer);
        Customer result = fakeOffice.getCustomerByCustomerName(BOB);
        assertEquals(expResult, result);
        System.out.println("test getCustomerByCustomerName from ParkingOffice class, " +  expResult.toString()  + " = " + result.toString()); 
    }
    
     @Test
    public void testPark() throws Exception {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aFriday;
        try {
            aFriday= ft.parse("2021-04-16 12:00");

            //parked in fake lot, charged 20 dollars, no discount. this returns the transaction and also adds to the transaction list
            ParkingPermit permit = fakeOffice.register(fakeCar3);
            //use the park() method in the parking Office, this should create transaction
            ParkingTransaction parkingTransaction = fakeOffice.park(aFriday, permit, fakeLot);
            String result = parkingTransaction.getChargedAmount().getAmount() + " " + parkingTransaction.getParkingPermit().getCar();


            String expResult = "20 Car [CarType =SUV, license=44444, customer=Joe]";
            assertEquals(expResult, result);
            System.out.println("test park() from ParkingOffice class, " +  result.toString() + " = " + expResult );

        } catch (ParseException ex) {
            Logger.getLogger(ParkingOfficeTest.class.getName()).log(Level.SEVERE, null, ex);
        }    

        
    }
    
    @Test
    public void testGetParkingCharges() throws Exception{
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aFriday;
        try {
            aFriday= ft.parse("2021-04-16 12:00");
            List<Money> expResult = new ArrayList<Money>();
            Money expCharge = new Money("dollars", 20L);
            expResult.add(expCharge);
            ParkingPermit permit = fakeOffice.register(fakeCar);
            //use the park() method in the parking Office, this should create transaction
            ParkingTransaction parkingTransaction = fakeOffice.park(aFriday, permit, fakeLot);
            List<Money> result = fakeOffice.getParkingCharges(fakeCar.getOwner());

            assertEquals(expResult.toString(), result.toString());
            System.out.println("test getParkingCharges from ParkingOffice class, " +  expResult.toString()  + " = " + result.toString()); 
        } catch (ParseException ex) {
            Logger.getLogger(ParkingOfficeTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
