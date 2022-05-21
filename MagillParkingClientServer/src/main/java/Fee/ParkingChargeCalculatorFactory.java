
package Fee;

import Customer.CarType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class ParkingChargeCalculatorFactory {
        private CarType carType;
        private Date parkDate;
        
        
        public ParkingChargeCalculatorFactory(CarType carType, Date parkDate){
            this.carType = carType;
            this.parkDate = parkDate;
        }
        
        public ParkingChargeCalculator getParkingChargeCalculator() throws ParseException{
            ParkingChargeCalculator parkingChargeCalculator = new BasicParkingChargeCalculator();
            parkingChargeCalculator = applyCarTypeDecorator(parkingChargeCalculator);
            parkingChargeCalculator = applyWeekendDecorator(parkingChargeCalculator);
            parkingChargeCalculator = applyWeeknightDecorator(parkingChargeCalculator); 
            return  parkingChargeCalculator;  
        }
        
        private ParkingChargeCalculator applyCarTypeDecorator(ParkingChargeCalculator parkingChargeCalculator){
            if (carType ==  CarType.COMPACT){
                parkingChargeCalculator = new CompactParkingChargeDecorator(parkingChargeCalculator);
            }
            return parkingChargeCalculator;
        }
        
        private ParkingChargeCalculator applyWeekendDecorator(ParkingChargeCalculator parkingChargeCalculator){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parkDate);
            calendar.get(Calendar.DAY_OF_WEEK);
            //if it is parked on the weekend, they get a weekend decorator applied
            if((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)||
                    (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY))
            {
                parkingChargeCalculator = new WeekendParkingChargeDecorator(parkingChargeCalculator);
            }
            return parkingChargeCalculator;
        }
        
        private ParkingChargeCalculator applyWeeknightDecorator(ParkingChargeCalculator parkingChargeCalculator) throws ParseException{
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parkDate);
            calendar.get(Calendar.DAY_OF_WEEK);
            //if it is parked on a weeknight after 6pm, they get weeknight rate
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            if((calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)&&
                    (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)&&
                    (dateFormat.parse(dateFormat.format(parkDate)).after(dateFormat.parse("18:00"))))
            {
                parkingChargeCalculator = new WeeknightParkingChargeDecorator(parkingChargeCalculator);
            }
            return parkingChargeCalculator;
        }
}
