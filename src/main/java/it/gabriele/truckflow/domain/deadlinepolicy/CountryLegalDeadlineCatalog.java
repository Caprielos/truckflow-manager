package it.gabriele.truckflow.domain.deadlinepolicy;

import it.gabriele.truckflow.domain.deadline.DeadlineOwnerType;
import it.gabriele.truckflow.domain.deadline.DeadlineSeverity;
import it.gabriele.truckflow.domain.deadline.DeadlineType;
import it.gabriele.truckflow.domain.regulation.EuropeanCountry;
import java.util.List;
import java.util.Objects;

/** Catalogo iniziale delle regole legali caricabili in base al paese configurato. */
public final class CountryLegalDeadlineCatalog {

  private CountryLegalDeadlineCatalog() {}

  public static List<DeadlinePolicyRule> rulesFor(EuropeanCountry country) {
    Objects.requireNonNull(country, "Il paese configurato è obbligatorio.");

    List<DeadlinePolicyRule> commonEuropeanRules = commonEuropeanRules(country);

    if (country == EuropeanCountry.ITALY) {
      return java.util.stream.Stream.concat(
              commonEuropeanRules.stream(), italySpecificRules().stream())
          .toList();
    }

    return commonEuropeanRules;
  }

  private static List<DeadlinePolicyRule> commonEuropeanRules(EuropeanCountry country) {
    return List.of(
        DeadlinePolicyRule.legal(
            "EU_TACHOGRAPH_CALIBRATION",
            country,
            DeadlineOwnerType.VEHICLE,
            ManagedDeadlineElementType.VEHICLE_TACHOGRAPH_CALIBRATION,
            DeadlineType.VEHICLE_INSPECTION,
            DeadlineRuleInterval.calendarMonths(24, 60),
            DeadlineSeverity.CRITICAL,
            true,
            "Regolamento UE 165/2014 - configurazione iniziale"),
        DeadlinePolicyRule.legal(
            "EU_DRIVER_TACHOGRAPH_CARD",
            country,
            DeadlineOwnerType.DRIVER,
            ManagedDeadlineElementType.DRIVER_TACHOGRAPH_CARD,
            DeadlineType.DOCUMENT_EXPIRATION,
            DeadlineRuleInterval.calendarMonths(60, 90),
            DeadlineSeverity.CRITICAL,
            true,
            "Regolamento UE 165/2014 - configurazione iniziale"),
        DeadlinePolicyRule.legal(
            "EU_DRIVER_CQC",
            country,
            DeadlineOwnerType.DRIVER,
            ManagedDeadlineElementType.DRIVER_CQC,
            DeadlineType.DRIVER_CQC,
            DeadlineRuleInterval.calendarMonths(60, 120),
            DeadlineSeverity.CRITICAL,
            true,
            "Direttiva 2006/126/CE e norme nazionali - configurazione iniziale"),
        DeadlinePolicyRule.legal(
            "EU_DRIVER_ADR_CERTIFICATE",
            country,
            DeadlineOwnerType.DRIVER,
            ManagedDeadlineElementType.DRIVER_ADR_CERTIFICATE,
            DeadlineType.DRIVER_ADR,
            DeadlineRuleInterval.calendarMonths(60, 120),
            DeadlineSeverity.CRITICAL,
            true,
            "Accordo ADR - configurazione iniziale"),
        DeadlinePolicyRule.legal(
            "EU_VEHICLE_ATP_CERTIFICATE",
            country,
            DeadlineOwnerType.VEHICLE,
            ManagedDeadlineElementType.VEHICLE_ATP_CERTIFICATE,
            DeadlineType.VEHICLE_ATP_CERTIFICATE,
            DeadlineRuleInterval.calendarMonths(72, 180),
            DeadlineSeverity.CRITICAL,
            true,
            "Accordo ATP - configurazione iniziale"),
        DeadlinePolicyRule.legal(
            "EU_VEHICLE_ADR_CERTIFICATE",
            country,
            DeadlineOwnerType.VEHICLE,
            ManagedDeadlineElementType.VEHICLE_ADR_CERTIFICATE,
            DeadlineType.VEHICLE_ADR_CERTIFICATE,
            DeadlineRuleInterval.calendarMonths(12, 60),
            DeadlineSeverity.CRITICAL,
            true,
            "Accordo ADR - configurazione iniziale"));
  }

  private static List<DeadlinePolicyRule> italySpecificRules() {
    return List.of(
        DeadlinePolicyRule.legal(
            "IT_VEHICLE_INSPECTION",
            EuropeanCountry.ITALY,
            DeadlineOwnerType.VEHICLE,
            ManagedDeadlineElementType.VEHICLE_INSPECTION,
            DeadlineType.VEHICLE_INSPECTION,
            DeadlineRuleInterval.calendarMonths(12, 60),
            DeadlineSeverity.CRITICAL,
            true,
            "Codice della Strada / Direttiva 2014/45/UE - configurazione Italia iniziale"),
        DeadlinePolicyRule.legal(
            "IT_VEHICLE_INSURANCE",
            EuropeanCountry.ITALY,
            DeadlineOwnerType.VEHICLE,
            ManagedDeadlineElementType.VEHICLE_INSURANCE,
            DeadlineType.VEHICLE_INSURANCE,
            DeadlineRuleInterval.calendarMonths(12, 45),
            DeadlineSeverity.CRITICAL,
            true,
            "Assicurazione obbligatoria RCA - configurazione Italia iniziale"),
        DeadlinePolicyRule.legal(
            "IT_DRIVER_MEDICAL_CHECK",
            EuropeanCountry.ITALY,
            DeadlineOwnerType.DRIVER,
            ManagedDeadlineElementType.DRIVER_MEDICAL_CHECK,
            DeadlineType.DRIVER_MEDICAL_CHECK,
            DeadlineRuleInterval.calendarMonths(24, 90),
            DeadlineSeverity.HIGH,
            true,
            "D.Lgs. 81/2008 e sorveglianza sanitaria - configurazione Italia iniziale"),
        DeadlinePolicyRule.legal(
            "IT_DRIVER_LICENSE",
            EuropeanCountry.ITALY,
            DeadlineOwnerType.DRIVER,
            ManagedDeadlineElementType.DRIVER_LICENSE,
            DeadlineType.DRIVER_LICENSE,
            DeadlineRuleInterval.calendarMonths(60, 120),
            DeadlineSeverity.CRITICAL,
            true,
            "Patente professionale - configurazione Italia iniziale"));
  }
}
