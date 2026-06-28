package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Sintesi economica di un periodo: ricavi, costi operativi, acquisti, ammortamenti e risultato cassa.
 */
public final class FleetFinancialStatement {

    private static final int MAX_CODE_LENGTH = 50;

    private final String statementNumber;
    private final DateRange period;
    private final List<MissionEconomics> missions;
    private final List<SupplierInvoice> supplierInvoices;
    private final List<FleetAssetPurchase> assetPurchases;
    private final Notes notes;

    private FleetFinancialStatement(
            String statementNumber,
            DateRange period,
            List<MissionEconomics> missions,
            List<SupplierInvoice> supplierInvoices,
            List<FleetAssetPurchase> assetPurchases,
            Notes notes
    ) {
        this.statementNumber = validateCode(statementNumber, "Il numero prospetto economico è obbligatorio.");
        if (period == null) {
            throw new IllegalArgumentException("Il periodo prospetto economico è obbligatorio.");
        }
        this.missions = validateList(missions, "Le missioni economiche sono obbligatorie.");
        this.supplierInvoices = validateList(supplierInvoices, "Le fatture fornitore sono obbligatorie.");
        this.assetPurchases = validateList(assetPurchases, "Gli acquisti beni sono obbligatori.");
        if (this.missions.isEmpty() && this.supplierInvoices.isEmpty() && this.assetPurchases.isEmpty()) {
            throw new IllegalArgumentException("Il prospetto economico deve contenere almeno un movimento.");
        }
        if (notes == null) {
            throw new IllegalArgumentException("Le note prospetto economico sono obbligatorie.");
        }
        validateCurrencyCompatibility(this.missions, this.supplierInvoices, this.assetPurchases);
        this.period = period;
        this.notes = notes;
    }

    public static FleetFinancialStatement of(
            String statementNumber,
            DateRange period,
            List<MissionEconomics> missions,
            List<SupplierInvoice> supplierInvoices,
            List<FleetAssetPurchase> assetPurchases,
            Notes notes
    ) {
        return new FleetFinancialStatement(statementNumber, period, missions, supplierInvoices, assetPurchases, notes);
    }

    private static String validateCode(String code, String nullMessage) {
        if (code == null) {
            throw new IllegalArgumentException(nullMessage);
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }
        if (normalized.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice può contenere solo lettere, numeri, trattini e underscore.");
        }
        return normalized;
    }

    private static <T> List<T> validateList(List<T> values, String nullMessage) {
        if (values == null) {
            throw new IllegalArgumentException(nullMessage);
        }
        if (values.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Le liste del prospetto economico non possono contenere null.");
        }
        return List.copyOf(values);
    }

    private static void validateCurrencyCompatibility(
            List<MissionEconomics> missions,
            List<SupplierInvoice> invoices,
            List<FleetAssetPurchase> assets
    ) {
        Money reference = findReferenceMoney(missions, invoices, assets);
        if (reference == null) {
            return;
        }
        for (MissionEconomics mission : missions) {
            reference.add(mission.calculateTotalRevenue());
            reference.add(mission.calculateTotalCosts());
        }
        for (SupplierInvoice invoice : invoices) {
            reference.add(invoice.calculateTotal());
        }
        for (FleetAssetPurchase asset : assets) {
            reference.add(asset.getPurchasePrice());
        }
    }

    private static Money findReferenceMoney(
            List<MissionEconomics> missions,
            List<SupplierInvoice> invoices,
            List<FleetAssetPurchase> assets
    ) {
        if (!missions.isEmpty()) {
            return missions.get(0).calculateTotalRevenue();
        }
        if (!invoices.isEmpty()) {
            return invoices.get(0).calculateTotal();
        }
        if (!assets.isEmpty()) {
            return assets.get(0).getPurchasePrice();
        }
        return null;
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public DateRange getPeriod() {
        return period;
    }

    public List<MissionEconomics> getMissions() {
        return missions;
    }

    public List<SupplierInvoice> getSupplierInvoices() {
        return supplierInvoices;
    }

    public List<FleetAssetPurchase> getAssetPurchases() {
        return assetPurchases;
    }

    public Notes getNotes() {
        return notes;
    }

    public Money calculateTotalRevenue() {
        Currency currency = referenceCurrency();
        if (missions.isEmpty()) {
            return Money.of(BigDecimal.ZERO, currency);
        }
        Money total = missions.get(0).calculateTotalRevenue();
        for (int i = 1; i < missions.size(); i++) {
            total = total.add(missions.get(i).calculateTotalRevenue());
        }
        return total;
    }

    public Money calculateMissionCosts() {
        Currency currency = referenceCurrency();
        Money total = Money.of(BigDecimal.ZERO, currency);
        for (MissionEconomics mission : missions) {
            total = total.add(mission.calculateTotalCosts());
        }
        return total;
    }

    public Money calculateSupplierInvoiceTotal() {
        Currency currency = referenceCurrency();
        Money total = Money.of(BigDecimal.ZERO, currency);
        for (SupplierInvoice invoice : supplierInvoices) {
            total = total.add(invoice.calculateTotal());
        }
        return total;
    }

    public Money calculateAssetInvestmentTotal() {
        Currency currency = referenceCurrency();
        Money total = Money.of(BigDecimal.ZERO, currency);
        for (FleetAssetPurchase asset : assetPurchases) {
            total = total.add(asset.getPurchasePrice());
        }
        return total;
    }

    public Money calculateDepreciationForPeriod() {
        Currency currency = referenceCurrency();
        Money total = Money.of(BigDecimal.ZERO, currency);
        long days = period.daysInclusive();
        for (FleetAssetPurchase asset : assetPurchases) {
            total = total.add(asset.calculateDepreciationForDays(days));
        }
        return total;
    }

    public ProfitabilityResult calculateOperatingProfitability() {
        Money costs = calculateMissionCosts()
                .add(calculateSupplierInvoiceTotal())
                .add(calculateDepreciationForPeriod());
        return ProfitabilityResult.of(calculateTotalRevenue(), costs);
    }

    /**
     * Risultato di cassa: qui gli acquisti di camion/rimorchi/allestimenti pesano subito come uscita di denaro.
     */
    public FinancialBalance calculateCashResult() {
        return FinancialBalance.from(calculateTotalRevenue())
                .subtract(calculateMissionCosts())
                .subtract(calculateSupplierInvoiceTotal())
                .subtract(calculateAssetInvestmentTotal());
    }

    public boolean isCashNegative() {
        return calculateCashResult().isNegative();
    }

    public Money calculateDebtAmount() {
        FinancialBalance cashResult = calculateCashResult();
        if (!cashResult.isNegative()) {
            return Money.of(BigDecimal.ZERO, referenceCurrency());
        }
        return cashResult.absoluteMoney();
    }

    private Currency referenceCurrency() {
        Money reference = findReferenceMoney(missions, supplierInvoices, assetPurchases);
        if (reference == null) {
            return Currency.getInstance("EUR");
        }
        return reference.getCurrency();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FleetFinancialStatement that)) return false;
        return statementNumber.equals(that.statementNumber)
                && period.equals(that.period)
                && missions.equals(that.missions)
                && supplierInvoices.equals(that.supplierInvoices)
                && assetPurchases.equals(that.assetPurchases)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statementNumber, period, missions, supplierInvoices, assetPurchases, notes);
    }
}
