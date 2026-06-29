package it.gabriele.truckflow.domain.configuration;

/** Regole di dominio per le configurazioni applicative. */
public final class ConfigurationRules {

  private ConfigurationRules() {}

  public static boolean canBeApplied(SystemConfiguration configuration) {
    validateConfiguration(configuration);

    return configuration.isActive();
  }

  public static boolean canOverride(
      SystemConfiguration baseConfiguration, SystemConfiguration overrideConfiguration) {
    validateConfiguration(baseConfiguration);
    validateConfiguration(overrideConfiguration);

    return baseConfiguration.isGlobal()
        && !overrideConfiguration.isGlobal()
        && baseConfiguration.isActive()
        && overrideConfiguration.isActive()
        && baseConfiguration.hasSameKey(overrideConfiguration)
        && baseConfiguration.hasSameCategory(overrideConfiguration)
        && baseConfiguration.getValue().getType() == overrideConfiguration.getValue().getType();
  }

  public static boolean isApplicableTo(
      SystemConfiguration configuration, ConfigurationScope scope, String scopeReference) {
    validateConfiguration(configuration);

    if (scope == null) {
      throw new IllegalArgumentException("L'ambito applicazione configurazione è obbligatorio.");
    }

    return configuration.isActive()
        && (configuration.isGlobal() || configuration.isForScope(scope, scopeReference));
  }

  public static boolean isSensitiveConfiguration(SystemConfiguration configuration) {
    validateConfiguration(configuration);

    return configuration.isSensitive();
  }

  public static boolean requiresRestrictedAccess(SystemConfiguration configuration) {
    validateConfiguration(configuration);

    return configuration.isSensitive()
        || configuration.getCategory() == ConfigurationCategory.SECURITY
        || configuration.getCategory() == ConfigurationCategory.INTEGRATION;
  }

  public static boolean isNumericConfiguration(SystemConfiguration configuration) {
    validateConfiguration(configuration);

    return configuration.getValue().isNumeric();
  }

  public static boolean isPricingConfiguration(SystemConfiguration configuration) {
    validateConfiguration(configuration);

    return configuration.isPricingConfiguration();
  }

  public static boolean isSecurityConfiguration(SystemConfiguration configuration) {
    validateConfiguration(configuration);

    return configuration.isSecurityConfiguration();
  }

  private static void validateConfiguration(SystemConfiguration configuration) {
    if (configuration == null) {
      throw new IllegalArgumentException("La configurazione è obbligatoria.");
    }
  }
}
