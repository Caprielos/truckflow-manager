package it.gabriele.truckflow.domain.fuel;

import it.gabriele.truckflow.domain.shared.Money;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FuelTransactionTest {

    @Test
    void shouldCalculateRealConsumptionKilometersPerLiter() {
        FuelTransaction previous = FuelTransaction.of(
                "truck-001",
                LocalDateTime.of(2026, 1, 1, 8, 0),
                300,
                Money.of("1.70", "EUR"),
                100000,
                FuelCardProvider.DKV
        );
        FuelTransaction current = FuelTransaction.of(
                "truck-001",
                LocalDateTime.of(2026, 1, 2, 8, 0),
                250,
                Money.of("1.72", "EUR"),
                100750,
                FuelCardProvider.DKV
        );

        assertEquals(3.0, current.calculateKilometersPerLiter(previous), 0.0001);
    }
}
