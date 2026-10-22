package parking.pricing;

import java.time.LocalDateTime;
import parking.model.Vehicle;

public interface PricingRule {
    long parkingFee(Vehicle v, LocalDateTime in, LocalDateTime out);
}
