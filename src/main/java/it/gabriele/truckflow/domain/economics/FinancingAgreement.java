package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.Percentage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Finanziamento o leasing collegato a un bene flotta: anticipo, rata, interessi e maxi rata finale.
 */
public final class FinancingAgreement {

    private static final int MAX_CODE_LENGTH = 50;

    private final String agreementNumber;
    private final String assetCode;
    private final String providerCode;
    private final LocalDate startDate;
    private final int numberOfInstallments;
    private final Money financedAmount;
    private final Money downPayment;
    private final Money installmentAmount;
    private final Money finalBalloonPayment;
    private final Percentage annualInterestRate;
    private final Notes notes;

    private FinancingAgreement(
            String agreementNumber,
            String assetCode,
            String providerCode,
            LocalDate startDate,
            int numberOfInstallments,
            Money financedAmount,
            Money downPayment,
            Money installmentAmount,
            Money finalBalloonPayment,
            Percentage annualInterestRate,
            Notes notes
    ) {
        this.agreementNumber = validateCode(agreementNumber, "Il numero finanziamento è obbligatorio.");
        this.assetCode = validateCode(assetCode, "Il codice bene finanziato è obbligatorio.");
        this.providerCode = validateCode(providerCode, "Il codice finanziaria/leasing è obbligatorio.");
        if (startDate == null) {
            throw new IllegalArgumentException("La data inizio finanziamento è obbligatoria.");
        }
        if (numberOfInstallments <= 0) {
            throw new IllegalArgumentException("Il numero rate deve essere positivo.");
        }
        if (financedAmount == null || financedAmount.getAmount().signum() == 0) {
            throw new IllegalArgumentException("L'importo finanziato deve essere maggiore di zero.");
        }
        if (downPayment == null) {
            throw new IllegalArgumentException("L'anticipo finanziamento è obbligatorio.");
        }
        if (installmentAmount == null || installmentAmount.getAmount().signum() == 0) {
            throw new IllegalArgumentException("La rata finanziamento deve essere maggiore di zero.");
        }
        if (finalBalloonPayment == null) {
            throw new IllegalArgumentException("La maxi rata finale è obbligatoria.");
        }
        if (annualInterestRate == null) {
            throw new IllegalArgumentException("Il tasso annuo è obbligatorio.");
        }
        financedAmount.add(downPayment);
        financedAmount.add(installmentAmount);
        financedAmount.add(finalBalloonPayment);
        if (notes == null) {
            throw new IllegalArgumentException("Le note finanziamento sono obbligatorie.");
        }
        this.startDate = startDate;
        this.numberOfInstallments = numberOfInstallments;
        this.financedAmount = financedAmount;
        this.downPayment = downPayment;
        this.installmentAmount = installmentAmount;
        this.finalBalloonPayment = finalBalloonPayment;
        this.annualInterestRate = annualInterestRate;
        this.notes = notes;
    }

    public static FinancingAgreement of(
            String agreementNumber,
            String assetCode,
            String providerCode,
            LocalDate startDate,
            int numberOfInstallments,
            Money financedAmount,
            Money downPayment,
            Money installmentAmount,
            Money finalBalloonPayment,
            Percentage annualInterestRate,
            Notes notes
    ) {
        return new FinancingAgreement(agreementNumber, assetCode, providerCode, startDate, numberOfInstallments,
                financedAmount, downPayment, installmentAmount, finalBalloonPayment, annualInterestRate, notes);
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

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public Money getFinancedAmount() {
        return financedAmount;
    }

    public Money getDownPayment() {
        return downPayment;
    }

    public Money getInstallmentAmount() {
        return installmentAmount;
    }

    public Money getFinalBalloonPayment() {
        return finalBalloonPayment;
    }

    public Percentage getAnnualInterestRate() {
        return annualInterestRate;
    }

    public Notes getNotes() {
        return notes;
    }

    public Money calculateInstallmentTotal() {
        return Money.of(installmentAmount.getAmount().multiply(BigDecimal.valueOf(numberOfInstallments)),
                installmentAmount.getCurrency());
    }

    public Money calculateTotalCashOutflow() {
        return downPayment.add(calculateInstallmentTotal()).add(finalBalloonPayment);
    }

    public Money calculateFinanceCost() {
        Money paid = calculateTotalCashOutflow();
        if (paid.isLessThanOrEqualTo(financedAmount)) {
            return Money.of(BigDecimal.ZERO, financedAmount.getCurrency());
        }
        return paid.subtract(financedAmount);
    }

    public LocalDate calculateEndDate() {
        return startDate.plusMonths(numberOfInstallments);
    }

    public boolean isActiveOn(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("La data da verificare è obbligatoria.");
        }
        return !date.isBefore(startDate) && date.isBefore(calculateEndDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinancingAgreement that)) return false;
        return numberOfInstallments == that.numberOfInstallments
                && agreementNumber.equals(that.agreementNumber)
                && assetCode.equals(that.assetCode)
                && providerCode.equals(that.providerCode)
                && startDate.equals(that.startDate)
                && financedAmount.equals(that.financedAmount)
                && downPayment.equals(that.downPayment)
                && installmentAmount.equals(that.installmentAmount)
                && finalBalloonPayment.equals(that.finalBalloonPayment)
                && annualInterestRate.equals(that.annualInterestRate)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agreementNumber, assetCode, providerCode, startDate, numberOfInstallments, financedAmount,
                downPayment, installmentAmount, finalBalloonPayment, annualInterestRate, notes);
    }
}
