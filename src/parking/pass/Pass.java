package parking.pass;
import java.time.LocalDateTime;
public interface Pass {
    boolean covers(String regNo, String role, LocalDateTime in, LocalDateTime out);
    static Pass none() { return (reg, role, in, out) -> false; }
}
