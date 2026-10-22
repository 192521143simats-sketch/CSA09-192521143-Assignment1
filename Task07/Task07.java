import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;

public class Task07 {
    public static void main(String[] args) {
        Vehicle a = new Car("TN02B2");
        Vehicle b = new ElectricCar("TN02B2");
        System.out.println(a);
        System.out.println(b);
        System.out.println("Equal by registration: " + a.equals(b));
        System.out.println("Same hash: " + (a.hashCode() == b.hashCode()));
        System.out.println("Set size: " + new java.util.HashSet<>(java.util.List.of(a, b)).size());
        System.out.println(
                "Overridden rate: bike="
                        + new TwoWheeler("TN01A1").calculateFee(60)
                        + ", car="
                        + a.calculateFee(60));
    }
}
