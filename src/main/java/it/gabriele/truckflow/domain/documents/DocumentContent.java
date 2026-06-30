package it.gabriele.truckflow.domain.documents;

public record DocumentContent(String body, String summary, String notes) {

  public DocumentContent {
    body = DocumentValidation.normalize(body);
    summary = DocumentValidation.normalize(summary);
    notes = DocumentValidation.normalize(notes);
  }

  public static DocumentContent empty() {
    return new DocumentContent("", "", "");
  }

  public boolean hasContent() {
    return !body.isBlank() || !summary.isBlank() || !notes.isBlank();
  }
}
