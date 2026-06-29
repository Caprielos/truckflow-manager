package it.gabriele.truckflow.web.deadlineservice;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineObjectRef;
import jakarta.validation.constraints.NotBlank;

/** DTO REST che identifica un oggetto qualunque valutabile dal deadline-service. */
public record DeadlineObjectRefRequest(
    @NotBlank String tenantId,
    @NotBlank String objectType,
    @NotBlank String objectId,
    String naturalKey) {

  DeadlineObjectRef toDomain() {
    return new DeadlineObjectRef(tenantId, objectType, objectId, naturalKey);
  }
}
