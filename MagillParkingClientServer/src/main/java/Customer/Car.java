package Customer;

import com.google.inject.Inject;
import com.google.inject.name.Named;


public class Car{
    private final CarType type;
    private final String licensePlate;
    private final Customer owner;


    public Car(CarType thisCarType, String thisLicensePlate,Customer thisOwner){
        this.type = thisCarType;
        this.licensePlate = thisLicensePlate; 
        this.owner = thisOwner;
    }
    
    public CarType getType(){
        return type;
    }
    
    public String getLicensePlate(){
        return licensePlate;
    }
    
    public Customer getOwner(){
        return owner;
    }


    @Override
    public String toString() {
      return "Car [CarType =" + type.toString() + ", license=" + licensePlate + ", customer=" + owner + "]";
    }

}

