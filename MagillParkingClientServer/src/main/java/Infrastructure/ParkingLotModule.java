
package Infrastructure;

import com.google.inject.AbstractModule;

public class ParkingLotModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(String.class)
          .toInstance("Main Street Lot");
        bind(Long.class)
           .toInstance(20L);
    }
}
