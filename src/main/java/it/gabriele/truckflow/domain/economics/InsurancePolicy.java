package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Polizza assicurativa collegata a un mezzo, rimorchio o attività aziendale.
 */
public final class InsurancePolicy {

    private static final int MAX_CODE_LENGTH = 50;
    private static final int MAX_PROVIDER_LENGTH = 100;

    private final String policyNumber;
    private final String providerName;
    private final String insuredAssetCode;
    private final DateRange coveragePeriod;
    private final Money annualPremium;
    private final Money deductible;
    private final Notes notes;

    private InsurancePolicy(
            String policyNumber,
            String providerName,
            String insuredAssetCode,
            DateRange coveragePeriod,
            Money annualPremium,
            Money deductible,
            Notes notes
    ) {
        this.policyNumber = validateCode(policyNumber, "Il numero polizza è obbligatorio.");
        this.providerName = validateProviderName(providerName);
        this.insuredAssetCode = validateCode(insuredAssetCode, "Il codice bene assicurato è obbligatorio.");
        if (coveragePeriod == null) {
            throw new IllegalArgumentException("Il periodo copertura è obbligatorio.");
        }
        if (annualPremium == null) {
            throw new IllegalArgumentException("Il premio annuo è obbligatorio.");
        }
        if (annualPremium.getAmount().signum() == 0) {
            throw new IllegalArgumentException("Il premio annuo deve essere maggiore di zero.");
        }
        if (deductible == null) {
            throw new IllegalArgumentException("La franchigia è obbligatoria.");
        }
        annualPremium.add(deductible);
        if (notes == null) {
            throw new IllegalArgumentException("Le note polizza sono obbligatorie.");
        }
        this.coveragePeriod = coveragePeriod;
        this.annualPremium = annualPremium;
        this.deductible = deductible;
        this.notes = notes;
    }

    public static InsurancePolicy of(
            String policyNumber,
            String providerName,
            String insuredAssetCode,
            DateRange coveragePeriod,
            Money annualPremium,
            Money deductible,
            Notes notes
    ) {
        return new InsurancePolicy(policyNumber, providerName, insuredAssetCode, coveragePeriod,
                annualPremium, deductible, notes);
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

    private static String validateProviderName(String providerName) {
        if (providerName == null) {
            throw new IllegalArgumentException("Il nome compagnia assicurativa è obbligatorio.");
        }
        String normalized = providerName.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Il nome compagnia assicurativa non può essere vuoto.");
        }
        if (normalized.length() > MAX_PROVIDER_LENGTH) {
            throw new IllegalArgumentException("Il nome compagnia assicurativa non può superare "
                    + MAX_PROVIDER_LENGTH + " caratteri.");
        }
        return normalized;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getInsuredAssetCode() {
        return insuredAssetCode;
    }

    public DateRange getCoveragePeriod() {
        return coveragePeriod;
    }

    public Money getAnnualPremium() {
        return annualPremium;
    }

    public Money getDeductible() {
        return deductible;
    }

    public Notes getNotes() {
        return notes;
    }

    public boolean covers(LocalDate date) {
        return coveragePeriod.contains(date);
    }

    public Money calculateDailyPremiumCost(long days) {
        if (days < 0) {
            throw new IllegalArgumentException("I giorni premio non possono essere negativi.");
        }
        BigDecimal amount = annualPremium.getAmount()
                .multiply(BigDecimal.valueOf(days))
                .divide(BigDecimal.valueOf(365), 2, RoundingMode.HALF_UP);
        return Money.of(amount, annualPremium.getCurrency());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InsurancePolicy that)) return false;
        return policyNumber.equals(that.policyNumber)
                && providerName.equals(that.providerName)
                && insuredAssetCode.equals(that.insuredAssetCode)
                && coveragePeriod.equals(that.coveragePeriod)
                && annualPremium.equals(that.annualPremium)
                && deductible.equals(that.deductible)
                && notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyNumber, providerName, insuredAssetCode, coveragePeriod, annualPremium, deductible, notes);
    }
}
