package it.gabriele.truckflow.domain.customs;

/** Regole di dominio per dogana e viaggi internazionali. */
public final class CustomsRules {

  private CustomsRules() {}

  public static boolean canBeSubmitted(CustomsDeclaration declaration) {
    validateDeclaration(declaration);

    return declaration.getStatus() == CustomsStatus.DRAFT
        && !declaration.getRequiredDocuments().isEmpty();
  }

  public static boolean canBeCleared(CustomsDeclaration declaration) {
    validateDeclaration(declaration);

    return declaration.getStatus() == CustomsStatus.SUBMITTED
        || declaration.getStatus() == CustomsStatus.WAITING_INSPECTION;
  }

  public static boolean hasCustomsCost(CustomsDeclaration declaration) {
    validateDeclaration(declaration);

    return declaration.getEstimatedCost() != null;
  }

  private static void validateDeclaration(CustomsDeclaration declaration) {
    if (declaration == null) {
      throw new IllegalArgumentException("La dichiarazione doganale è obbligatoria.");
    }
  }
}
