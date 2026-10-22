import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;

public class Task11 {
    static int passed;
    static LocalDateTime in = LocalDateTime.of(2026, 10, 22, 10, 0);

    static void check(String name, Object expected, Object actual) {
        if (!expected.equals(actual))
            throw new AssertionError(name + " expected=" + expected + " actual=" + actual);
        passed++;
        System.out.println("PASS | " + name + " | expected=" + expected + " | actual=" + actual);
    }

    static void reject(String name, Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException | IllegalStateException e) {
            check(name, "rejected", "rejected");
            return;
        }
        throw new AssertionError(name + " was accepted");
    }

    public static void main(String[] args) {
        Car car = new Car("TN01A1");
        check("zero minutes", 0L, car.calculateFee(0));
        check("exact hour", 30L, car.calculateFee(60));
        check("partial hour", 60L, car.calculateFee(61));
        check("130 minutes", 90L, car.calculateFee(130));
        check("positive second", 30L, car.calculateFee(in, in.plusSeconds(1)));
        check("fractional second", 30L, car.calculateFee(in, in.plusNanos(1)));
        check("two wheeler", 30L, new TwoWheeler("TN02B2").calculateFee(130));
        ElectricCar ev = new ElectricCar("TN03E3");
        check("EV charging", 120L, ev.chargingFee(10));
        reject("negative duration", () -> car.calculateFee(-1));
        reject("negative kWh", () -> ev.chargingFee(-1));
        reject("unknown type", () -> parking.app.Main.vehicle("UFO", "TN01A1"));
        reject("invalid registration", () -> new Car("?"));
        ParkingFacility p = new ParkingFacility(new int[] {2, 1}, new StandardPricing());
        ParkingTicket first = p.enter(car, in, "VISITOR", Pass.none(), false);
        check("first slot", 0, first.getSlot().getNumber());
        reject(
                "duplicate registration",
                () -> p.enter(new Car("tn01a1"), in, "VISITOR", Pass.none(), false));
        p.enter(new TwoWheeler("TN02B2"), in, "VISITOR", Pass.none(), false);
        reject(
                "last general slot occupied",
                () -> p.enter(new Car("TN04A4"), in, "VISITOR", Pass.none(), false));
        p.enter(ev, in, "VISITOR", Pass.none(), true);
        check("full occupancy", 3, p.occupancy());
        reject("invalid floor", () -> new Slot(-1, 0, false));
        check("invalid slot index", false, p.isValidSlot(0, 2));
        check("invalid floor index", false, p.isValidSlot(5, 0));
        reject("exit before entry", () -> p.exit("TN01A1", in.minusSeconds(1), 0));
        check("invalid exit retains occupancy", 3, p.occupancy());
        reject("non-EV charging", () -> p.exit("TN01A1", in, 1));
        check("car exit", 90L, p.exit("TN01A1", in.plusMinutes(130), 0));
        check("bike exit", 30L, p.exit("TN02B2", in.plusMinutes(130), 0));
        check("EV exit", 210L, p.exit("TN03E3", in.plusMinutes(130), 10));
        check("hand collection 90+30+210", 330L, p.getCollection());
        check("released occupancy", 0, p.occupancy());
        reject("unknown exit", () -> p.exit("TN01A1", in, 0));
        Slot s = new Slot(0, 0, false);
        s.occupy(car);
        check("release occupied", true, s.release());
        check("release already free", false, s.release());
        ParkingTicket again =
                p.enter(
                        car,
                        in,
                        "STAFF",
                        new MonthlyPass(car.getRegNo(), YearMonth.of(2026, 10)),
                        false);
        check("reallocation", 0, again.getSlot().getNumber());
        check("ticket never reused", false, first.getId().equals(again.getId()));
        check("monthly pass free", 0L, p.exit("TN01A1", in.plusHours(1), 0));
        p.enter(car, in, "STUDENT", new MonthlyPass(car.getRegNo(), YearMonth.of(2026, 9)), false);
        check("expired pass pays", 30L, p.exit("TN01A1", in.plusHours(1), 0));
        p.enter(car, in, "VISITOR", new MonthlyPass(car.getRegNo(), YearMonth.of(2026, 10)), false);
        check("visitor cannot use monthly", 30L, p.exit("TN01A1", in.plusHours(1), 0));
        p.enter(ev, in, "STAFF", new MonthlyPass(ev.getRegNo(), YearMonth.of(2026, 10)), true);
        check("pass does not waive charging", 120L, p.exit("TN03E3", in.plusHours(1), 10));
        MonthlyPass month = new MonthlyPass(car.getRegNo(), YearMonth.of(2026, 10));
        check(
                "monthly expiry midnight",
                false,
                month.covers(car.getRegNo(), "STAFF", in, LocalDateTime.of(2026, 11, 1, 0, 0)));
        check("pass bound to registration", false, month.covers("OTHER", "STAFF", in, in));
        check("equal across subclass", true, car.equals(new ElectricCar("TN01A1")));
        check("equal hashes", car.hashCode(), new ElectricCar("TN01A1").hashCode());
        reject("zero capacity", () -> new ParkingFacility(new int[] {0}, new StandardPricing()));
        ParkingFacility north = new ParkingFacility(new int[] {1}, new StandardPricing());
        ParkingFacility south = new ParkingFacility(new int[] {1}, new StandardPricing());
        ParkingTicket a = north.enter(car, in, "VISITOR", Pass.none(), false);
        ParkingTicket b = south.enter(car, in, "VISITOR", Pass.none(), false);
        check("unique between facilities", false, a.getId().equals(b.getId()));
        check("isolated occupancy", 1, north.occupancy());
        System.out.println("TOTAL PASSED: " + passed);
    }
}
