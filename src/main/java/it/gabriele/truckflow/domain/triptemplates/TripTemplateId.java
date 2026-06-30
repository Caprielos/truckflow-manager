package it.gabriele.truckflow.domain.triptemplates;

import java.util.UUID;

public record TripTemplateId(UUID value) {

  public TripTemplateId {
    value = TripTemplateValidation.requireNonNull(value, "value");
  }

  public static TripTemplateId random() {
    return new TripTemplateId(UUID.randomUUID());
  }
}
