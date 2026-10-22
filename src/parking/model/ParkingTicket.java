package parking.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import parking.pass.Pass;

public final class ParkingTicket {
    private static final String RUN = UUID.randomUUID().toString();
    private static final AtomicLong NEXT = new AtomicLong();
    private final String id;
    private final Vehicle vehicle;
    private final Slot slot;
    private final LocalDateTime entry;
    private final String role;
    private final Pass pass;

    public ParkingTicket(Vehicle v, Slot s, LocalDateTime entry) {
        this(v, s, entry, "VISITOR", Pass.none());
    }

    public ParkingTicket(Vehicle v, Slot s, LocalDateTime entry, String role, Pass pass) {
        this.vehicle = Objects.requireNonNull(v);
        this.slot = Objects.requireNonNull(s);
        this.entry = Objects.requireNonNull(entry);
        this.pass = Objects.requireNonNull(pass);
        if (!java.util.Set.of("VISITOR", "STAFF", "STUDENT").contains(role))
            throw new IllegalArgumentException("Invalid role");
        this.role = role;
        id = RUN + "-" + NEXT.incrementAndGet();
    }

    public String getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public record Position(int floor, int number) {
        public int getFloor() {
            return floor;
        }

        public int getNumber() {
            return number;
        }
    }

    public Position getSlot() {
        return new Position(slot.getFloor(), slot.getNumber());
    }

    public LocalDateTime getEntry() {
        return entry;
    }

    public String getRole() {
        return role;
    }

    public Pass getPass() {
        return pass;
    }

    public static long issuedCount() {
        return NEXT.get();
    }
}
