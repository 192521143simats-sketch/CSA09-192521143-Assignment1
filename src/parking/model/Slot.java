package parking.model;

public final class Slot {
    private final int floor;
    private final int number;
    private final boolean charging;
    private Vehicle vehicle;

    public Slot(int floor, int number, boolean charging) {
        if (floor < 0 || number < 0) throw new IllegalArgumentException("Invalid slot");
        this.floor = floor;
        this.number = number;
        this.charging = charging;
    }

    public int getFloor() {
        return floor;
    }

    public int getNumber() {
        return number;
    }

    public boolean isFree() {
        return vehicle == null;
    }

    public boolean isSuitable(Vehicle v, boolean needsCharging) {
        return needsCharging ? charging && v instanceof Chargeable : !charging;
    }

    public void occupy(Vehicle v) {
        if (v == null || !isFree()) throw new IllegalStateException("Slot unavailable");
        vehicle = v;
    }

    public boolean release() {
        if (isFree()) return false;
        vehicle = null;
        return true;
    }
}
