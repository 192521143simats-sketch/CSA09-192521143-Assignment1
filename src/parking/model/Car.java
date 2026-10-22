package parking.model;

public class Car extends Vehicle {
    public Car(String regNo) {
        super(regNo);
        if (Boolean.getBoolean("parking.trace")) System.out.println("2 Car constructor");
    }

    @Override
    protected long ratePerHour() {
        return 30;
    }
}
