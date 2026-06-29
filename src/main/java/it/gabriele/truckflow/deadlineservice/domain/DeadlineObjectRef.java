package it.gabriele.truckflow.deadlineservice.domain;

/** Riferimento generico a un oggetto di qualunque dominio o microservizio. */
public record DeadlineObjectRef(
    String tenantId, String objectType, String objectId, String naturalKey) {

  public DeadlineObjectRef {
    tenantId = requireText(tenantId, "tenantId");
    objectType = requireText(objectType, "objectType");
    objectId = requireText(objectId, "objectId");
    naturalKey = naturalKey == null ? "" : naturalKey.strip();
  }

  private static String requireText(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(fieldName + " è obbligatorio.");
    }
    return value.strip();
  }
}
