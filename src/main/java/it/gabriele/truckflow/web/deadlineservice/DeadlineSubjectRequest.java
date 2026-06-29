package it.gabriele.truckflow.web.deadlineservice;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Set;

/** DTO REST neutro: descrive l'oggetto e i fatti senza dipendere dal dominio principale. */
public record DeadlineSubjectRequest(
    @Valid @NotNull DeadlineObjectRefRequest objectRef,
    String configuredCountry,
    String manufacturer,
    String model,
    @NotEmpty Set<ManagedElementCode> elements,
    Map<String, String> facts) {

  DeadlineSubject toDomain() {
    return new DeadlineSubject(
        objectRef.toDomain(), configuredCountry, manufacturer, model, elements, facts);
  }
}
