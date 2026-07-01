package it.gabriele.truckflow.infrastructure.exception;

/** Exception used when a technical repository adapter cannot complete a persistence operation. */
public class RepositoryException extends InfrastructureException {

  public RepositoryException(String message) {
    super(message);
  }

  public RepositoryException(String message, Throwable cause) {
    super(message, cause);
  }
}
