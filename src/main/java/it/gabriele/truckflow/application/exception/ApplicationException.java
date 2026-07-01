package it.gabriele.truckflow.application.exception;

/** Base unchecked exception for application-layer errors. */
public class ApplicationException extends RuntimeException {

  public ApplicationException(String message) {
    super(requireMessage(message));
  }

  public ApplicationException(String message, Throwable cause) {
    super(requireMessage(message), cause);
  }

  private static String requireMessage(String message) {
    if (message == null || message.isBlank()) {
      return "Application error";
    }
    return message.trim();
  }
}
