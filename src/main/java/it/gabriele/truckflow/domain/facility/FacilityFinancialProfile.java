package it.gabriele.truckflow.domain.facility;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Profilo economico di una struttura fisica. Permette di distinguere una sede di proprietà da un
 * deposito in affitto.
 */
public final class FacilityFinancialProfile {

  private static final int MAX_FACILITY_CODE_LENGTH = 50;

  private final String facilityCode;
  private final FacilityOwnershipType ownershipType;
  private final Money purchasePrice;
  private final Money monthlyRent;
  private final Money depositAmount;
  private final List<FacilityCostLine> costLines;
  private final Notes notes;

  private FacilityFinancialProfile(
      String facilityCode,
      FacilityOwnershipType ownershipType,
      Money purchasePrice,
      Money monthlyRent,
      Money depositAmount,
      List<FacilityCostLine> costLines,
      Notes notes) {
    this.facilityCode = validateFacilityCode(facilityCode);
    this.ownershipType = validateOwnershipType(ownershipType);
    this.purchasePrice = purchasePrice;
    this.monthlyRent = monthlyRent;
    this.depositAmount = depositAmount;
    this.costLines = List.copyOf(validateCostLines(costLines));
    this.notes = validateNotes(notes);

    validateEconomicConsistency();
  }

  public static FacilityFinancialProfile owned(
      String facilityCode, Money purchasePrice, List<FacilityCostLine> costLines, Notes notes) {
    if (purchasePrice == null) {
      throw new IllegalArgumentException(
          "Il prezzo acquisto struttura di proprietà è obbligatorio.");
    }
    return new FacilityFinancialProfile(
        facilityCode, FacilityOwnershipType.OWNED, purchasePrice, null, null, costLines, notes);
  }

  public static FacilityFinancialProfile rented(
      String facilityCode,
      Money monthlyRent,
      Money depositAmount,
      List<FacilityCostLine> costLines,
      Notes notes) {
    if (monthlyRent == null) {
      throw new IllegalArgumentException("Il canone mensile struttura in affitto è obbligatorio.");
    }
    return new FacilityFinancialProfile(
        facilityCode,
        FacilityOwnershipType.RENTED,
        null,
        monthlyRent,
        depositAmount,
        costLines,
        notes);
  }

  public static FacilityFinancialProfile leased(
      String facilityCode,
      Money monthlyLease,
      Money depositAmount,
      List<FacilityCostLine> costLines,
      Notes notes) {
    if (monthlyLease == null) {
      throw new IllegalArgumentException("Il canone mensile leasing struttura è obbligatorio.");
    }
    return new FacilityFinancialProfile(
        facilityCode,
        FacilityOwnershipType.LEASED,
        null,
        monthlyLease,
        depositAmount,
        costLines,
        notes);
  }

  public static FacilityFinancialProfile thirdPartyYard(
      String facilityCode,
      Money monthlyFee,
      Money depositAmount,
      List<FacilityCostLine> costLines,
      Notes notes) {
    if (monthlyFee == null) {
      throw new IllegalArgumentException("Il canone mensile piazzale terzi è obbligatorio.");
    }
    return new FacilityFinancialProfile(
        facilityCode,
        FacilityOwnershipType.THIRD_PARTY_YARD,
        null,
        monthlyFee,
        depositAmount,
        costLines,
        notes);
  }

  private static String validateFacilityCode(String facilityCode) {
    if (facilityCode == null) {
      throw new IllegalArgumentException("Il codice struttura è obbligatorio.");
    }

    String normalizedCode = facilityCode.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException("Il codice struttura non può essere vuoto.");
    }

    if (normalizedCode.length() > MAX_FACILITY_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice struttura non può superare " + MAX_FACILITY_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice struttura può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  private static FacilityOwnershipType validateOwnershipType(FacilityOwnershipType ownershipType) {
    if (ownershipType == null) {
      throw new IllegalArgumentException("Il tipo proprietà struttura è obbligatorio.");
    }
    return ownershipType;
  }

  private static List<FacilityCostLine> validateCostLines(List<FacilityCostLine> costLines) {
    if (costLines == null) {
      throw new IllegalArgumentException("Le voci costo struttura sono obbligatorie.");
    }

    if (costLines.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "Le voci costo struttura non possono contenere elementi null.");
    }

