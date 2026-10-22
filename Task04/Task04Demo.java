import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;
public class Task04Demo { public static void main(String[] args) {
LocalDateTime in = LocalDateTime.of(2026, 10, 22, 10, 0);
ParkingTicket a = new ParkingTicket(new Car("TN01A1"), new Slot(0, 0, false), in);
ParkingTicket b = new ParkingTicket(new Car("TN02B2"), new Slot(0, 1, false), in, "STAFF", Pass.none());
System.out.println("Short constructor role: " + a.getRole());
System.out.println("Full constructor role: " + b.getRole());
System.out.println("Different ticket identities: " + !a.getId().equals(b.getId()));
System.out.println("Vehicle reference retained: " + a.getVehicle());
}}
