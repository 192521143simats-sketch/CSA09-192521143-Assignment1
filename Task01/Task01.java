import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;
public class Task01 { public static void main(String[] args) {
Vehicle[] vehicles = {new TwoWheeler("TN01A1"), new Car("TN02B2"), new ElectricCar("TN03C3")};
for (Vehicle v : vehicles) System.out.println(v);
String key = "TN02B2";
for (Vehicle v : vehicles) if (v.getRegNo().equals(key)) System.out.println("Found: " + v);
System.out.println("Candidates: Vehicle, Slot, ParkingTicket, ParkingFacility, Pass, PricingRule");
}}
