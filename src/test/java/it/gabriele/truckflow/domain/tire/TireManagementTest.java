package it.gabriele.truckflow.domain.tire;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TireManagementTest {

  @Test
  void shouldTrackSingleTireAndWheelPosition() {
    Tire tire = Tire.of("rfid-001", TireStatus.RETREADED, 7.5, 120000, 145000);
    WheelPosition position = WheelPosition.of(2, WheelSide.RIGHT, WheelSlot.OUTER);

    assertEquals("RFID-001", tire.getTireCode());
    assertEquals(25000, tire.calculateKilometersInUse());
    assertFalse(tire.isBelowLegalMinimum());
    assertEquals("Asse 2 RIGHT OUTER", position.formatLabel());
  }
}
