package parking.app;
import java.time.LocalDateTime;
import parking.model.*;
import parking.pass.Pass;
import parking.pricing.StandardPricing;
import parking.service.ParkingFacility;
public final class Main {
    public static Vehicle vehicle(String type, String reg) {
        return switch (type.toUpperCase(java.util.Locale.ROOT)) {
            case "BIKE" -> new TwoWheeler(reg);
            case "CAR" -> new Car(reg);
            case "EV" -> new ElectricCar(reg);
            default -> throw new IllegalArgumentException("Unknown vehicle type");
        };
    }
    public static void main(String[] args) {
        String type = args.length > 0 ? args[0] : "EV";
        String reg = args.length > 1 ? args[1] : "TN01EV1234";
        ParkingFacility p = new ParkingFacility(new int[]{20, 20, 20}, new StandardPricing());
        Vehicle v = vehicle(type, reg);
        LocalDateTime in = LocalDateTime.of(2026, 10, 22, 10, 0);
        ParkingTicket t = p.enter(v, in, "VISITOR", Pass.none(), v instanceof Chargeable);
        System.out.println("Entered: " + v + " floor=" + t.getSlot().getFloor() + " slot=" + t.getSlot().getNumber());
        System.out.println("Ticket issued: " + t.getId());
        System.out.println("Fee for 130 minutes: Rs " + p.exit(reg, in.plusMinutes(130), v instanceof Chargeable ? 10 : 0));
        System.out.println("Occupancy after exit: " + p.occupancy());
    }
}
