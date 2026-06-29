package it.gabriele.truckflow.domain.payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.driver.Driver;
import it.gabriele.truckflow.domain.driver.DriverAdrCertificateType;
import it.gabriele.truckflow.domain.driver.DriverLicenseCategory;
import it.gabriele.truckflow.domain.driver.DriverOperationalQualification;
import it.gabriele.truckflow.domain.driver.DriverProfessionalQualification;
import it.gabriele.truckflow.domain.economics.MissionCostLine;
import it.gabriele.truckflow.domain.economics.MissionCostType;
import it.gabriele.truckflow.domain.fleet.VehicleBodyBaseType;
import it.gabriele.truckflow.domain.fleet.VehicleCombinationType;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Percentage;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DriverPayrollRulesTest {

  @Test
  void shouldCalculateRealisticDriverMissionCostWithAdrTrailerAllowancesAndEmployerCharges() {
    Driver driver =
        Driver.available(
            "DRV-001",
            "Mario Rossi",
            Set.of(DriverLicenseCategory.CE),
            Set.of(DriverProfessionalQualification.CQC_GOODS),
            Set.of(DriverAdrCertificateType.ADR_BASIC, DriverAdrCertificateType.ADR_TANK),
            Set.of(DriverOperationalQualification.WASTE_TRANSPORT),
            Notes.empty());

    DriverMissionWorkReport report =
        DriverMissionWorkReport.builder("WR-001", "MIS-001", driver)
            .drivingTime(Duration.ofHours(8))
            .otherWorkTime(Duration.ofHours(2))
            .waitingTime(Duration.ofHours(1))
            .loadingUnloadingTime(Duration.ofHours(1))
            .overtime(Duration.ofHours(1))
            .nightWorkTime(Duration.ofHours(2))
            .internationalAllowanceDays(1)
            .overnightDays(1)
            .cargoCategories(Set.of(CargoCategory.WASTE_DANGEROUS, CargoCategory.FUEL))
            .vehicleContext(VehicleCombinationType.ARTICULATED_VEHICLE, VehicleBodyBaseType.TANK)
            .build();

    DriverPayrollPolicy policy = realisticPolicy();

    DriverMissionPayroll payroll =
        DriverPayrollRules.calculateMissionPayroll(
            "PAY-001",
            report,
            policy,
            Notes.of("Costo autista completo per missione ADR con cisterna"));

    assertTrue(payroll.containsComponent(DriverPayComponentType.BASE_DRIVING_TIME));
    assertTrue(payroll.containsComponent(DriverPayComponentType.ADR_BASIC_PREMIUM));
    assertTrue(payroll.containsComponent(DriverPayComponentType.ADR_TANK_PREMIUM));
    assertTrue(payroll.containsComponent(DriverPayComponentType.DANGEROUS_WASTE_PREMIUM));
    assertTrue(payroll.containsComponent(DriverPayComponentType.ARTICULATED_VEHICLE_PREMIUM));
    assertTrue(payroll.containsComponent(DriverPayComponentType.SEMI_TRAILER_PREMIUM));
    assertTrue(payroll.containsComponent(DriverPayComponentType.TANK_BODY_PREMIUM));
    assertTrue(payroll.containsComponent(DriverPayComponentType.SOCIAL_CONTRIBUTIONS));
    assertEquals(Money.of("760.20", "EUR"), payroll.calculateTotalEmployerCost());
    assertEquals(Money.of("105.00", "EUR"), payroll.calculateAllowancesAndReimbursements());
    assertEquals(Money.of("151.20", "EUR"), payroll.calculateEmployerCharges());
  }

  @Test
  void shouldProjectPayrollIntoMissionCostLine() {
    Driver driver =
        Driver.available(
            "DRV-002",
            "Luigi Bianchi",
            Set.of(DriverLicenseCategory.C),
            Set.of(DriverProfessionalQualification.CQC_GOODS),
            Set.of(),
            Set.of(),
            Notes.empty());

    DriverMissionWorkReport report =
        DriverMissionWorkReport.builder("WR-002", "MIS-002", driver)
            .drivingTime(Duration.ofHours(4))
            .otherWorkTime(Duration.ofHours(1))
            .cargoCategories(Set.of(CargoCategory.GENERAL))
            .vehicleContext(VehicleCombinationType.SINGLE_VEHICLE, VehicleBodyBaseType.DRY_BOX)
            .build();

    DriverMissionPayroll payroll =
        DriverPayrollRules.calculateMissionPayroll(
            "PAY-002", report, simplePolicy(), Notes.empty());
    MissionPayrollProjection projection =
        MissionPayrollProjection.fromPayroll(payroll, "DRV_COST_002", Notes.empty());
    MissionCostLine costLine = projection.getMissionCostLine();

    assertEquals(MissionCostType.DRIVER_WAGE, costLine.getType());
    assertEquals(payroll.calculateTotalEmployerCost(), costLine.getAmount());
    assertTrue(DriverPayrollRules.missionHasDriverCost(projection));
  }

  private static DriverPayrollPolicy realisticPolicy() {
    return DriverPayrollPolicy.of(
        "PAYPOL-2026",
        "Politica paga trasporto pesante 2026",
        LocalDate.of(2026, 1, 1),
        null,
        List.of(
            amount(
                "BASE_DRIVE",
                DriverPayComponentType.BASE_DRIVING_TIME,
                DriverPayUnit.PER_HOUR,
                "Guida ordinaria",
                "20.00"),
            amount(
                "BASE_WORK",
                DriverPayComponentType.BASE_OTHER_WORK,
                DriverPayUnit.PER_HOUR,
                "Lavoro non guida",
                "18.00"),
            amount(
                "WAITING",
                DriverPayComponentType.WAITING_TIME,
                DriverPayUnit.PER_HOUR,
                "Attesa",
                "12.00"),
            amount(
                "LOAD_UNLOAD",
                DriverPayComponentType.LOADING_UNLOADING,
                DriverPayUnit.PER_HOUR,
                "Carico scarico",
                "18.00"),
            amount(
                "OVERTIME",
                DriverPayComponentType.OVERTIME,
                DriverPayUnit.PER_HOUR,
                "Straordinario",
                "30.00"),
            amount(
                "NIGHT",
                DriverPayComponentType.NIGHT_WORK,
                DriverPayUnit.PER_HOUR,
                "Maggiorazione notturna",
                "8.00"),
            amount(
                "INT_ALLOW",
                DriverPayComponentType.INTERNATIONAL_DAILY_ALLOWANCE,
                DriverPayUnit.PER_DAY,
                "Diaria estera",
                "60.00"),
            amount(
                "OVERNIGHT",
                DriverPayComponentType.OVERNIGHT_ALLOWANCE,
                DriverPayUnit.PER_DAY,
                "Pernottamento",
                "45.00"),
            amount(
                "CE_PREM",
                DriverPayComponentType.LICENSE_CE_PREMIUM,
                DriverPayUnit.PER_MISSION,
                "Premio CE",
                "25.00"),
            amount(
                "CQC_PREM",
                DriverPayComponentType.CQC_GOODS_PREMIUM,
                DriverPayUnit.PER_MISSION,
                "Premio CQC merci",
                "10.00"),
            amount(
                "ADR_BASIC",
                DriverPayComponentType.ADR_BASIC_PREMIUM,
                DriverPayUnit.PER_HOUR,
                "Premio ADR base",
                "5.00"),
            amount(
                "ADR_TANK",
                DriverPayComponentType.ADR_TANK_PREMIUM,
                DriverPayUnit.PER_HOUR,
                "Premio ADR cisterna",
                "4.00"),
            amount(
                "DANGER_WASTE",
                DriverPayComponentType.DANGEROUS_WASTE_PREMIUM,
                DriverPayUnit.PER_HOUR,
                "Premio rifiuti pericolosi",
                "6.00"),
            amount(
                "WASTE",
                DriverPayComponentType.WASTE_TRANSPORT_PREMIUM,
                DriverPayUnit.PER_HOUR,
                "Premio trasporto rifiuti",
                "3.00"),
            amount(
                "ARTIC",
                DriverPayComponentType.ARTICULATED_VEHICLE_PREMIUM,
                DriverPayUnit.PER_MISSION,
                "Premio articolato",
                "20.00"),
            amount(
                "SEMI",
                DriverPayComponentType.SEMI_TRAILER_PREMIUM,
                DriverPayUnit.PER_MISSION,
                "Premio semirimorchio",
                "15.00"),
            amount(
                "TANK_BODY",
                DriverPayComponentType.TANK_BODY_PREMIUM,
                DriverPayUnit.PER_MISSION,
                "Premio cisterna",
                "18.00"),
            percentage(
                "CONTRIB",
                DriverPayComponentType.SOCIAL_CONTRIBUTIONS,
                "Contributi aziendali",
                "25"),
            percentage(
                "INS_EMP",
                DriverPayComponentType.EMPLOYER_INSURANCE,
                "Assicurazione dipendente",
                "5")),
        Notes.empty());
  }

  private static DriverPayrollPolicy simplePolicy() {
    return DriverPayrollPolicy.of(
        "PAYPOL-SIMPLE",
        "Politica paga semplice",
        LocalDate.of(2026, 1, 1),
        null,
        List.of(
            amount(
                "BASE_DRIVE",
                DriverPayComponentType.BASE_DRIVING_TIME,
                DriverPayUnit.PER_HOUR,
                "Guida ordinaria",
                "20.00"),
            amount(
                "BASE_WORK",
                DriverPayComponentType.BASE_OTHER_WORK,
                DriverPayUnit.PER_HOUR,
                "Lavoro non guida",
                "18.00"),
            percentage(
                "CONTRIB",
                DriverPayComponentType.SOCIAL_CONTRIBUTIONS,
                "Contributi aziendali",
                "30")),
        Notes.empty());
  }

  private static DriverPayRule amount(
      String code,
      DriverPayComponentType type,
      DriverPayUnit unit,
      String description,
      String amount) {
    return DriverPayRule.amount(
        code, type, unit, description, Money.of(amount, "EUR"), Notes.empty());
  }

  private static DriverPayRule percentage(
      String code, DriverPayComponentType type, String description, String percentage) {
    return DriverPayRule.percentageOfBase(
        code, type, description, Percentage.of(percentage), Notes.empty());
  }
}
