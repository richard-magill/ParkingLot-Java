
package Fee;




public class BasicParkingChargeCalculator implements ParkingChargeCalculator{
    @Override
    public Long calculateFee(Long baseFee){
        return baseFee;
    }
}
