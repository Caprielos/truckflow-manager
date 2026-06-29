package it.gabriele.truckflow.web.common;

import it.gabriele.truckflow.application.common.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Traduce eccezioni Java in risposte HTTP leggibili. */
@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public RestErrorResponse handleResourceNotFound(
      ResourceNotFoundException exception, HttpServletRequest request) {
    return error(HttpStatus.NOT_FOUND, exception.getMessage(), request, List.of());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RestErrorResponse handleIllegalArgument(
      IllegalArgumentException exception, HttpServletRequest request) {
    return error(HttpStatus.BAD_REQUEST, exception.getMessage(), request, List.of());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RestErrorResponse handleValidation(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    List<String> details =
        exception.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
            .toList();

    return error(HttpStatus.BAD_REQUEST, "Richiesta non valida.", request, details);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RestErrorResponse handleUnreadableJson(
      HttpMessageNotReadableException exception, HttpServletRequest request) {
    return error(
        HttpStatus.BAD_REQUEST,
        "JSON non leggibile o formato dati non valido.",
        request,
        List.of());
  }

  private static RestErrorResponse error(
      HttpStatus status, String message, HttpServletRequest request, List<String> details) {
    return new RestErrorResponse(
        LocalDateTime.now(),
        status.value(),
        status.getReasonPhrase(),
        message,
        request.getRequestURI(),
        details);
  }
}
