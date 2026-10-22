import parking.model.*;
import parking.pass.*;
import parking.pricing.*;
import parking.service.ParkingFacility;
import java.time.*;

public class Task02 {
    public static void main(String[] args) {
        int[] capacities = {20, 50, 100};
        String[][] slots = new String[capacities.length][];
        for (int f = 0; f < capacities.length; f++) slots[f] = new String[capacities[f]];
        int minutes = 61;
        System.out.println("Floor capacities: " + java.util.Arrays.toString(capacities));
        System.out.println("Wrong integer hours: " + minutes / 60);
        System.out.println("Wrong fee: Rs " + (minutes / 60) * 30);
        System.out.println("Correct fee: Rs " + new Car("TN01A1").calculateFee(minutes));
        System.out.println(
                "Input: command-line arguments; times: LocalDateTime; fees: long rupees");
    }
}
