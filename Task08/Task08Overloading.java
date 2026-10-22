import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;

public class Task08Overloading {
    public static void main(String[] args) {
        parking.model.Vehicle v = new parking.model.ElectricCar("TN03C3");
        LocalDateTime in = LocalDateTime.of(2026, 10, 22, 10, 0);
        System.out.println("calculateFee(long): " + v.calculateFee(130L));
        System.out.println(
                "calculateFee(LocalDateTime, LocalDateTime): "
                        + v.calculateFee(in, in.plusMinutes(130)));
        System.out.println(
                "Declared Vehicle -> actual ElectricCar -> inherited calculateFee -> Car.ratePerHour");
    }
}
