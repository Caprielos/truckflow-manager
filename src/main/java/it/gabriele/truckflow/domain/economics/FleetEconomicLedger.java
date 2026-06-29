package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/**
 * Libro economico reale di periodo.
 * Confronta ricavi, costi operativi, acquisti, IVA, spese ricorrenti, finanziamenti e cassa.
 */
public final class FleetEconomicLedger {

    private static final int MAX_CODE_LENGTH = 50;

    private final String ledgerNumber;
    private final DateRange period;
    private final Money openingCash;
    private final List<CustomerRevenueInvoice> customerInvoices;
    private final List<MissionEconomics> missions;
    private final List<SupplierInvoice> supplierInvoices;
    private final List<FleetAssetAcquisition> assetAcquisitions;
    private final List<RecurringExpense> recurringExpenses;
    private final List<FinancingAgreement> financingAgreements;
    private final Notes notes;

    private FleetEconomicLedger(
            String ledgerNumber,
            DateRange period,
            Money openingCash,
            List<CustomerRevenueInvoice> customerInvoices,
            List<MissionEconomics> missions,
            List<SupplierInvoice> supplierInvoices,
            List<FleetAssetAcquisition> assetAcquisitions,
            List<RecurringExpense> recurringExpenses,
            List<FinancingAgreement> financingAgreements,
            Notes notes
    ) {
        this.ledgerNumber = validateCode(ledgerNumber, "Il numero libro economico è obbligatorio.");
        if (period == null) {
            throw new IllegalArgumentException("Il periodo libro economico è obbligatorio.");
        }
        if (openingCash == null) {
            throw new IllegalArgumentException("La cassa iniziale è obbligatoria.");
        }
        this.customerInvoices = validateList(customerInvoices, "Le fatture cliente sono obbligatorie.");
        this.missions = validateList(missions, "Le missioni economiche sono obbligatorie.");
        this.supplierInvoices = validateList(supplierInvoices, "Le fatture fornitore sono obbligatorie.");
        this.assetAcquisitions = validateList(assetAcquisitions, "Gli acquisti flotta sono obbligatori.");
        this.recurringExpenses = validateList(recurringExpenses, "Le spese ricorrenti sono obbligatorie.");
        this.financingAgreements = validateList(financingAgreements, "I finanziamenti sono obbligatori.");
        if (notes == null) {
            throw new IllegalArgumentException("Le note libro economico sono obbligatorie.");
        }
        validateCurrencyCompatibility(openingCash, this.customerInvoices, this.missions, this.supplierInvoices,
                this.assetAcquisitions, this.recurringExpenses, this.financingAgreements);
        this.period = period;
        this.openingCash = openingCash;
        this.notes = notes;
    }

