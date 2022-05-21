package Infrastructure;

import Fee.ParkingChargeCalculator;
import Fee.ParkingChargeCalculatorFactory;
import Customer.CarType;
import Transaction.Money;
import com.google.inject.Inject;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;



public final class ParkingLot{
    //immutable private attributes
    private final String id;
    private final String name;
    private final long baseFee;
    
  
    //default constructor, uses UUID to create string ID, each lot can have its own fee
    @Inject
    public ParkingLot(String thisName, long thisFee)
    {
        String uId = UUID.randomUUID().toString();
        this.id = uId;
        this.name = thisName;
        this.baseFee = thisFee;

    }

    //get name
    public String getName(){
        return this.name;
    }
    
    //get ID
    public String getId(){
        return this.id;
    }
    
    //fee calculated based on Fee decorator pattern
    public Money getRate(CarType carType, Date parkDate) throws ParseException
    {
        ParkingChargeCalculator parkingChargeCalculator 
                = new ParkingChargeCalculatorFactory(carType, parkDate).getParkingChargeCalculator();
        Long fee = parkingChargeCalculator.calculateFee(baseFee);
        return new Money("dollars", fee);
    }
}
