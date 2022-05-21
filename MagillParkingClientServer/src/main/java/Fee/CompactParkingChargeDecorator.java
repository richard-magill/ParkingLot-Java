
package Fee;



public class CompactParkingChargeDecorator extends ParkingChargeDecorator{
    public CompactParkingChargeDecorator(ParkingChargeCalculator customParkingChargeCalculator){
        super(customParkingChargeCalculator);
    }
    public Long calculateFee(Long baseFee){
        return customParkingChargeCalculator.calculateFee(baseFee) - applyCompactDiscount();
    }
    
    private Long applyCompactDiscount(){
            return 3L;
    } 
}
