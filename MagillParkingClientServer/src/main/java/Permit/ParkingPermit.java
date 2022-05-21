package Permit;

import Customer.Car;



public class ParkingPermit {
    private Car car;
    private String id;
    
    public ParkingPermit(String thisId, Car thisCar){
        id = thisId;
        car = thisCar;
    }

    
    public Car getCar(){
        return car;
    }
    
    public String getPermitId(){
        return id;
    }

  @Override
  public String toString() {
    return "ParkingPermitId=" +id + " Owner=" + car.getOwner().getName() + " CarType=" + car.getType().toString() + " LicensePlate=" + car.getLicensePlate();
  }
}
