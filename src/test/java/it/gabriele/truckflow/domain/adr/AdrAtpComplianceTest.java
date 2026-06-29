package it.gabriele.truckflow.domain.adr;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.atp.AtpCertificate;
import it.gabriele.truckflow.domain.atp.AtpClass;
import it.gabriele.truckflow.domain.atp.AtpRules;
import it.gabriele.truckflow.domain.atp.TemperatureRecorder;
import it.gabriele.truckflow.domain.cargo.AdrClass;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AdrAtpComplianceTest {

  @Test
  void shouldRequireAdrCoreEquipmentAndChecklistBeforeDeparture() {
    AdrComplianceProfile profile =
        new AdrComplianceProfile(
            "ADR-PROFILE-001",
            "TRUCK-ADR-001",
            Set.of(AdrClass.CLASS_3_FLAMMABLE_LIQUIDS),
            Set.of(
                AdrEquipmentType.FIRE_EXTINGUISHER,
                AdrEquipmentType.WHEEL_CHOCK,
                AdrEquipmentType.WARNING_VEST,
                AdrEquipmentType.PORTABLE_LIGHT,
                AdrEquipmentType.PROTECTIVE_GLOVES,
                AdrEquipmentType.EYE_PROTECTION,
                AdrEquipmentType.WRITTEN_INSTRUCTIONS,
                AdrEquipmentType.ORANGE_PLATES,
                AdrEquipmentType.HAZARD_LABELS),
            AdrTunnelRestrictionCode.C,
            true,
            true,
            true);

    AdrOperationalChecklist checklist =
        new AdrOperationalChecklist(
            "ADR-CHECK-001", "MIS-001", true, true, true, true, true, true, true);

    assertTrue(AdrRules.hasCoreEquipment(profile));
    assertTrue(AdrRules.canCarryAdrClass(profile, AdrClass.CLASS_3_FLAMMABLE_LIQUIDS));
    assertTrue(AdrRules.canDepart(profile, checklist));
  }

  @Test
  void shouldValidateAtpCertificateAndTemperatureRecorder() {
    TemperatureRecorder recorder =
        new TemperatureRecorder("TEMP-001", LocalDate.of(2027, 1, 1), true, true);
    AtpCertificate certificate =
        new AtpCertificate(
            "ATP-001",
            "FRIGO-001",
            AtpClass.FRC,
            LocalDate.of(2027, 6, 1),
            TemperatureRange.ofCelsius(-25, 8),
            java.util.List.of(recorder),
            true,
            true);

    assertTrue(
        AtpRules.isReadyForFoodOrPharmaTransport(
            certificate, TemperatureRange.ofCelsius(2, 8), LocalDate.of(2026, 6, 1)));
    assertTrue(AtpRules.canCarryFrozenGoods(certificate, LocalDate.of(2026, 6, 1)));

    assertFalse(
        AtpRules.canCarryTemperatureRange(
            certificate, TemperatureRange.ofCelsius(-30, -20), LocalDate.of(2026, 6, 1)));
  }
}
