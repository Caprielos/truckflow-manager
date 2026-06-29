package it.gabriele.truckflow.domain.pod;

/** Regole per POD digitale, prove consegna e contestazioni. */
public final class PodRules {

  private PodRules() {}

  public static boolean isLegallyStrong(DigitalProofOfDelivery pod) {
    if (pod == null) {
      throw new IllegalArgumentException("Il POD è obbligatorio.");
    }
    return pod.hasEvidence(PodEvidenceType.RECIPIENT_SIGNATURE)
        && pod.hasEvidence(PodEvidenceType.TIMESTAMP)
        && pod.hasGeoLocation()
        && !pod.hasReservations();
  }

  public static boolean requiresClaimReview(DigitalProofOfDelivery pod) {
    if (pod == null) {
      throw new IllegalArgumentException("Il POD è obbligatorio.");
    }
    return pod.status() == PodStatus.DISPUTED
        || pod.hasReservations()
        || pod.hasEvidence(PodEvidenceType.PHOTO_DAMAGE)
        || !pod.hasEvidence(PodEvidenceType.RECIPIENT_SIGNATURE);
  }

  public static boolean canArchive(DigitalProofOfDelivery pod) {
    if (pod == null) {
      throw new IllegalArgumentException("Il POD è obbligatorio.");
    }
    return pod.status() == PodStatus.VALIDATED || pod.status() == PodStatus.WITH_RESERVATIONS;
  }
}
