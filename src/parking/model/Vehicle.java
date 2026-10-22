package parking.model;
import java.time.*;
import java.util.Locale;
public abstract class Vehicle {
    private final String regNo;
    protected Vehicle(String regNo) {
        if (regNo == null || !regNo.trim().matches("[A-Za-z0-9]{3,15}"))
            throw new IllegalArgumentException("Invalid registration");
        this.regNo = regNo.trim().toUpperCase(Locale.ROOT);
    }
    public final String getRegNo() { return regNo; }
    protected abstract long ratePerHour();
    public long calculateFee(long minutes) {
        if (minutes < 0) throw new IllegalArgumentException("Negative duration");
        long hours = minutes / 60 + (minutes % 60 == 0 ? 0 : 1);
        return Math.multiplyExact(hours, ratePerHour());
    }
    public long calculateFee(LocalDateTime in, LocalDateTime out) {
        if (out.isBefore(in)) throw new IllegalArgumentException("Exit before entry");
        Duration d = Duration.between(in, out);
        long seconds = d.getSeconds();
        long minutes = seconds / 60 + (seconds % 60 != 0 || d.getNano() != 0 ? 1 : 0);
        return calculateFee(minutes);
    }
    @Override public final boolean equals(Object other) {
        return other instanceof Vehicle v && regNo.equals(v.regNo);
    }
    @Override public final int hashCode() { return regNo.hashCode(); }
    @Override public String toString() { return getClass().getSimpleName() + " " + regNo; }
}
