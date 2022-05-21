
package Fee;

import com.google.inject.AbstractModule;


public class FactoryModule extends AbstractModule{
    protected void configure(){
        bind(ParkingChargeCalculator.class).to(BasicParkingChargeCalculator.class);
    }
}
