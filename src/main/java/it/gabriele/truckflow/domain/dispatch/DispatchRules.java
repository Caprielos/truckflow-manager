package it.gabriele.truckflow.domain.dispatch;

/** Regole di dominio per la pianificazione operativa dell'ufficio traffico. */
public final class DispatchRules {

  private DispatchRules() {}

  public static boolean canAssign(DispatchAssignmentCandidate candidate) {
    validateCandidate(candidate);
    return candidate.isAssignable() && candidate.isProfitExpected();
  }

  public static boolean shouldReviewBeforeAssigning(DispatchAssignmentCandidate candidate) {
    validateCandidate(candidate);
    return candidate.requiresManualReview() || !candidate.isProfitExpected();
  }

  public static boolean planHasAssignableCandidate(DispatchPlan plan) {
    validatePlan(plan);
    return !plan.getAssignableCandidates().isEmpty();
  }

  private static void validateCandidate(DispatchAssignmentCandidate candidate) {
    if (candidate == null) {
      throw new IllegalArgumentException("Il candidato dispatch è obbligatorio.");
    }
  }

  private static void validatePlan(DispatchPlan plan) {
    if (plan == null) {
      throw new IllegalArgumentException("Il piano dispatch è obbligatorio.");
    }
  }
}
