package it.gabriele.truckflow.domain.integration;

import it.gabriele.truckflow.domain.shared.Notes;
import java.util.Objects;

/** Connettore configurato verso un sistema esterno enterprise. */
public final class IntegrationConnector {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_PROVIDER_LENGTH = 120;

  private final String connectorCode;
  private final ExternalSystemType systemType;
  private final String providerName;
  private final IntegrationStatus status;
  private final Notes notes;

  private IntegrationConnector(
      String connectorCode,
      ExternalSystemType systemType,
      String providerName,
      IntegrationStatus status,
      Notes notes) {
    this.connectorCode = validateCode(connectorCode, "Il codice connettore è obbligatorio.");

    if (systemType == null) {
      throw new IllegalArgumentException("Il tipo sistema esterno è obbligatorio.");
    }

    this.providerName = validateProvider(providerName);

    if (status == null) {
      throw new IllegalArgumentException("Lo stato connettore è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note connettore sono obbligatorie.");
    }

    this.systemType = systemType;
    this.status = status;
    this.notes = notes;
  }

  public static IntegrationConnector configured(
      String connectorCode, ExternalSystemType systemType, String providerName, Notes notes) {
    return new IntegrationConnector(
        connectorCode, systemType, providerName, IntegrationStatus.CONFIGURED, notes);
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

  private static String validateProvider(String providerName) {
    if (providerName == null) {
      throw new IllegalArgumentException("Il provider integrazione è obbligatorio.");
    }

    String normalizedProvider = providerName.trim();

    if (normalizedProvider.isEmpty()) {
      throw new IllegalArgumentException("Il provider integrazione non può essere vuoto.");
    }

    if (normalizedProvider.length() > MAX_PROVIDER_LENGTH) {
      throw new IllegalArgumentException(
          "Il provider integrazione non può superare " + MAX_PROVIDER_LENGTH + " caratteri.");
    }

    return normalizedProvider;
  }

  public IntegrationConnector activate() {
    if (!IntegrationRules.canBeActivated(this)) {
      throw new IllegalStateException("Il connettore non può essere attivato.");
    }

    return new IntegrationConnector(
        connectorCode, systemType, providerName, IntegrationStatus.ACTIVE, notes);
  }

  public IntegrationConnector disable() {
    return new IntegrationConnector(
        connectorCode, systemType, providerName, IntegrationStatus.DISABLED, notes);
  }

  public String getConnectorCode() {
    return connectorCode;
  }

  public ExternalSystemType getSystemType() {
    return systemType;
  }

  public String getProviderName() {
    return providerName;
  }

  public IntegrationStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isActive() {
    return status == IntegrationStatus.ACTIVE;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof IntegrationConnector that)) return false;
    return connectorCode.equals(that.connectorCode)
        && systemType == that.systemType
        && providerName.equals(that.providerName)
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(connectorCode, systemType, providerName, status, notes);
  }
}
