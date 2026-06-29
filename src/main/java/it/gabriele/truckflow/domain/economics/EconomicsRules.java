package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Percentage;

/**
 * Regole economiche: convenienza missione, perdita, debito/cassa negativa, margine minimo.
 */
public final class EconomicsRules {

    private EconomicsRules() {
    }

    public static boolean isMissionProfitable(MissionEconomics economics) {
        validateMission(economics);
        return economics.calculateProfitability().isProfitable();
    }

    public static boolean isMissionLossMaking(MissionEconomics economics) {
        validateMission(economics);
        return economics.calculateProfitability().isLossMaking();
    }

    public static boolean missionMeetsMinimumMargin(MissionEconomics economics, Percentage minimumMargin) {
        validateMission(economics);
        if (minimumMargin == null) {
            throw new IllegalArgumentException("Il margine minimo è obbligatorio.");
        }
        ProfitabilityResult result = economics.calculateProfitability();
        return result.isProfitable()
                && !result.getMarginPercentage().isLessThanOrEqualTo(minimumMargin);
    }

    public static boolean shouldReviewMissionBeforeAcceptance(MissionEconomics economics, Percentage minimumMargin) {
        validateMission(economics);
        if (minimumMargin == null) {
            throw new IllegalArgumentException("Il margine minimo è obbligatorio.");
        }
        ProfitabilityResult result = economics.calculateProfitability();
        return result.getStatus().isCritical()
                || result.getMarginPercentage().isLessThanOrEqualTo(minimumMargin);
    }

    public static boolean isCompanyCashNegative(FleetFinancialStatement statement) {
        validateStatement(statement);
        return statement.isCashNegative();
    }

    public static boolean operatingPeriodIsProfitable(FleetFinancialStatement statement) {
        validateStatement(statement);
        return statement.calculateOperatingProfitability().isProfitable();
    }


    public static boolean ledgerHasCashDebt(FleetEconomicLedger ledger) {
        validateLedger(ledger);
        return ledger.isCashNegative();
    }

    public static boolean ledgerHasVatDebt(FleetEconomicLedger ledger) {
        validateLedger(ledger);
        return ledger.hasVatDebt();
    }

    public static boolean ledgerHasVatCredit(FleetEconomicLedger ledger) {
        validateLedger(ledger);
        return ledger.hasVatCredit();
    }

    public static boolean ledgerIsLossMakingAfterAllKnownCosts(FleetEconomicLedger ledger) {
        validateLedger(ledger);
        return ledger.calculateAccountingProfitability().isLossMaking();
    }

    private static void validateMission(MissionEconomics economics) {
        if (economics == null) {
            throw new IllegalArgumentException("L'economics missione è obbligatorio.");
        }
    }


    private static void validateLedger(FleetEconomicLedger ledger) {
        if (ledger == null) {
            throw new IllegalArgumentException("Il libro economico è obbligatorio.");
        }
    }

    private static void validateStatement(FleetFinancialStatement statement) {
        if (statement == null) {
            throw new IllegalArgumentException("Il prospetto economico è obbligatorio.");
        }
    }
}
