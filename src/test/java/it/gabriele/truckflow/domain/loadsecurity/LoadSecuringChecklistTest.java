package it.gabriele.truckflow.domain.loadsecurity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class LoadSecuringChecklistTest {

  @Test
  void shouldCountLoadSecuringEquipmentByType() {
    LoadSecuringChecklist checklist =
        LoadSecuringChecklist.of(
            List.of(
                LoadSecuringEquipment.of(LoadSecuringEquipmentType.RATCHET_STRAP, 12, 5000),
                LoadSecuringEquipment.of(LoadSecuringEquipmentType.LOAD_BAR, 4, 0)));

    assertEquals(12, checklist.countByType(LoadSecuringEquipmentType.RATCHET_STRAP));
    assertTrue(checklist.hasAtLeast(LoadSecuringEquipmentType.LOAD_BAR, 4));
    assertFalse(checklist.hasAtLeast(LoadSecuringEquipmentType.EDGE_PROTECTOR, 1));
  }
}
