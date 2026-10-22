package parking.pricing;
import java.time.LocalDateTime;
import parking.model.Vehicle;
public final class StandardPricing implements PricingRule {
    @Override public long parkingFee(Vehicle v, LocalDateTime in, LocalDateTime out) {
        return v.calculateFee(in, out);
    }
}
