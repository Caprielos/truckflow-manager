package it.gabriele.truckflow.domain.documents;

public record DocumentReference(
    DocumentReferenceType referenceType, String referencedId, String notes) {

  public DocumentReference {
    referenceType = DocumentValidation.requireNonNull(referenceType, "referenceType");
    referencedId = DocumentValidation.requireText(referencedId, "referencedId");
    notes = DocumentValidation.normalize(notes);
  }

  public boolean references(DocumentReferenceType referenceType, String referencedId) {
    return this.referenceType == DocumentValidation.requireNonNull(referenceType, "referenceType")
        && this.referencedId.equals(DocumentValidation.requireText(referencedId, "referencedId"));
  }
}
