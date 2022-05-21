package Transaction;

import Infrastructure.ParkingLot;
import Permit.ParkingPermit;
import Transaction.Money;
import java.util.Date;

public class ParkingTransaction {
    private Date date;
    private ParkingPermit permit;
    private ParkingLot lot;
    private Money chargedMoney;
    
    public ParkingTransaction(Date thisDate, ParkingPermit thisPermit, ParkingLot thisLot, Money thisChargedMoney){
        date = thisDate;
        permit = thisPermit;
        lot = thisLot;
        chargedMoney = thisChargedMoney;
    }
    
    public Money getChargedAmount(){
        return chargedMoney;
    }
    
    public ParkingPermit getParkingPermit(){
        return permit;
    }
}
