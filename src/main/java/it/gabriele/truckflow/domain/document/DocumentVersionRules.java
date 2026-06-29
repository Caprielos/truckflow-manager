package it.gabriele.truckflow.domain.document;

/** Regole per versioning documentale. */
public final class DocumentVersionRules {

  private DocumentVersionRules() {}

  public static boolean canBeSuperseded(DocumentVersion version) {
    if (version == null) {
      throw new IllegalArgumentException("La versione documento è obbligatoria.");
    }

    return version.getStatus() == DocumentVersionStatus.CURRENT;
  }
}
