package it.gabriele.truckflow.domain.configuration;

/** Categoria funzionale di una configurazione. */
public enum ConfigurationCategory {
  OPERATION(false),
  PRICING(false),
  NOTIFICATION(false),
  DOCUMENT(false),
  SECURITY(true),
  SUSTAINABILITY(false),
  REPORTING(false),
  INTEGRATION(true);

  private final boolean sensitive;

  ConfigurationCategory(boolean sensitive) {
    this.sensitive = sensitive;
  }

  public boolean isSensitive() {
    return sensitive;
  }
}
