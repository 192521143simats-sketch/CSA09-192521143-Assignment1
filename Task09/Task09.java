import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;
public class Task09 { public static void main(String[] args) {
Vehicle v = new ElectricCar("TN01E1");
Chargeable c = (Chargeable)v;
System.out.println("Abstract Vehicle reference: " + v);
System.out.println("Charging capability: Rs " + c.chargingFee(10));
System.out.println("Default interface method: " + c.chargingDescription());
Chargeable.validateUnits(0);
System.out.println("Static interface validation: accepted zero kWh");
System.out.println("Unrelated interface capability supported independently of Vehicle state");
}}
