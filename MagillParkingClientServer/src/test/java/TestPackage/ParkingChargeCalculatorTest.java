
package TestPackage;

import Customer.CarType;
import Fee.ParkingChargeCalculator;
import Fee.ParkingChargeCalculatorFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ParkingChargeCalculatorTest {
    //This test tests both the ParkingChargeCalculatorFactory class and 
    //Decorator pattern based on the ParkingChargeCalculator class
    @Test
    public void testcalculateFee_BasicParkingCharge() throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aMonday;
        try {
            Long baseFee = 20L;           
            //monday at noon means no WeeknightParkingChargeDecorator or WeekendParkingChargeDecorator
            //is applied.
            aMonday= ft.parse("2021-04-12 12:00");
            //a SUV means no CompactParkingChargeDecorator is applied
            ParkingChargeCalculatorFactory factory = new ParkingChargeCalculatorFactory(CarType.SUV, aMonday);
            ParkingChargeCalculator parkingChargeCalculator = factory.getParkingChargeCalculator();
            //so only the BasicParkingChargeCalculator is returned, no discount
            Long expResult = 20L;
            Long result = parkingChargeCalculator.calculateFee(baseFee);
            assertEquals(expResult, result);
            System.out.println("test calculateFee_BasicParkingChargeDecorator from parkingChargeCalculatorTest, " +  expResult  + " = " + result);          
        } catch (ParseException ex) {
            Logger.getLogger(ParkingChargeCalculatorTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    @Test
    public void testcalculateFee_CompactParkingCharge() throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aMonday;
        try {
            Long baseFee = 20L;           
            //monday at noon means no WeeknightParkingChargeDecorator or WeekendParkingChargeDecorator
            //is applied.
            aMonday= ft.parse("2021-04-12 12:00");
            //a COMPACT means CompactParkingChargeDecorator is applied, so $3 off
            ParkingChargeCalculatorFactory factory = new ParkingChargeCalculatorFactory(CarType.COMPACT, aMonday);
            ParkingChargeCalculator parkingChargeCalculator = factory.getParkingChargeCalculator();
            //so only the Compact decorated ParkingChargeCalculator is returned, $3 discount
            Long expResult = 17L;
            Long result = parkingChargeCalculator.calculateFee(baseFee);
            assertEquals(expResult, result);
            System.out.println("test calculateFee_CompactParkingChargeDecorator from parkingChargeCalculatorTest, " +  expResult  + " = " + result);          
        } catch (ParseException ex) {
            Logger.getLogger(ParkingChargeCalculatorTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    @Test
    public void testcalculateFee_WeeknightParkingCharge() throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aMondayNight;
        try {
            Long baseFee = 20L;           
            //monday at 7 pm  means  WeeknightParkingChargeDecorator will be applied
            aMondayNight= ft.parse("2021-04-12 19:00");
            //a SUV means no CompactParkingChargeDecorator is applied
            ParkingChargeCalculatorFactory factory = new ParkingChargeCalculatorFactory(CarType.SUV, aMondayNight);
            ParkingChargeCalculator parkingChargeCalculator = factory.getParkingChargeCalculator();
            //so only the Weeknight decorated ParkingChargeCalculator is returned, $3 discount
            Long expResult = 17L;
            Long result = parkingChargeCalculator.calculateFee(baseFee);
            assertEquals(expResult, result);
            System.out.println("test calculateFee_WeeknightParkingChargeDecorator from parkingChargeCalculatorTest, " +  expResult  + " = " + result);          
        } catch (ParseException ex) {
            Logger.getLogger(ParkingChargeCalculatorTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    @Test
    public void testcalculateFee_WeekendParkingCharge() throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aSunday;
        try {
            Long baseFee = 20L;           
            //sunday  means  WeekendParkingChargeDecorator will be applied, $5 discount
            aSunday= ft.parse("2021-04-11 12:00");
            //a SUV means no CompactParkingChargeDecorator is applied
            ParkingChargeCalculatorFactory factory = new ParkingChargeCalculatorFactory(CarType.SUV, aSunday);
            ParkingChargeCalculator parkingChargeCalculator = factory.getParkingChargeCalculator();
            //so only the Weekend decorated ParkingChargeCalculator is returned, $5 discount
            Long expResult = 15L;
            Long result = parkingChargeCalculator.calculateFee(baseFee);
            assertEquals(expResult, result);
            System.out.println("test calculateFee_WeekendParkingChargeDecorator from parkingChargeCalculatorTest, " +  expResult  + " = " + result);          
        } catch (ParseException ex) {
            Logger.getLogger(ParkingChargeCalculatorTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    
    @Test
    public void testcalculateFee_ChainedDecoratorParkingCharges() throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date aSunday;
        try {
            Long baseFee = 20L;           
            //sunday  means  WeekendParkingChargeDecorator will be applied, $5 discount
            aSunday= ft.parse("2021-04-11 12:00");
            //a SUV means no CompactParkingChargeDecorator is applied
            ParkingChargeCalculatorFactory factory = new ParkingChargeCalculatorFactory(CarType.COMPACT, aSunday);
            ParkingChargeCalculator parkingChargeCalculator = factory.getParkingChargeCalculator();
            //so both the Compact and the Weekend decorated ParkingChargeCalculator is returned, $8 discount
            Long expResult = 12L;
            Long result = parkingChargeCalculator.calculateFee(baseFee);
            assertEquals(expResult, result);
            System.out.println("test calculateFee_ChainedParkingChargeDecorators from parkingChargeCalculatorTest, " +  expResult  + " = " + result);          
        } catch (ParseException ex) {
            Logger.getLogger(ParkingChargeCalculatorTest.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
