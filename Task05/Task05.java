import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;
public class Task05 { public static void main(String[] args) {
LocalDateTime in = LocalDateTime.of(2026, 10, 22, 10, 0);
ParkingFacility north = new ParkingFacility(new int[]{1}, new StandardPricing());
ParkingFacility south = new ParkingFacility(new int[]{1}, new StandardPricing());
ParkingTicket a = north.enter(new Car("TN01A1"), in, "VISITOR", Pass.none(), false);
ParkingTicket b = south.enter(new Car("TN02B2"), in, "VISITOR", Pass.none(), false);
System.out.println("Unique across facilities: " + !a.getId().equals(b.getId()));
System.out.println("Shared issued count: " + ParkingTicket.issuedCount());
System.out.println("North occupancy: " + north.occupancy() + "; South occupancy: " + south.occupancy());
north.exit("TN01A1", in.plusHours(1), 0); a = null;
System.out.println("North after exit: " + north.occupancy());
System.out.println("Ticket becomes GC eligible only after all references disappear");
}}
