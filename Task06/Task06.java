import parking.model.*;

public class Task06 {
    public static void main(String[] args) {
        ElectricCar ev;
        System.setProperty("parking.trace", "true");
        try {
            ev = new ElectricCar("TN01E1");
        } finally {
            System.clearProperty("parking.trace");
        }
        System.out.println("ElectricCar is Car: " + (ev instanceof Car));
        System.out.println("ElectricCar is Vehicle: " + (ev instanceof Vehicle));
        System.out.println("Inherited registration: " + ev.getRegNo());
    }
}
