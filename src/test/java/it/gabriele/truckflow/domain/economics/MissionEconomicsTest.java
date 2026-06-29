package it.gabriele.truckflow.domain.economics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Percentage;
import java.util.List;
import org.junit.jupiter.api.Test;

class MissionEconomicsTest {

  @Test
  void shouldCalculateProfitMarginForRealMission() {
    MissionEconomics economics =
        MissionEconomics.of(
            "MIS-001",
            "SHP-001",
            List.of(
                MissionRevenueLine.baseTransportFee(
                    "REV-BASE", "Trasporto Milano Roma", Money.of("1200", "EUR"), Notes.empty()),
                MissionRevenueLine.of(
                    "REV-FUEL",
                    MissionRevenueType.FUEL_SURCHARGE,
                    "Supplemento gasolio",
                    Money.of("90", "EUR"),
                    Notes.empty())),
            List.of(
                MissionCostLine.of(
                    "COST-FUEL",
                    MissionCostType.FUEL,
                    "Gasolio",
                    Money.of("310", "EUR"),
                    Notes.empty()),
                MissionCostLine.of(
                    "COST-TOLL",
                    MissionCostType.TOLL,
                    "Pedaggi",
                    Money.of("140", "EUR"),
                    Notes.empty()),
                MissionCostLine.of(
                    "COST-DRIVER",
                    MissionCostType.DRIVER_WAGE,
                    "Costo autista",
                    Money.of("190", "EUR"),
                    Notes.empty()),
                MissionCostLine.of(
                    "COST-TIRE",
                    MissionCostType.TIRE_WEAR,
                    "Quota usura gomme",
                    Money.of("45", "EUR"),
                    Notes.empty()),
                MissionCostLine.of(
                    "COST-INS",
                    MissionCostType.INSURANCE_QUOTA,
                    "Quota assicurazione",
                    Money.of("35", "EUR"),
                    Notes.empty())),
            Notes.empty());

    ProfitabilityResult result = economics.calculateProfitability();

    assertEquals(Money.of("1290", "EUR"), result.getTotalRevenue());
    assertEquals(Money.of("720", "EUR"), result.getTotalCosts());
    assertEquals(ProfitabilityStatus.PROFIT, result.getStatus());
    assertEquals(Percentage.of("44.19"), result.getMarginPercentage());
    assertTrue(EconomicsRules.isMissionProfitable(economics));
    assertFalse(EconomicsRules.shouldReviewMissionBeforeAcceptance(economics, Percentage.of("20")));
  }

  @Test
  void shouldDetectLossMakingMissionAndDebtAmount() {
    MissionEconomics economics =
        MissionEconomics.of(
            "MIS-LOSS",
            "SHP-LOSS",
            List.of(
                MissionRevenueLine.baseTransportFee(
                    "REV-BASE", "Trasporto sottocosto", Money.of("500", "EUR"), Notes.empty())),
            List.of(
                MissionCostLine.of(
                    "COST-FUEL",
                    MissionCostType.FUEL,
                    "Gasolio",
                    Money.of("420", "EUR"),
                    Notes.empty()),
                MissionCostLine.of(
                    "COST-TOLL",
                    MissionCostType.TOLL,
                    "Pedaggi",
                    Money.of("180", "EUR"),
                    Notes.empty())),
            Notes.empty());

    ProfitabilityResult result = economics.calculateProfitability();

    assertEquals(ProfitabilityStatus.LOSS, result.getStatus());
    assertTrue(result.isInDebt());
    assertEquals(Money.of("100", "EUR"), result.getDebtAmount());
    assertTrue(EconomicsRules.isMissionLossMaking(economics));
  }
}
