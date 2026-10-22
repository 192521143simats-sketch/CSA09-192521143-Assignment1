package parking.model;

public final class EBike extends Vehicle {
    public EBike(String reg) {
        super(reg);
    }

    @Override
    protected long ratePerHour() {
        return 0;
    }

    @Override
    public long calculateFee(long minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Negative duration");
        return minutes == 0 ? 0 : 20;
    }
}
