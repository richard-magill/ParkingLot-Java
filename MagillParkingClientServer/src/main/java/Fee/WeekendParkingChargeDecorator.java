
package Fee;


public class WeekendParkingChargeDecorator extends ParkingChargeDecorator{
    public WeekendParkingChargeDecorator(ParkingChargeCalculator customParkingChargeCalculator){
    super(customParkingChargeCalculator);
    }
    public Long calculateFee(Long baseFee){
        return customParkingChargeCalculator.calculateFee(baseFee) - applyWeekendDiscount();
    }
    
    private Long applyWeekendDiscount(){
                return 5L;
    }
}
