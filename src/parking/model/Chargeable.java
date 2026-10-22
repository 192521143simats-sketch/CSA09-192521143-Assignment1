package parking.model;

public interface Chargeable {
    long chargingFee(int kWh);

    static void validateUnits(int kWh) {
        if (kWh < 0) throw new IllegalArgumentException("Negative charging units");
    }

    default String chargingDescription() {
        return "Charging billed separately";
    }
}
