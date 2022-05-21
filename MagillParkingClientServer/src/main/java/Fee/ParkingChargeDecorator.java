
package Fee;




public abstract class ParkingChargeDecorator implements ParkingChargeCalculator{
    protected ParkingChargeCalculator customParkingChargeCalculator;
    public ParkingChargeDecorator(ParkingChargeCalculator customParkingChargeCalculator){
        this.customParkingChargeCalculator = customParkingChargeCalculator;
    }
    
    @Override
    public Long calculateFee(Long baseFee) {
        return customParkingChargeCalculator.calculateFee(baseFee);
    }
}
