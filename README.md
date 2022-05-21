# ParkingLot-Java
Java Design Patterns, Google Guice dependency injection, multi-threading

UI on this is nothing special but the real point is in the Java - lots of design patterns.
This application incorporates design patterns including Factory, Decorator, and Client-Server to 
create a robust application framework. Serialization of the data has been implemented, using Java 
object serialization. The Server is set 
up with multi-threading, including protection for the synchronized ParkingOffice and ParkingLot objects. 
Dependency injection for the ParkingOffice and ParkingLot objects has been implemented using Google 
Guice, including within the relevant unit tests. Unit testing, while not 100% complete at this time, has 
been widely implemented. 


 To run, first start Server, then ParkingGui. The JSON-oriented classes aren’t 100% 
functional yet, so they should not be started. 
 To “Register Vehicle”, CarType options of SUV or COMPACT are the only options that 
will correctly Register the car. Changing the CarType input to a dropdown with only 
those two options would be a ParkingGui improvement that should be made soon. 
 “Customer Registration” occurs automatically with Vehicle Registration, as noted earlier. 
 Registering a car with a duplicate license plate will retun null to the client and throw an 
exception: 
Mon May 31 14:45:19 PDT 2021 SEVERE (Server.Server$ClientHandler run) null 
java.lang.Exception: license plate already registered 
 To “Park Car”, the only required input is the integer corresponding to the Parking 
Permit Id. So in the example shown above to park that vehicle, just enter 0. The “park 
time” parking is equal to the time of submission, so no “park time” user entry is 
required. Also, by convention this lot doesn’t do overnight parking. You park and pay 
for a day, so charges are based on that and I am not currently tracking time of exit. 
That is probably another improvement that should be made as well. “Park Car” will 
return the charges from the Server, as in “[Parking Transaction, Id = 0, $20]” 
 “View Customers” provides a list of all registered customers. 
 “Get Charges” requires just a user name, and then the Server will respond back with a 
list of all such charges for that user. Another improvement here would be to add more 
detail to that response, such as license plate and park time. 
