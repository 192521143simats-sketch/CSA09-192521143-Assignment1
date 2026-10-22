import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;
public class Task10Demo { public static void main(String[] args) {
Slot slot = new Slot(0, 0, false);
slot.occupy(new Car("TN01A1")); System.out.println("Occupied through validated method: " + !slot.isFree());
try { slot.occupy(new Car("TN02B2")); } catch (IllegalStateException e) { System.out.println("Rejected: " + e.getMessage()); }
try { new Car("TN01A1").calculateFee(-1); } catch (IllegalArgumentException e) { System.out.println("Rejected: " + e.getMessage()); }
Integer a = Integer.valueOf(1000), b = Integer.valueOf(1000);
System.out.println("Wrapper value equality: " + a.equals(b));
System.out.println("Packages: parking.model, parking.pass, parking.pricing, parking.service, parking.app");
}}
