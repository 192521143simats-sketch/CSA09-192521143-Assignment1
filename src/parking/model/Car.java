package parking.model;
public class Car extends Vehicle {
    public Car(String regNo) { super(regNo); }
    @Override protected long ratePerHour() { return 30; }
}
