import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;

public class Task12 {
    public static void main(String[] args) {
        ParkingFacility p = new ParkingFacility(new int[] {2, 3, 4, 2, 5}, new ChallengePricing());
        LocalDateTime in = LocalDateTime.of(2026, 10, 22, 8, 0);
        p.enter(new EBike("EB001"), in, "VISITOR", Pass.none(), false);
        p.enter(new DeliveryVan("VAN001"), in, "VISITOR", Pass.none(), false);
        p.enter(
                new Car("DAY001"),
                in,
                "VISITOR",
                new OneDayPass("DAY001", in.toLocalDate()),
                false);
        System.out.println("Five floor capacities: [2, 3, 4, 2, 5]; total=" + p.capacity());
        System.out.println(
                "E-bike flat fee for 2 hours: Rs " + p.exit("EB001", in.plusHours(2), 0));
        System.out.println("Van 08:00-10:00 (30 + 60): Rs " + p.exit("VAN001", in.plusHours(2), 0));
        System.out.println(
                "Visitor one-day pass parking: Rs " + p.exit("DAY001", in.plusHours(2), 0));
        System.out.println("Total collection: Rs " + p.getCollection());
        System.out.println("Occupancy after exits: " + p.occupancy());
    }
}
