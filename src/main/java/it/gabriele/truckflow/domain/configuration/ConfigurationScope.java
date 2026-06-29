package it.gabriele.truckflow.domain.configuration;

/** Ambito in cui una configurazione è applicabile. */
public enum ConfigurationScope {
  GLOBAL(false),
  ORGANIZATION(true),
  CUSTOMER(true),
  FACILITY(true),
  USER(true);

  private final boolean requiresReference;

  ConfigurationScope(boolean requiresReference) {
    this.requiresReference = requiresReference;
  }

  public boolean requiresReference() {
    return requiresReference;
  }

  public boolean isGlobal() {
    return this == GLOBAL;
  }
}
