package parking.service;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import parking.model.*;
import parking.pass.Pass;
import parking.pricing.PricingRule;
public final class ParkingFacility {
    private final Slot[][] slots;
    private final ParkingTicket[] active;
    private final PricingRule pricing;
    private long collection;
    public ParkingFacility(int[] capacities, PricingRule pricing) {
        if (capacities == null || capacities.length == 0) throw new IllegalArgumentException("No floors");
        slots = new Slot[capacities.length][];
        int total = 0;
        for (int f = 0; f < capacities.length; f++) {
            if (capacities[f] < 1) throw new IllegalArgumentException("Invalid capacity");
            total = Math.addExact(total, capacities[f]);
            slots[f] = new Slot[capacities[f]];
            for (int s = 0; s < capacities[f]; s++)
                slots[f][s] = new Slot(f, s, capacities[f] > 1 && s == capacities[f] - 1);
        }
        active = new ParkingTicket[total]; this.pricing = Objects.requireNonNull(pricing);
    }
    public ParkingTicket enter(Vehicle v, LocalDateTime in, String role, Pass pass, boolean charge) {
        Objects.requireNonNull(v); Objects.requireNonNull(in); Objects.requireNonNull(pass);
        if (find(v.getRegNo()) >= 0) throw new IllegalArgumentException("Duplicate registration");
        if (charge && !(v instanceof Chargeable)) throw new IllegalArgumentException("Vehicle cannot charge");
        for (Slot[] floor : slots) for (Slot s : floor) {
            if (!s.isFree() || !s.isSuitable(v, charge)) continue;
            ParkingTicket t = new ParkingTicket(v, s, in, role, pass);
            for (int i = 0; i < active.length; i++) if (active[i] == null) {
                s.occupy(v); active[i] = t; return t;
            }
        }
        throw new IllegalStateException("No suitable free slot");
    }
    private int find(String reg) {
        if (reg == null) return -1;
        String key = reg.trim().toUpperCase(Locale.ROOT);
        for (int i = 0; i < active.length; i++)
            if (active[i] != null && active[i].getVehicle().getRegNo().equals(key)) return i;
        return -1;
    }
    public long exit(String reg, LocalDateTime out, int kWh) {
        int i = find(reg);
        if (i < 0) throw new IllegalArgumentException("No active ticket");
        ParkingTicket t = active[i]; Objects.requireNonNull(out);
        if (out.isBefore(t.getEntry())) throw new IllegalArgumentException("Exit before entry");
        Chargeable.validateUnits(kWh);
        Vehicle v = t.getVehicle();
        Slot booked = slots[t.getSlot().getFloor()][t.getSlot().getNumber()];
        if (kWh > 0 && (!(v instanceof Chargeable) || !booked.isSuitable(v, true)))
            throw new IllegalArgumentException("No charging booking");
        long fee = t.getPass().covers(v.getRegNo(), t.getRole(), t.getEntry(), out)
            ? 0 : pricing.parkingFee(v, t.getEntry(), out);
        if (v instanceof Chargeable c) fee = Math.addExact(fee, c.chargingFee(kWh));
        long updated = Math.addExact(collection, fee);
        booked.release(); active[i] = null; collection = updated; return fee;
    }
    public boolean isValidSlot(int floor, int slot) {
        return floor >= 0 && floor < slots.length && slot >= 0 && slot < slots[floor].length;
    }
    public int occupancy() {
        int count = 0; for (ParkingTicket t : active) if (t != null) count++; return count;
    }
    public long getCollection() { return collection; }
    public int capacity() { return active.length; }
}
