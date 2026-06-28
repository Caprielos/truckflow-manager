package it.gabriele.truckflow.domain.telematics;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TelematicsSnapshotTest {

    @Test
    void shouldCreateTelematicsSnapshot() {
        TelematicsSnapshot snapshot = TelematicsSnapshot.of(
                "truck-001",
                Instant.parse("2026-01-01T10:00:00Z"),
                45.4642,
                9.1900,
                123456L,
                58.5
        );

        assertEquals("TRUCK-001", snapshot.getVehicleFleetNumber());
        assertEquals(45.4642, snapshot.getLatitude(), 0.0001);
        assertEquals(58.5, snapshot.getFuelLevelPercentage(), 0.0001);
    }
}
