
package TestPackage;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {  
       
      Result resultParkingChargeCalculatorTest = JUnitCore.runClasses(ParkingChargeCalculatorTest.class);    
      Result resultParkingLotTest = JUnitCore.runClasses(ParkingLotTest.class);
      Result resultParkingOfficeTest = JUnitCore.runClasses(ParkingOfficeTest.class);


      //test ParkingChargeCalculator class
      for (Failure failure : resultParkingChargeCalculatorTest.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("successful ParkingChargeCalculator test? " + resultParkingChargeCalculatorTest.wasSuccessful());
      
      //test ParkingLot class
      for (Failure failure : resultParkingLotTest.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("successful ParkingLot test? " + resultParkingLotTest.wasSuccessful());
      
      //test ParkingOffice class
      for (Failure failure : resultParkingOfficeTest.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println("successful ParkingOffice test? " + resultParkingOfficeTest.wasSuccessful());
      

   

   }  
}
