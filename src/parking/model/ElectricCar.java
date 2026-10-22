package parking.model;

public class ElectricCar extends Car implements Chargeable {
    public ElectricCar(String regNo) {
        super(regNo);
        if (Boolean.getBoolean("parking.trace")) System.out.println("3 ElectricCar constructor");
    }

    @Override
    public long chargingFee(int kWh) {
        Chargeable.validateUnits(kWh);
        return 12L * kWh;
    }
}
