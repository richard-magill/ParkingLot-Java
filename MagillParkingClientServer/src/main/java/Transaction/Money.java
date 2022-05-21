package Transaction;

//immutable Money class, can't be changed once created
public final class Money {
   //immutable private attributes
   private final String currency;
   private final long amount;
   
   //default constructor
   public Money(String myCurrency,long myAmount){
       this.currency = myCurrency;
       this.amount = myAmount;
   }
   
   //get currency
   public String getCurrency(){
       return currency;
   }
   
   //get amount
   public long getAmount()
   {
       return amount;
   }
   @Override
   public String toString() {
    return String.valueOf(amount) + " " + currency;
   }
}
