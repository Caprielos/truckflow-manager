package it.gabriele.truckflow.web.deadlineservice;

import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationPlan;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationStatus;
import java.time.LocalDate;
import java.util.List;

/** Risultato REST aggregato della valutazione scadenze di un oggetto. */
public record DeadlineEvaluationPlanResponse(
    String tenantId,
    String objectType,
    String objectId,
    String naturalKey,
    String configuredCountry,
    LocalDate evaluatedAt,
    DeadlineEvaluationStatus overallStatus,
    boolean canOperate,
    List<DeadlineEvaluationResponse> evaluations) {

  static DeadlineEvaluationPlanResponse fromDomain(DeadlineEvaluationPlan plan) {
    return new DeadlineEvaluationPlanResponse(
        plan.subject().objectRef().tenantId(),
        plan.subject().objectRef().objectType(),
        plan.subject().objectRef().objectId(),
        plan.subject().objectRef().naturalKey(),
        plan.subject().configuredCountry(),
        plan.evaluatedAt(),
        plan.overallStatus(),
        plan.canOperate(),
        plan.evaluations().stream().map(DeadlineEvaluationResponse::fromDomain).toList());
  }
}
