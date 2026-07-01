package it.gabriele.truckflow.infrastructure.exception;

/** Base unchecked exception for technical infrastructure failures. */
public class InfrastructureException extends RuntimeException {

  public InfrastructureException(String message) {
    super(message);
  }

  public InfrastructureException(String message, Throwable cause) {
    super(message, cause);
  }
}
