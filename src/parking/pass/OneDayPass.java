package parking.pass;
import java.time.*;
import java.util.Locale;
public final class OneDayPass implements Pass {
    private final String reg;
    private final LocalDate date;
    public OneDayPass(String reg, LocalDate date) {
        if (reg == null || date == null) throw new IllegalArgumentException("Invalid pass");
        this.reg = reg.toUpperCase(Locale.ROOT); this.date = date;
    }
    @Override public boolean covers(String reg, String role, LocalDateTime in, LocalDateTime out) {
        return this.reg.equals(reg) && role.equals("VISITOR")
            && in.toLocalDate().equals(date) && out.toLocalDate().equals(date);
    }
}
