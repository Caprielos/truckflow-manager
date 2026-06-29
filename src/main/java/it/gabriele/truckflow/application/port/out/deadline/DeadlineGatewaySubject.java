package it.gabriele.truckflow.application.port.out.deadline;

import java.util.Map;
import java.util.Set;

/** Oggetto generico che il dominio principale invia al servizio scadenze. */
public record DeadlineGatewaySubject(
    DeadlineGatewayObjectRef objectRef,
    String configuredCountry,
    String manufacturer,
    String model,
    Set<String> elementCodes,
    Map<String, String> facts) {

  public DeadlineGatewaySubject {
    if (objectRef == null) {
      throw new IllegalArgumentException("objectRef è obbligatorio.");
    }
    configuredCountry = normalize(configuredCountry);
    manufacturer = normalize(manufacturer);
    model = normalize(model);
    elementCodes = elementCodes == null ? Set.of() : Set.copyOf(elementCodes);
    facts = facts == null ? Map.of() : Map.copyOf(facts);
  }

  public static DeadlineGatewaySubject of(
      String tenantId,
      String objectType,
      String objectId,
      String naturalKey,
      String configuredCountry,
      String manufacturer,
      String model,
      Set<String> elementCodes,
      Map<String, String> facts) {
    return new DeadlineGatewaySubject(
        new DeadlineGatewayObjectRef(tenantId, objectType, objectId, naturalKey),
        configuredCountry,
        manufacturer,
        model,
        elementCodes,
        facts);
  }

  private static String normalize(String value) {
    return value == null ? "" : value.strip();
  }
}
