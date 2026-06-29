package it.gabriele.truckflow.application.port.out.deadline;

/** Riferimento neutro a un oggetto da valutare tramite il servizio scadenze. */
public record DeadlineGatewayObjectRef(
    String tenantId, String objectType, String objectId, String naturalKey) {

  public DeadlineGatewayObjectRef {
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
