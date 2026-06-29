package it.gabriele.truckflow.domain.integration;

/** Regole di dominio per integrazioni esterne. */
public final class IntegrationRules {

  private IntegrationRules() {}

  public static boolean canBeActivated(IntegrationConnector connector) {
    validateConnector(connector);

    return connector.getStatus() == IntegrationStatus.CONFIGURED
        || connector.getStatus() == IntegrationStatus.DISABLED;
  }

  public static boolean needsReconciliation(IntegrationRun run) {
    validateRun(run);

    return run.hasFailures() || run.getStatus() == IntegrationStatus.COMPLETED_WITH_ERRORS;
  }

  private static void validateConnector(IntegrationConnector connector) {
    if (connector == null) {
      throw new IllegalArgumentException("Il connettore integrazione è obbligatorio.");
    }
  }

  private static void validateRun(IntegrationRun run) {
    if (run == null) {
      throw new IllegalArgumentException("Il run integrazione è obbligatorio.");
    }
  }
}
