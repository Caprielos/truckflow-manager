package it.gabriele.truckflow.domain.deadlinepolicy;

import it.gabriele.truckflow.domain.deadline.DeadlineOwnerType;
import it.gabriele.truckflow.domain.deadline.DeadlineSeverity;
import it.gabriele.truckflow.domain.deadline.DeadlineType;
import it.gabriele.truckflow.domain.regulation.EuropeanCountry;
import java.util.Locale;
import java.util.Objects;

/** Regola configurabile di scadenza legale o tecnica. */
public final class DeadlinePolicyRule {

  private static final int MAX_CODE_LENGTH = 80;

  private final String ruleCode;
  private final DeadlinePolicySource source;
  private final EuropeanCountry country;
  private final String manufacturer;
  private final String modelFamily;
  private final DeadlineOwnerType ownerType;
  private final ManagedDeadlineElementType elementType;
  private final DeadlineType deadlineType;
  private final DeadlineRuleInterval interval;
  private final DeadlineSeverity severity;
  private final boolean blocksOperations;
  private final String reference;

  private DeadlinePolicyRule(
      String ruleCode,
      DeadlinePolicySource source,
      EuropeanCountry country,
      String manufacturer,
      String modelFamily,
      DeadlineOwnerType ownerType,
      ManagedDeadlineElementType elementType,
      DeadlineType deadlineType,
      DeadlineRuleInterval interval,
      DeadlineSeverity severity,
      boolean blocksOperations,
      String reference) {
    this.ruleCode = normalizeCode(ruleCode);
    this.source = Objects.requireNonNull(source, "La fonte regola scadenza è obbligatoria.");
    this.ownerType = Objects.requireNonNull(ownerType, "Il tipo proprietario è obbligatorio.");
    this.elementType = Objects.requireNonNull(elementType, "Il tipo elemento è obbligatorio.");
    this.deadlineType = Objects.requireNonNull(deadlineType, "Il tipo scadenza è obbligatorio.");
    this.interval = Objects.requireNonNull(interval, "L'intervallo scadenza è obbligatorio.");
    this.severity = Objects.requireNonNull(severity, "La gravità scadenza è obbligatoria.");
    this.reference = normalizeReference(reference);
    this.blocksOperations = blocksOperations;

    if (source == DeadlinePolicySource.LEGAL_COUNTRY) {
      this.country =
          Objects.requireNonNull(country, "Il paese della regola legale è obbligatorio.");
      this.manufacturer = null;
      this.modelFamily = null;
    } else {
      this.country = null;
      this.manufacturer = normalizeRequiredText(manufacturer, "Il costruttore è obbligatorio.");
      this.modelFamily =
          normalizeRequiredText(modelFamily, "Il modello/famiglia mezzo è obbligatorio.");
    }
  }

  public static DeadlinePolicyRule legal(
      String ruleCode,
      EuropeanCountry country,
      DeadlineOwnerType ownerType,
      ManagedDeadlineElementType elementType,
      DeadlineType deadlineType,
      DeadlineRuleInterval interval,
      DeadlineSeverity severity,
      boolean blocksOperations,
      String reference) {
    return new DeadlinePolicyRule(
        ruleCode,
        DeadlinePolicySource.LEGAL_COUNTRY,
        country,
        null,
        null,
        ownerType,
        elementType,
        deadlineType,
        interval,
        severity,
        blocksOperations,
        reference);
  }

  public static DeadlinePolicyRule technical(
      String ruleCode,
      String manufacturer,
      String modelFamily,
      DeadlineOwnerType ownerType,
      ManagedDeadlineElementType elementType,
      DeadlineType deadlineType,
      DeadlineRuleInterval interval,
      DeadlineSeverity severity,
      boolean blocksOperations,
      String reference) {
    return new DeadlinePolicyRule(
        ruleCode,
        DeadlinePolicySource.TECHNICAL_MANUFACTURER,
        null,
        manufacturer,
        modelFamily,
        ownerType,
        elementType,
        deadlineType,
        interval,
        severity,
        blocksOperations,
        reference);
  }

  public boolean matchesLegal(EuropeanCountry selectedCountry) {
    return source == DeadlinePolicySource.LEGAL_COUNTRY && country == selectedCountry;
  }

  public boolean matchesTechnical(String selectedManufacturer, String selectedModelFamily) {
    return source == DeadlinePolicySource.TECHNICAL_MANUFACTURER
        && manufacturer.equals(
            normalizeRequiredText(selectedManufacturer, "Il costruttore è obbligatorio."))
        && modelFamily.equals(
            normalizeRequiredText(
                selectedModelFamily, "Il modello/famiglia mezzo è obbligatorio."));
  }

  public String getRuleCode() {
    return ruleCode;
  }

  public DeadlinePolicySource getSource() {
    return source;
  }

  public EuropeanCountry getCountry() {
    return country;
  }

  public String getManufacturer() {
    return manufacturer;
  }

  public String getModelFamily() {
    return modelFamily;
  }

  public DeadlineOwnerType getOwnerType() {
    return ownerType;
  }

  public ManagedDeadlineElementType getElementType() {
    return elementType;
  }

  public DeadlineType getDeadlineType() {
    return deadlineType;
  }

  public DeadlineRuleInterval getInterval() {
    return interval;
  }

  public DeadlineSeverity getSeverity() {
    return severity;
  }

  public boolean blocksOperations() {
    return blocksOperations;
  }

  public String getReference() {
    return reference;
  }

  private static String normalizeCode(String code) {
    String normalized = normalizeRequiredText(code, "Il codice regola scadenza è obbligatorio.");
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice regola scadenza non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice regola scadenza può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static String normalizeRequiredText(String value, String message) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException(message);
    }
    return value.trim().toUpperCase(Locale.ROOT);
  }

  private static String normalizeReference(String reference) {
    if (reference == null || reference.trim().isEmpty()) {
      return "N/D";
    }
    return reference.trim();
  }
}