    return new ArrayList<>(costLines);
  }

  private static Notes validateNotes(Notes notes) {
    if (notes == null) {
      throw new IllegalArgumentException("Le note profilo economico struttura sono obbligatorie.");
    }
    return notes;
  }

  private void validateEconomicConsistency() {
    if (ownershipType == FacilityOwnershipType.OWNED && monthlyRent != null) {
      throw new IllegalArgumentException(
          "Una struttura di proprietà non deve avere canone mensile di affitto.");
    }

    if (ownershipType.requiresRecurringOccupancyPayment() && monthlyRent == null) {
      throw new IllegalArgumentException(
          "Una struttura non di proprietà deve avere un canone mensile.");
    }

    if (!ownershipType.isOwnedAsset() && purchasePrice != null) {
      throw new IllegalArgumentException(
          "Il prezzo di acquisto è ammesso solo per strutture di proprietà.");
    }

    validateSameCurrencyWhenPresent();
  }

  private void validateSameCurrencyWhenPresent() {
    Currency referenceCurrency = null;

    if (purchasePrice != null) {
      referenceCurrency = purchasePrice.getCurrency();
    } else if (monthlyRent != null) {
      referenceCurrency = monthlyRent.getCurrency();
    } else if (depositAmount != null) {
      referenceCurrency = depositAmount.getCurrency();
    } else if (!costLines.isEmpty()) {
      referenceCurrency = costLines.get(0).getAmount().getCurrency();
    }

    if (referenceCurrency == null) {
      return;
    }

    if (purchasePrice != null && !purchasePrice.getCurrency().equals(referenceCurrency)) {
      throw new IllegalArgumentException("Gli importi struttura devono avere la stessa valuta.");
    }
    if (monthlyRent != null && !monthlyRent.getCurrency().equals(referenceCurrency)) {
      throw new IllegalArgumentException("Gli importi struttura devono avere la stessa valuta.");
    }
    if (depositAmount != null && !depositAmount.getCurrency().equals(referenceCurrency)) {
      throw new IllegalArgumentException("Gli importi struttura devono avere la stessa valuta.");
    }
    Currency finalReferenceCurrency = referenceCurrency;
    if (costLines.stream()
        .anyMatch(line -> !line.getAmount().getCurrency().equals(finalReferenceCurrency))) {
      throw new IllegalArgumentException("Gli importi struttura devono avere la stessa valuta.");
    }
  }

  public String getFacilityCode() {
    return facilityCode;
  }

  public FacilityOwnershipType getOwnershipType() {
    return ownershipType;
  }

  public Optional<Money> getPurchasePrice() {
    return Optional.ofNullable(purchasePrice);
  }

  public Optional<Money> getMonthlyRent() {
    return Optional.ofNullable(monthlyRent);
  }

  public Optional<Money> getDepositAmount() {
    return Optional.ofNullable(depositAmount);
  }

  public List<FacilityCostLine> getCostLines() {
    return costLines;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isOwned() {
    return ownershipType.isOwnedAsset();
  }

  public boolean hasRecurringOccupancyPayment() {
    return ownershipType.requiresRecurringOccupancyPayment();
  }

  public Money calculateMonthlyRecurringCost() {
    Currency currency = resolveCurrency();
    Money total = zero(currency);

    if (monthlyRent != null) {
      total = total.add(monthlyRent);
    }

    for (FacilityCostLine line : costLines) {
      if (line.isRecurring()) {
        total = total.add(line.monthlyEquivalent());
      }
    }

    return total;
  }

  public Money calculateOneTimeCashOut() {
    Currency currency = resolveCurrency();
    Money total = zero(currency);

    if (purchasePrice != null) {
      total = total.add(purchasePrice);
    }
    if (depositAmount != null) {
      total = total.add(depositAmount);
    }

    for (FacilityCostLine line : costLines) {
      if (!line.isRecurring()) {
        total = total.add(line.getAmount());
      }
    }

    return total;
  }

  public Money calculateAnnualRecurringCost() {
    Money monthly = calculateMonthlyRecurringCost();
    return Money.of(monthly.getAmount().multiply(BigDecimal.valueOf(12)), monthly.getCurrency());
  }

  private Currency resolveCurrency() {
    if (purchasePrice != null) {
      return purchasePrice.getCurrency();
    }
    if (monthlyRent != null) {
      return monthlyRent.getCurrency();
    }
    if (depositAmount != null) {
      return depositAmount.getCurrency();
    }
    if (!costLines.isEmpty()) {
      return costLines.get(0).getAmount().getCurrency();
    }
    return Currency.getInstance("EUR");
  }

  private static Money zero(Currency currency) {
    return Money.of(BigDecimal.ZERO, currency);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FacilityFinancialProfile that)) return false;
    return facilityCode.equals(that.facilityCode)
        && ownershipType == that.ownershipType
        && Objects.equals(purchasePrice, that.purchasePrice)
        && Objects.equals(monthlyRent, that.monthlyRent)
        && Objects.equals(depositAmount, that.depositAmount)
        && costLines.equals(that.costLines)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        facilityCode, ownershipType, purchasePrice, monthlyRent, depositAmount, costLines, notes);
  }

  @Override
  public String toString() {
    return facilityCode + " - " + ownershipType + " - monthly=" + calculateMonthlyRecurringCost();
  }
}
