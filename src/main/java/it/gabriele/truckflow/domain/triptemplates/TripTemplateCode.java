package it.gabriele.truckflow.domain.triptemplates;

import it.gabriele.truckflow.domain.triptemplates.exceptions.InvalidTripTemplateException;

public record TripTemplateCode(String value) {

  public TripTemplateCode {
    value = TripTemplateValidation.requireText(value, "value").toUpperCase();

    if (!value.matches("[A-Z0-9][A-Z0-9_-]*")) {
      throw new InvalidTripTemplateException(
          "Trip template code can contain only uppercase letters, numbers, dashes and"
              + " underscores.");
    }
  }

  public static TripTemplateCode of(String value) {
    return new TripTemplateCode(value);
  }
}
