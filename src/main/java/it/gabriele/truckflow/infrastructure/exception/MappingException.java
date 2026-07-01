package it.gabriele.truckflow.infrastructure.exception;

/** Exception used when infrastructure mapping between domain and technical models fails. */
public class MappingException extends InfrastructureException {

  public MappingException(String message) {
    super(message);
  }

  public MappingException(String message, Throwable cause) {
    super(message, cause);
  }
}
