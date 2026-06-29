package it.gabriele.truckflow.domain.document;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DeliveryNoteAndDocumentBundleTest {

  @Test
  void shouldModelStructuredDeliveryNoteAndDocumentBundle() {
    DeliveryNote deliveryNote =
        DeliveryNote.of(
            "DDT-001",
            "SHP-001",
            "ACME-MI",
            "CLIENTE-RM",
            "LOC-MI",
            "LOC-RM",
            LocalDate.of(2026, 6, 29),
            List.of(
                DeliveryNoteLine.of("L1", "Farmaci su pallet", 18, 7200, 22.5, 18, Notes.empty()),
                DeliveryNoteLine.of(
                    "L2", "Collo controllo temperatura", 1, 20, 0.2, 0, Notes.empty())),
            TemperatureRange.ofCelsius(2, 8),
            Notes.empty());

    TransportDocument cmr =
        TransportDocument.verified(
            "CMR-001",
            TransportDocumentType.CMR_WAYBILL,
            "SHP-001",
            LocalDate.of(2026, 6, 29),
            null,
            Notes.empty());

    ShipmentDocumentBundle bundle =
        ShipmentDocumentBundle.of(
            "BUNDLE-001",
            "SHP-001",
            Set.of(TransportDocumentType.CMR_WAYBILL, TransportDocumentType.TEMPERATURE_LOG),
            List.of(cmr),
            Notes.empty());

    assertEquals(19, deliveryNote.calculateTotalPackages());
    assertEquals(18, deliveryNote.calculateTotalPallets());
    assertTrue(deliveryNote.requiresTemperatureControl());
    assertFalse(bundle.isComplete());
    assertTrue(bundle.missingRequiredTypes().contains(TransportDocumentType.TEMPERATURE_LOG));
  }
}
