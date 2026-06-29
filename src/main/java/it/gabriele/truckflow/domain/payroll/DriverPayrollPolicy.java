package it.gabriele.truckflow.domain.payroll;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Politica paga aziendale/contrattuale applicata agli autisti. Le cifre non sono hard-coded nel
 * dominio: vengono configurate per azienda, paese, contratto e periodo di validità.
 */
public final class DriverPayrollPolicy {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_DESCRIPTION_LENGTH = 200;

  private final String policyCode;
  private final String description;
  private final LocalDate validFrom;
  private final LocalDate validTo;
  private final List<DriverPayRule> rules;
  private final Notes notes;

  private DriverPayrollPolicy(
      String policyCode,
      String description,
      LocalDate validFrom,
      LocalDate validTo,
      List<DriverPayRule> rules,
      Notes notes) {
    this.policyCode = validateCode(policyCode, "Il codice politica paga è obbligatorio.");
    this.description = validateDescription(description);
    if (validFrom == null) {
      throw new IllegalArgumentException("La data inizio validità politica paga è obbligatoria.");
    }
    if (validTo != null && validTo.isBefore(validFrom)) {
      throw new IllegalArgumentException(
          "La data fine validità politica paga non può precedere l'inizio.");
    }
    if (rules == null) {
      throw new IllegalArgumentException("Le regole paga sono obbligatorie.");
    }
    if (rules.isEmpty()) {
      throw new IllegalArgumentException("La politica paga deve avere almeno una regola.");
    }
    if (rules.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Le regole paga non possono contenere null.");
    }
    long uniqueCodes = rules.stream().map(DriverPayRule::getRuleCode).distinct().count();
    if (uniqueCodes != rules.size()) {
      throw new IllegalArgumentException("Le regole paga non possono avere codici duplicati.");
    }
    if (notes == null) {
      throw new IllegalArgumentException("Le note politica paga sono obbligatorie.");
    }
    this.validFrom = validFrom;
    this.validTo = validTo;
    this.rules = List.copyOf(rules);
    this.notes = notes;
  }

  public static DriverPayrollPolicy of(
      String policyCode,
      String description,
      LocalDate validFrom,
      LocalDate validTo,
      List<DriverPayRule> rules,
      Notes notes) {
    return new DriverPayrollPolicy(policyCode, description, validFrom, validTo, rules, notes);
  }

  public boolean isValidOn(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("La data verifica politica paga è obbligatoria.");
    }
    return !date.isBefore(validFrom) && (validTo == null || !date.isAfter(validTo));
  }

  public Optional<DriverPayRule> findRule(DriverPayComponentType componentType) {
    if (componentType == null) {
      throw new IllegalArgumentException("Il tipo voce paga da cercare è obbligatorio.");
    }
    return rules.stream().filter(rule -> rule.getComponentType() == componentType).findFirst();
  }

  public boolean hasRule(DriverPayComponentType componentType) {
    return findRule(componentType).isPresent();
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
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static String validateDescription(String description) {
    if (description == null) {
      throw new IllegalArgumentException("La descrizione politica paga è obbligatoria.");
    }
    String normalized = description.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("La descrizione politica paga non può essere vuota.");
    }
    if (normalized.length() > MAX_DESCRIPTION_LENGTH) {
      throw new IllegalArgumentException(
          "La descrizione politica paga non può superare "
              + MAX_DESCRIPTION_LENGTH
              + " caratteri.");
    }
    return normalized;
  }

  public String getPolicyCode() {
    return policyCode;
  }

  public String getDescription() {
    return description;
  }

  public LocalDate getValidFrom() {
    return validFrom;
  }

  public LocalDate getValidTo() {
    return validTo;
  }

  public List<DriverPayRule> getRules() {
    return rules;
  }

  public Notes getNotes() {
    return notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DriverPayrollPolicy that)) return false;
    return policyCode.equals(that.policyCode)
        && description.equals(that.description)
        && validFrom.equals(that.validFrom)
        && Objects.equals(validTo, that.validTo)
        && rules.equals(that.rules)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(policyCode, description, validFrom, validTo, rules, notes);
  }
}
