package it.gabriele.truckflow.domain.payroll;

import it.gabriele.truckflow.domain.driver.DriverAdrCertificateType;
import it.gabriele.truckflow.domain.driver.DriverLicenseCategory;
import it.gabriele.truckflow.domain.driver.DriverOperationalQualification;
import it.gabriele.truckflow.domain.driver.DriverProfessionalQualification;
import it.gabriele.truckflow.domain.fleet.VehicleBodyBaseType;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * Regole di calcolo del costo autista. Non decide importi per legge:
 * applica una politica configurata e attiva solo le voci coerenti con missione,
 * patenti, ADR, merce, allestimento e convoglio.
 */
public final class DriverPayrollRules {

    private DriverPayrollRules() {
    }

    public static DriverMissionPayroll calculateMissionPayroll(
            String payrollCode,
            DriverMissionWorkReport report,
            DriverPayrollPolicy policy,
            Notes notes
    ) {
        if (report == null) {
            throw new IllegalArgumentException("Il report lavoro autista è obbligatorio.");
        }
        if (policy == null) {
            throw new IllegalArgumentException("La politica paga autista è obbligatoria.");
        }
        if (notes == null) {
            throw new IllegalArgumentException("Le note payroll calcolato sono obbligatorie.");
        }

        List<DriverMissionPayLine> lines = new ArrayList<>();

        addAmountLine(lines, policy, DriverPayComponentType.BASE_DRIVING_TIME, report.getDrivingHours(), null);
        addAmountLine(lines, policy, DriverPayComponentType.BASE_OTHER_WORK, report.getOtherWorkHours(), null);
        addAmountLine(lines, policy, DriverPayComponentType.WAITING_TIME, report.getWaitingHours(), null);
        addAmountLine(lines, policy, DriverPayComponentType.LOADING_UNLOADING, report.getLoadingUnloadingHours(), null);
        addAmountLine(lines, policy, DriverPayComponentType.OVERTIME, report.getOvertimeHours(), null);
        addAmountLine(lines, policy, DriverPayComponentType.NIGHT_WORK, report.getNightWorkHours(), null);
        addAmountLine(lines, policy, DriverPayComponentType.SUNDAY_WORK, report.getSundayWorkHours(), null);
        addAmountLine(lines, policy, DriverPayComponentType.HOLIDAY_WORK, report.getHolidayWorkHours(), null);

        addAmountLine(lines, policy, DriverPayComponentType.DOMESTIC_DAILY_ALLOWANCE, BigDecimal.valueOf(report.getDomesticAllowanceDays()), null);
        addAmountLine(lines, policy, DriverPayComponentType.INTERNATIONAL_DAILY_ALLOWANCE, BigDecimal.valueOf(report.getInternationalAllowanceDays()), null);
        addAmountLine(lines, policy, DriverPayComponentType.OVERNIGHT_ALLOWANCE, BigDecimal.valueOf(report.getOvernightDays()), null);

        if (report.driverHasLicense(DriverLicenseCategory.C1)) {
            addAmountLine(lines, policy, DriverPayComponentType.LICENSE_C1_PREMIUM, BigDecimal.ONE, null);
        }
        if (report.driverHasLicense(DriverLicenseCategory.C)) {
            addAmountLine(lines, policy, DriverPayComponentType.LICENSE_C_PREMIUM, BigDecimal.ONE, null);
        }
        if (report.driverHasLicense(DriverLicenseCategory.CE)) {
            addAmountLine(lines, policy, DriverPayComponentType.LICENSE_CE_PREMIUM, BigDecimal.ONE, null);
        }
        if (report.driverHasProfessionalQualification(DriverProfessionalQualification.CQC_GOODS)) {
            addAmountLine(lines, policy, DriverPayComponentType.CQC_GOODS_PREMIUM, BigDecimal.ONE, null);
        }

        if (report.transportsAdrCargo() && report.driverHasAdrCertificate(DriverAdrCertificateType.ADR_BASIC)) {
            addAmountLine(lines, policy, DriverPayComponentType.ADR_BASIC_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsLiquidOrTankCargo() && report.driverHasAdrCertificate(DriverAdrCertificateType.ADR_TANK)) {
            addAmountLine(lines, policy, DriverPayComponentType.ADR_TANK_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.driverHasAdrCertificate(DriverAdrCertificateType.ADR_CLASS_1_EXPLOSIVES)) {
            addAmountLine(lines, policy, DriverPayComponentType.ADR_CLASS_1_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.driverHasAdrCertificate(DriverAdrCertificateType.ADR_CLASS_7_RADIOACTIVE)) {
            addAmountLine(lines, policy, DriverPayComponentType.ADR_CLASS_7_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsDangerousWaste()) {
            addAmountLine(lines, policy, DriverPayComponentType.DANGEROUS_WASTE_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsWaste() && report.driverHasOperationalQualification(DriverOperationalQualification.WASTE_TRANSPORT)) {
            addAmountLine(lines, policy, DriverPayComponentType.WASTE_TRANSPORT_PREMIUM, report.getDrivingHours(), null);
        }

        if (report.transportsTemperatureControlledCargo()
                && report.driverHasOperationalQualification(DriverOperationalQualification.TEMPERATURE_CONTROLLED_TRANSPORT)) {
            addAmountLine(lines, policy, DriverPayComponentType.REFRIGERATED_TRANSPORT_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsPharmaceuticals()) {
            addAmountLine(lines, policy, DriverPayComponentType.PHARMA_TRANSPORT_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsFoodGradeCargo()) {
            addAmountLine(lines, policy, DriverPayComponentType.FOOD_GRADE_TRANSPORT_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsLivestock() && report.driverHasOperationalQualification(DriverOperationalQualification.LIVE_ANIMAL_TRANSPORT)) {
            addAmountLine(lines, policy, DriverPayComponentType.LIVESTOCK_TRANSPORT_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsHighValueCargo()
                && report.driverHasOperationalQualification(DriverOperationalQualification.HIGH_VALUE_CARGO)) {
            addAmountLine(lines, policy, DriverPayComponentType.HIGH_VALUE_CARGO_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsOversizedCargo()
                && report.driverHasOperationalQualification(DriverOperationalQualification.OVERSIZED_CARGO)) {
            addAmountLine(lines, policy, DriverPayComponentType.OVERSIZED_CARGO_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsBulkCargo()
                && report.driverHasOperationalQualification(DriverOperationalQualification.BULK_TRANSPORT)) {
            addAmountLine(lines, policy, DriverPayComponentType.BULK_TRANSPORT_PREMIUM, report.getDrivingHours(), null);
        }
        if (report.transportsLiquidOrTankCargo()) {
            addAmountLine(lines, policy, DriverPayComponentType.LIQUID_TANKER_PREMIUM, report.getDrivingHours(), null);
        }

        if (report.usesArticulatedVehicle()) {
            addAmountLine(lines, policy, DriverPayComponentType.ARTICULATED_VEHICLE_PREMIUM, BigDecimal.ONE, null);
            addAmountLine(lines, policy, DriverPayComponentType.SEMI_TRAILER_PREMIUM, BigDecimal.ONE, null);
        }
        if (report.usesTruckAndTrailer()) {
            addAmountLine(lines, policy, DriverPayComponentType.TRUCK_AND_TRAILER_PREMIUM, BigDecimal.ONE, null);
            addAmountLine(lines, policy, DriverPayComponentType.DRAWBAR_TRAILER_PREMIUM, BigDecimal.ONE, null);
        }
        if (report.getBodyBaseType() == VehicleBodyBaseType.TANK) {
            addAmountLine(lines, policy, DriverPayComponentType.TANK_BODY_PREMIUM, BigDecimal.ONE, null);
        }
        if (report.usesTipperBody()) {
            addAmountLine(lines, policy, DriverPayComponentType.TIPPER_BODY_PREMIUM, BigDecimal.ONE, null);
        }
        if (report.usesLowLoaderBody()) {
            addAmountLine(lines, policy, DriverPayComponentType.LOW_LOADER_BODY_PREMIUM, BigDecimal.ONE, null);
        }
        if (report.usesRefrigeratedBody()) {
            addAmountLine(lines, policy, DriverPayComponentType.REFRIGERATED_BODY_PREMIUM, BigDecimal.ONE, null);
        }
        if (report.usesCrane()
                && report.driverHasOperationalQualification(DriverOperationalQualification.TRUCK_MOUNTED_CRANE)) {
            addAmountLine(lines, policy, DriverPayComponentType.CRANE_OPERATION_PREMIUM, report.getLoadingUnloadingHours(), null);
        }
        if (report.usesTailLift()) {
            addAmountLine(lines, policy, DriverPayComponentType.TAIL_LIFT_OPERATION_PREMIUM, report.getLoadingUnloadingHours(), null);
        }

        Money baseLabourCost = calculateBaseLabourCost(lines);
        addAmountLine(lines, policy, DriverPayComponentType.MEAL_REIMBURSEMENT, BigDecimal.ONE, null);
        addAmountLine(lines, policy, DriverPayComponentType.HOTEL_REIMBURSEMENT, BigDecimal.valueOf(report.getOvernightDays()), null);
        addAmountLine(lines, policy, DriverPayComponentType.PARKING_REIMBURSEMENT, BigDecimal.ONE, null);
        addPercentageLine(lines, policy, DriverPayComponentType.SOCIAL_CONTRIBUTIONS, baseLabourCost);
        addPercentageLine(lines, policy, DriverPayComponentType.EMPLOYER_INSURANCE, baseLabourCost);
        addPercentageLine(lines, policy, DriverPayComponentType.TRAINING_AMORTIZATION, baseLabourCost);
        addPercentageLine(lines, policy, DriverPayComponentType.HEALTH_SURVEILLANCE, baseLabourCost);

        if (lines.isEmpty()) {
            throw new IllegalArgumentException("La politica paga non ha prodotto righe: configurare almeno una regola applicabile.");
        }

        return DriverMissionPayroll.of(
                payrollCode,
                report.getMissionNumber(),
                report.getDriver().getDriverCode(),
                policy.getPolicyCode(),
                lines,
                notes
        );
    }

    public static boolean missionHasDriverCost(MissionPayrollProjection projection) {
        if (projection == null) {
            throw new IllegalArgumentException("La proiezione payroll è obbligatoria.");
        }
        return projection.getPayroll().calculateTotalEmployerCost().getAmount().signum() > 0;
    }

    private static void addAmountLine(
            List<DriverMissionPayLine> lines,
            DriverPayrollPolicy policy,
            DriverPayComponentType type,
            BigDecimal quantity,
            Money baseAmount
    ) {
        if (quantity.signum() == 0) {
            return;
        }
        policy.findRule(type)
                .filter(rule -> !rule.getUnit().usesPercentage())
                .map(rule -> rule.calculateLine(quantity, baseAmount))
                .ifPresent(lines::add);
    }

    private static void addPercentageLine(
            List<DriverMissionPayLine> lines,
            DriverPayrollPolicy policy,
            DriverPayComponentType type,
            Money baseAmount
    ) {
        if (baseAmount == null || baseAmount.getAmount().signum() == 0) {
            return;
        }
        policy.findRule(type)
                .filter(rule -> rule.getUnit().usesPercentage())
                .map(rule -> rule.calculateLine(BigDecimal.ONE, baseAmount))
                .ifPresent(lines::add);
    }

    private static Money calculateBaseLabourCost(List<DriverMissionPayLine> lines) {
        List<DriverMissionPayLine> baseLines = lines.stream()
                .filter(line -> !line.isAllowanceOrReimbursement())
                .filter(line -> !line.isEmployerCost())
                .toList();
        if (baseLines.isEmpty()) {
            return null;
        }
        Currency currency = baseLines.get(0).getAmount().getCurrency();
        Money total = Money.of(BigDecimal.ZERO, currency);
        for (DriverMissionPayLine line : baseLines) {
            total = total.add(line.getAmount());
        }
        return total;
    }
}
