package it.gabriele.truckflow.infrastructure.exception;

/** Exception used when required infrastructure configuration is missing or invalid. */
public class InfrastructureConfigurationException extends InfrastructureException {

  public InfrastructureConfigurationException(String message) {
    super(message);
  }

  public InfrastructureConfigurationException(String message, Throwable cause) {
    super(message, cause);
  }
}
