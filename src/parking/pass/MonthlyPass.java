package parking.pass;

import java.time.*;
import java.util.Locale;

public final class MonthlyPass implements Pass {
    private final String regNo;
    private final YearMonth month;

    public MonthlyPass(String regNo, YearMonth month) {
        if (regNo == null || month == null) throw new IllegalArgumentException("Invalid pass");
        this.regNo = regNo.toUpperCase(Locale.ROOT);
        this.month = month;
    }

    @Override
    public boolean covers(String reg, String role, LocalDateTime in, LocalDateTime out) {
        return regNo.equals(reg)
                && (role.equals("STUDENT") || role.equals("STAFF"))
                && YearMonth.from(in).equals(month)
                && YearMonth.from(out).equals(month);
    }
}
