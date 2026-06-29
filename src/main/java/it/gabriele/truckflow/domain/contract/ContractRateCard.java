package it.gabriele.truckflow.domain.contract;

import it.gabriele.truckflow.domain.shared.Notes;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/** Listino applicato da un contratto cliente. */
public final class ContractRateCard {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_NAME_LENGTH = 150;

  private final String rateCardCode;
  private final String name;
  private final List<TariffRule> rules;
  private final Notes notes;

  private ContractRateCard(String rateCardCode, String name, List<TariffRule> rules, Notes notes) {
    this.rateCardCode = validateCode(rateCardCode);
    this.name = validateName(name);
    this.rules = validateRules(rules);
    if (notes == null) {
      throw new IllegalArgumentException("Le note listino sono obbligatorie.");
    }
    this.notes = notes;
  }

  public static ContractRateCard of(
      String rateCardCode, String name, List<TariffRule> rules, Notes notes) {
    return new ContractRateCard(rateCardCode, name, rules, notes);
  }

  private static String validateCode(String code) {
    if (code == null) {
      throw new IllegalArgumentException("Il codice listino è obbligatorio.");
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Il codice listino non può essere vuoto.");
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice listino non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice listino può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static String validateName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Il nome listino è obbligatorio.");
    }
    String normalized = name.trim();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException("Il nome listino non può essere vuoto.");
    }
    if (normalized.length() > MAX_NAME_LENGTH) {
      throw new IllegalArgumentException(
          "Il nome listino non può superare " + MAX_NAME_LENGTH + " caratteri.");
    }
    return normalized;
  }

  private static List<TariffRule> validateRules(List<TariffRule> rules) {
    if (rules == null) {
      throw new IllegalArgumentException("Le regole listino sono obbligatorie.");
    }
    if (rules.isEmpty()) {
      throw new IllegalArgumentException("Il listino deve avere almeno una regola.");
    }
    if (rules.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Le regole listino non possono contenere null.");
    }
    long uniqueCodes = rules.stream().map(TariffRule::getRuleCode).distinct().count();
    if (uniqueCodes != rules.size()) {
      throw new IllegalArgumentException("Il listino non può avere codici regola duplicati.");
    }
    return List.copyOf(rules);
  }

  public String getRateCardCode() {
    return rateCardCode;
  }

  public String getName() {
    return name;
  }

  public List<TariffRule> getRules() {
    return rules;
  }

  public Notes getNotes() {
    return notes;
  }

  public Optional<TariffRule> findRule(TariffRuleType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo regola da cercare è obbligatorio.");
    }
    return rules.stream().filter(rule -> rule.appliesTo(type)).findFirst();
  }

  public boolean containsRule(TariffRuleType type) {
    return findRule(type).isPresent();
  }
}
