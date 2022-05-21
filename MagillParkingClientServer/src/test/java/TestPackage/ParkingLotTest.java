

package TestPackage;

import Customer.CarType;
import Infrastructure.ParkingLot;
import Infrastructure.ParkingLotModule;
import Transaction.Money;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;


public class ParkingLotTest {
    ParkingLot lot;
    
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
        lot = injector.getInstance(ParkingLot.class);      
    }
   
    @Test
    public void testGetWeekendSUVRateFromFeeStrategy() {
        //base is 20, weekend is $5 off, and SUV gets no further discount
        // so 15 is expected
        String expResult = "rate = 15";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aSunday;
        try {
            aSunday= ft.parse("2021-04-11 12:00");
            Money thisRate = lot.getRate(CarType.SUV, aSunday);
            String result = "rate = " + thisRate.getAmount();
            assertEquals(expResult, result);
            System.out.println("test GetWeekendSUVRateFromFeeStrategy from ParkingLot class, " +  expResult  + " = " + result);
        } catch (ParseException ex) {
            Logger.getLogger(ParkingLotTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    
    @Test
    public void testGetWeekendCompactRateFromFeeStrategy() {
        //base is 20, weekend is $5 off, and Compact gets additional $3 off
        // so 12 is expected
        String expResult = "rate = 12";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aSunday;
        try {
            aSunday= ft.parse("2021-04-11 12:00:00");
            Money thisRate = lot.getRate(CarType.COMPACT, aSunday);
            String result = "rate = " + thisRate.getAmount();
            assertEquals(expResult, result);
            System.out.println("test GetWeekendCompactRateFromFeeStrategy from ParkingLot class, " +  expResult  + " = " + result);        
        } catch (ParseException ex) {
            Logger.getLogger(ParkingLotTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @Test
    public void testGetPremiumSUVRateFromFeeStrategy() {
        //base is 20, and SUV gets no further discount
        // so 20 is expected
        String expResult = "rate = 20";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aMonday;
        try {
            aMonday= ft.parse("2021-04-12 12:00");
            Money thisRate = lot.getRate(CarType.SUV, aMonday);
            String result = "rate = " + thisRate.getAmount();
            assertEquals(expResult, result);
            System.out.println("test GetPremiumSUVRateFromFeeStrategy from ParkingLot class, " +  expResult  + " = " + result);          
        } catch (ParseException ex) {
            Logger.getLogger(ParkingLotTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    @Test
    public void testGetPremiumCompactRateFromFeeStrategy() throws ParseException {
        //base is 20, and Compact gets additional $3 off
        // so 17 is expected
        String expResult = "rate = 17";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aMonday;
        try {
            aMonday= ft.parse("2021-04-12 12:00");
            Money thisRate = lot.getRate(CarType.COMPACT, aMonday);
            String result = "rate = " + thisRate.getAmount();
            assertEquals(expResult, result);
            System.out.println("test GetPremiumCompactRateFromFeeStrategy from ParkingLot class, " +  expResult  + " = " + result);          
        } catch (ParseException ex) {
            Logger.getLogger(ParkingLotTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     @Test
    public void testGetWeeknightSUVRateFromFeeStrategy() {
        //base is 20, you get 3 off on weeknight, and SUV gets no further discount
        // so 17 is expected
        String expResult = "rate = 17";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aMondayNight;
        try {
            aMondayNight= ft.parse("2021-04-12 20:00");
            Money thisRate = lot.getRate(CarType.SUV, aMondayNight);
            String result = "rate = " + thisRate.getAmount();
            assertEquals(expResult, result);
            System.out.println("test GetWeeknightSUVRateFromFeeStrategy from ParkingLot class, " +  expResult  + " = " + result);          
        } catch (ParseException ex) {
            Logger.getLogger(ParkingLotTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    @Test
    public void testGetWeeknightCompactRateFromFeeStrategy() throws ParseException {
        //base is 20, you get 3 off on weeknight, and Compact gets additional $3 off
        // so 14 is expected
        String expResult = "rate = 14";
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aMondayNight;
        try {
            aMondayNight= ft.parse("2021-04-12 20:00");
            Money thisRate = lot.getRate(CarType.COMPACT, aMondayNight);
            String result = "rate = " + thisRate.getAmount();
            assertEquals(expResult, result);
            System.out.println("test GetWeeknightCompactRateFromFeeStrategy from ParkingLot class, " +  expResult  + " = " + result);          
        } catch (ParseException ex) {
            Logger.getLogger(ParkingLotTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
