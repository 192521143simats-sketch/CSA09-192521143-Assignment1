package parking.pricing;
import java.math.*;
import java.time.*;
import parking.model.*;
public final class ChallengePricing implements PricingRule {
    @Override public long parkingFee(Vehicle v, LocalDateTime in, LocalDateTime out) {
        if (out.isBefore(in)) throw new IllegalArgumentException("Exit before entry");
        if (!(v instanceof DeliveryVan)) return v.calculateFee(in, out);
        LocalDateTime cursor = in;
        BigDecimal weightedSeconds = BigDecimal.ZERO;
        while (cursor.isBefore(out)) {
            LocalDate day = cursor.toLocalDate();
            LocalDateTime start = day.atTime(9, 0), end = day.atTime(17, 0);
            boolean peak = !cursor.isBefore(start) && cursor.isBefore(end);
            LocalDateTime boundary = cursor.isBefore(start) ? start : peak ? end : day.plusDays(1).atStartOfDay();
            LocalDateTime stop = boundary.isBefore(out) ? boundary : out;
            Duration d = Duration.between(cursor, stop);
            BigDecimal seconds = BigDecimal.valueOf(d.getSeconds()).add(BigDecimal.valueOf(d.getNano(),9));
            weightedSeconds = weightedSeconds.add(seconds.multiply(BigDecimal.valueOf(peak ? 60 : 30)));
            cursor = stop;
        }
        return weightedSeconds.divide(BigDecimal.valueOf(3600),0,RoundingMode.CEILING).longValueExact();
    }
}
