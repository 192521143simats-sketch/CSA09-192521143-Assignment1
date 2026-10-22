package parking.model;
public class ElectricCar extends Car implements Chargeable {
    public ElectricCar(String regNo) { super(regNo); }
    @Override public long chargingFee(int kWh) {
        Chargeable.validateUnits(kWh);
        return 12L * kWh;
    }
}
