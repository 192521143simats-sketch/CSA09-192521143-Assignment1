package parking.model;

public class TwoWheeler extends Vehicle {
    public TwoWheeler(String regNo) {
        super(regNo);
    }

    @Override
    protected long ratePerHour() {
        return 10;
    }
}
