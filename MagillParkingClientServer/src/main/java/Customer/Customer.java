package Customer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class Customer {
  private final String name;

  @Inject
  public Customer(@Named("customerName") String name) {
    this.name = name;
  }


  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Customer)) {
      return false;
    }
    Customer other = (Customer) obj;
    if (name == null) {
      return other.name == null;
    } else {
      return name.equals(other.name);
    }
  }

  @Override
  public String toString() {
    return  name ;
  }
}
