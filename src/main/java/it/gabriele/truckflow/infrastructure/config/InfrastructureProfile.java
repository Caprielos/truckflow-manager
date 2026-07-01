package it.gabriele.truckflow.infrastructure.config;

/** Technical infrastructure profiles planned for wiring and configuration steps. */
public enum InfrastructureProfile {
  /** Lightweight in-memory adapters used for tests and local scenarios. */
  MEMORY,

  /** Local technical configuration for developer machines. */
  LOCAL,

  /** Test configuration for automated technical verification. */
  TEST,

  /** Future production-grade infrastructure configuration. */
  PRODUCTION
}
