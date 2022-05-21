
package Infrastructure;


import com.google.inject.AbstractModule;

public class ParkingOfficeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(String.class)
          .toInstance("Official Parking Office");
    }
}
