
package Fee;


public class WeeknightParkingChargeDecorator extends ParkingChargeDecorator{
    public WeeknightParkingChargeDecorator(ParkingChargeCalculator customParkingChargeCalculator){
    super(customParkingChargeCalculator);
    }
    public Long calculateFee(Long baseFee){
        return customParkingChargeCalculator.calculateFee(baseFee) - applyWeeknightDiscount();
    }
    
    private Long applyWeeknightDiscount(){
                return 3L;
    }
}
