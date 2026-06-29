package it.gabriele.truckflow.domain.sla;

import it.gabriele.truckflow.domain.shared.DateRange;
import it.gabriele.truckflow.domain.shared.Notes;
import java.util.List;
import java.util.Objects;

/** Accordo SLA con cliente o fornitore, composto da regole misurabili. */
public final class ServiceLevelAgreement {

  private static final int MAX_CODE_LENGTH = 50;

  private final String agreementCode;
  private final String partyCode;
  private final DateRange validity;
  private final List<SlaRule> rules;
  private final List<PenaltyRule> penalties;
  private final SlaStatus status;
  private final Notes notes;

  private ServiceLevelAgreement(
      String agreementCode,
      String partyCode,
      DateRange validity,
      List<SlaRule> rules,
      List<PenaltyRule> penalties,
      SlaStatus status,
      Notes notes) {
    this.agreementCode = validateCode(agreementCode, "Il codice SLA è obbligatorio.");
    this.partyCode = validateCode(partyCode, "Il codice controparte SLA è obbligatorio.");

    if (validity == null) {
      throw new IllegalArgumentException("La validità SLA è obbligatoria.");
    }

    if (rules == null || rules.isEmpty()) {
      throw new IllegalArgumentException("Lo SLA deve avere almeno una regola.");
    }

    if (rules.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Le regole SLA non possono contenere valori null.");
    }

    if (penalties == null || penalties.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException(
          "Le penali SLA sono obbligatorie e non possono contenere null.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato SLA è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note SLA sono obbligatorie.");
    }

    this.validity = validity;
    this.rules = List.copyOf(rules);
    this.penalties = List.copyOf(penalties);
    this.status = status;
    this.notes = notes;
  }

  public static ServiceLevelAgreement draft(
      String agreementCode,
      String partyCode,
      DateRange validity,
      List<SlaRule> rules,
      List<PenaltyRule> penalties,
      Notes notes) {
    return new ServiceLevelAgreement(
        agreementCode, partyCode, validity, rules, penalties, SlaStatus.DRAFT, notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  public ServiceLevelAgreement activate() {
    if (!SlaRules.canBeActivated(this)) {
      throw new IllegalStateException("Lo SLA non può essere attivato.");
    }

    return new ServiceLevelAgreement(
        agreementCode, partyCode, validity, rules, penalties, SlaStatus.ACTIVE, notes);
  }

  public ServiceLevelAgreement expire() {
    return new ServiceLevelAgreement(
        agreementCode, partyCode, validity, rules, penalties, SlaStatus.EXPIRED, notes);
  }

  public String getAgreementCode() {
    return agreementCode;
  }

  public String getPartyCode() {
    return partyCode;
  }

  public DateRange getValidity() {
    return validity;
  }

  public List<SlaRule> getRules() {
    return rules;
  }

  public List<PenaltyRule> getPenalties() {
    return penalties;
  }

  public SlaStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isActive() {
    return status.isActive();
  }

  public boolean hasRuleFor(SlaMetric metric) {
    if (metric == null) {
      throw new IllegalArgumentException("La metrica SLA da cercare è obbligatoria.");
    }

    return rules.stream().anyMatch(rule -> rule.getMetric() == metric);
  }

  public boolean hasPenaltyFor(SlaMetric metric) {
    if (metric == null) {
      throw new IllegalArgumentException("La metrica penale da cercare è obbligatoria.");
    }

    return penalties.stream().anyMatch(penalty -> penalty.getMetric() == metric);
  }
}
