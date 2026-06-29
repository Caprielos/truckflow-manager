package it.gabriele.truckflow.application.common;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Risultato applicativo generico. Serve quando un adapter web/CLI vuole evitare eccezioni. I casi
 * d'uso possono comunque lanciare eccezioni di dominio per input impossibili.
 */
public final class ApplicationResult<T> {

  private final T value;
  private final List<ApplicationError> errors;

  private ApplicationResult(T value, List<ApplicationError> errors) {
    if (errors == null) {
      throw new IllegalArgumentException("Gli errori applicativi sono obbligatori.");
    }
    if (errors.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Gli errori applicativi non possono contenere null.");
    }
    this.value = value;
    this.errors = List.copyOf(errors);
  }

  public static <T> ApplicationResult<T> success(T value) {
    if (value == null) {
      throw new IllegalArgumentException("Il valore di successo è obbligatorio.");
    }
    return new ApplicationResult<>(value, List.of());
  }

  public static <T> ApplicationResult<T> failure(ApplicationError error) {
    if (error == null) {
      throw new IllegalArgumentException("L'errore applicativo è obbligatorio.");
    }
    return new ApplicationResult<>(null, List.of(error));
  }

  public static <T> ApplicationResult<T> failure(List<ApplicationError> errors) {
    if (errors == null || errors.isEmpty()) {
      throw new IllegalArgumentException("La lista errori applicativi è obbligatoria.");
    }
    return new ApplicationResult<>(null, errors);
  }

  public boolean isSuccess() {
    return errors.isEmpty();
  }

  public boolean isFailure() {
    return !isSuccess();
  }

  public Optional<T> getValue() {
    return Optional.ofNullable(value);
  }

  public T getValueOrThrow() {
    if (isFailure()) {
      throw new IllegalStateException("Il risultato contiene errori e non ha valore.");
    }
    return value;
  }

  public List<ApplicationError> getErrors() {
    return errors;
  }
}