    public static FleetEconomicLedger of(
            String ledgerNumber,
            DateRange period,
            Money openingCash,
            List<CustomerRevenueInvoice> customerInvoices,
            List<MissionEconomics> missions,
            List<SupplierInvoice> supplierInvoices,
            List<FleetAssetAcquisition> assetAcquisitions,
            List<RecurringExpense> recurringExpenses,
            List<FinancingAgreement> financingAgreements,
            Notes notes
    ) {
        return new FleetEconomicLedger(ledgerNumber, period, openingCash, customerInvoices, missions, supplierInvoices,
                assetAcquisitions, recurringExpenses, financingAgreements, notes);
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
            throw new IllegalArgumentException("Le liste del libro economico non possono contenere null.");
        }
        return List.copyOf(values);
    }

    private static void validateCurrencyCompatibility(
            Money openingCash,
            List<CustomerRevenueInvoice> customerInvoices,
            List<MissionEconomics> missions,
            List<SupplierInvoice> supplierInvoices,
            List<FleetAssetAcquisition> assetAcquisitions,
            List<RecurringExpense> recurringExpenses,
            List<FinancingAgreement> financingAgreements
    ) {
        Money reference = openingCash;
        for (CustomerRevenueInvoice invoice : customerInvoices) {
            reference.add(invoice.calculateGrossTotal());
        }
        for (MissionEconomics mission : missions) {
            reference.add(mission.calculateTotalRevenue());
            reference.add(mission.calculateTotalCosts());
        }
        for (SupplierInvoice invoice : supplierInvoices) {
            reference.add(invoice.calculateTotal());
        }
        for (FleetAssetAcquisition acquisition : assetAcquisitions) {
            reference.add(acquisition.calculateGrossTotal());
        }
        for (RecurringExpense expense : recurringExpenses) {
            reference.add(expense.calculateGrossAmount());
        }
        for (FinancingAgreement agreement : financingAgreements) {
            reference.add(agreement.calculateTotalCashOutflow());
        }
    }

    public String getLedgerNumber() {
        return ledgerNumber;
    }

    public DateRange getPeriod() {
        return period;
    }

    public Money getOpeningCash() {
        return openingCash;
    }

    public List<CustomerRevenueInvoice> getCustomerInvoices() {
        return customerInvoices;
    }

    public List<MissionEconomics> getMissions() {
        return missions;
    }

    public List<SupplierInvoice> getSupplierInvoices() {
        return supplierInvoices;
    }

    public List<FleetAssetAcquisition> getAssetAcquisitions() {
        return assetAcquisitions;
    }

    public List<RecurringExpense> getRecurringExpenses() {
        return recurringExpenses;
    }

    public List<FinancingAgreement> getFinancingAgreements() {
        return financingAgreements;
    }

    public Notes getNotes() {
        return notes;
    }

    public Money calculateCustomerRevenueNet() {
        if (!customerInvoices.isEmpty()) {
            return sum(customerInvoices.stream().map(CustomerRevenueInvoice::calculateNetTotal).toList());
        }
        return sum(missions.stream().map(MissionEconomics::calculateTotalRevenue).toList());
    }

    public Money calculateCustomerGrossInvoiced() {
        if (!customerInvoices.isEmpty()) {
            return sum(customerInvoices.stream().map(CustomerRevenueInvoice::calculateGrossTotal).toList());
        }
        return calculateCustomerRevenueNet();
    }

    public Money calculateSalesVatCollected() {
        return sum(customerInvoices.stream().map(CustomerRevenueInvoice::calculateVatTotal).toList());
    }

    public Money calculateMissionCosts() {
        return sum(missions.stream().map(MissionEconomics::calculateTotalCosts).toList());
    }

    public Money calculateSupplierInvoiceGrossTotal() {
        return sum(supplierInvoices.stream().map(SupplierInvoice::calculateTotal).toList());
    }

    public Money calculateSupplierInvoiceAccountingCost() {
        return sum(supplierInvoices.stream().map(SupplierInvoice::calculateAccountingCostTotal).toList());
    }

    public Money calculateSupplierRecoverableVat() {
        return sum(supplierInvoices.stream().map(SupplierInvoice::calculateRecoverableVatTotal).toList());
    }

    public Money calculateAssetAcquisitionGrossTotal() {
        return sum(assetAcquisitions.stream().map(FleetAssetAcquisition::calculateGrossTotal).toList());
    }

    public Money calculateAssetAccountingCost() {
        return sum(assetAcquisitions.stream().map(FleetAssetAcquisition::calculateAccountingCostTotal).toList());
    }

    public Money calculateAssetRecoverableVat() {
        return sum(assetAcquisitions.stream().map(FleetAssetAcquisition::calculateRecoverableVatTotal).toList());
    }

    public Money calculateRecurringGrossTotal() {
        return sum(recurringExpenses.stream()
                .filter(expense -> expense.overlaps(period))
                .map(RecurringExpense::calculateGrossAmount)
                .toList());
    }

    public Money calculateRecurringAccountingCost() {
        return sum(recurringExpenses.stream()
                .filter(expense -> expense.overlaps(period))
                .map(RecurringExpense::calculateAccountingCost)
                .toList());
    }

    public Money calculateRecurringRecoverableVat() {
        return sum(recurringExpenses.stream()
                .filter(expense -> expense.overlaps(period))
                .map(RecurringExpense::calculateRecoverableVatAmount)
                .toList());
    }

    public Money calculateFinancingCashOutflow() {
        return sum(financingAgreements.stream().map(FinancingAgreement::calculateTotalCashOutflow).toList());
    }

    public Money calculateFinancingCost() {
        return sum(financingAgreements.stream().map(FinancingAgreement::calculateFinanceCost).toList());
    }

    public Money calculateRecoverableVatTotal() {
        return calculateSupplierRecoverableVat()
                .add(calculateAssetRecoverableVat())
                .add(calculateRecurringRecoverableVat());
    }

    public FinancialBalance calculateVatPosition() {
        return FinancialBalance.from(calculateSalesVatCollected()).subtract(calculateRecoverableVatTotal());
    }

    public boolean hasVatDebt() {
        return calculateVatPosition().isPositive();
    }

    public boolean hasVatCredit() {
        return calculateVatPosition().isNegative();
    }

    /**
     * Risultato gestionale: usa imponibile ricavi e costi contabili, non confonde IVA con guadagno.
     */
    public ProfitabilityResult calculateAccountingProfitability() {
        Money costs = calculateMissionCosts()
                .add(calculateSupplierInvoiceAccountingCost())
                .add(calculateRecurringAccountingCost())
                .add(calculateFinancingCost());
        return ProfitabilityResult.of(calculateCustomerRevenueNet(), costs);
    }

    /**
     * Risultato di cassa: considera quello che entra/esce davvero dal conto, incluso lordo IVA e acquisti.
     */
    public FinancialBalance calculateCashResult() {
        return FinancialBalance.from(openingCash)
                .add(calculateCustomerGrossInvoiced())
                .subtract(calculateMissionCosts())
                .subtract(calculateSupplierInvoiceGrossTotal())
                .subtract(calculateAssetAcquisitionGrossTotal())
                .subtract(calculateRecurringGrossTotal())
                .subtract(calculateFinancingCashOutflow());
    }

    public boolean isCashNegative() {
        return calculateCashResult().isNegative();
    }

    public Money calculateDebtAmount() {
        FinancialBalance result = calculateCashResult();
        if (!result.isNegative()) {
            return Money.of(BigDecimal.ZERO, openingCash.getCurrency());
        }
        return result.absoluteMoney();
    }

    private Money sum(List<Money> amounts) {
        Currency currency = openingCash.getCurrency();
        Money total = Money.of(BigDecimal.ZERO, currency);
        for (Money amount : amounts) {
            total = total.add(amount);
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FleetEconomicLedger that)) return false;
        return ledgerNumber.equals(that.ledgerNumber)
                && period.equals(that.period)
                && openingCash.equals(that.openingCash)
                && customerInvoices.equals(that.customerInvoices)
                && missions.equals(that.missions)
                && supplierInvoices.equals(that.supplierInvoices)
                && assetAcquisitions.equals(that.assetAcquisitions)
                && recurringExpenses.equals(that.recurringExpenses)
                && financingAgreements.equals(that.financingAgreements)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ledgerNumber, period, openingCash, customerInvoices, missions, supplierInvoices,
                assetAcquisitions, recurringExpenses, financingAgreements, notes);
    }
}
