package it.gabriele.truckflow.infrastructure.client.deadline;

import it.gabriele.truckflow.application.port.out.deadline.DeadlineGateway;
import it.gabriele.truckflow.application.port.out.deadline.DeadlineGatewayEvaluation;
import it.gabriele.truckflow.application.port.out.deadline.DeadlineGatewayObjectRef;
import it.gabriele.truckflow.application.port.out.deadline.DeadlineGatewayResult;
import it.gabriele.truckflow.application.port.out.deadline.DeadlineGatewayStatus;
import it.gabriele.truckflow.application.port.out.deadline.DeadlineGatewaySubject;
import it.gabriele.truckflow.deadlineservice.application.DeadlineServiceFacade;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineObjectRef;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineRuleSourceType;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluation;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationPlan;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adapter temporaneo in-process verso il deadline-service.
 *
 * <p>Serve nella fase monolitica: mantiene già il confine architetturale, ma senza chiamate HTTP.
 * Quando il microservizio verrà separato, questa classe sarà sostituita da un client REST/gRPC.
 */
public final class InProcessDeadlineGateway implements DeadlineGateway {
  private final DeadlineServiceFacade deadlineServiceFacade;

  public InProcessDeadlineGateway(DeadlineServiceFacade deadlineServiceFacade) {
    if (deadlineServiceFacade == null) {
      throw new IllegalArgumentException("deadlineServiceFacade è obbligatorio.");
    }
    this.deadlineServiceFacade = deadlineServiceFacade;
  }

  @Override
  public DeadlineGatewayResult evaluate(DeadlineGatewaySubject subject, LocalDate evaluationDate) {
    return toGatewayResult(
        deadlineServiceFacade.evaluate(toDeadlineSubject(subject), evaluationDate), subject);
  }

  @Override
  public List<DeadlineGatewayResult> evaluateBatch(
      List<DeadlineGatewaySubject> subjects, LocalDate evaluationDate) {
    List<DeadlineGatewaySubject> safeSubjects =
        subjects == null ? List.of() : List.copyOf(subjects);
    List<DeadlineEvaluationPlan> plans =
        deadlineServiceFacade.evaluateBatch(
            safeSubjects.stream().map(InProcessDeadlineGateway::toDeadlineSubject).toList(),
            evaluationDate);

    if (plans.size() != safeSubjects.size()) {
      throw new IllegalStateException(
          "Il deadline-service ha restituito un numero di risultati diverso dagli oggetti"
              + " valutati.");
    }

    return java.util.stream.IntStream.range(0, plans.size())
        .mapToObj(index -> toGatewayResult(plans.get(index), safeSubjects.get(index)))
        .toList();
  }

  private static DeadlineSubject toDeadlineSubject(DeadlineGatewaySubject subject) {
    if (subject == null) {
      throw new IllegalArgumentException("subject è obbligatorio.");
    }
    return new DeadlineSubject(
        toDeadlineObjectRef(subject.objectRef()),
        subject.configuredCountry(),
        subject.manufacturer(),
        subject.model(),
        subject.elementCodes().stream()
            .map(InProcessDeadlineGateway::toManagedElementCode)
            .collect(Collectors.toSet()),
        subject.facts());
  }

  private static DeadlineObjectRef toDeadlineObjectRef(DeadlineGatewayObjectRef objectRef) {
    return new DeadlineObjectRef(
        objectRef.tenantId(), objectRef.objectType(), objectRef.objectId(), objectRef.naturalKey());
  }

  private static ManagedElementCode toManagedElementCode(String elementCode) {
    try {
      return ManagedElementCode.valueOf(elementCode);
    } catch (IllegalArgumentException exception) {
      throw new IllegalArgumentException(
          "Elemento gestito non riconosciuto: " + elementCode, exception);
    }
  }

  private static DeadlineGatewayResult toGatewayResult(
      DeadlineEvaluationPlan plan, DeadlineGatewaySubject originalSubject) {
    return new DeadlineGatewayResult(
        originalSubject,
        plan.evaluatedAt(),
        DeadlineGatewayStatus.valueOf(plan.overallStatus().name()),
        plan.canOperate(),
        plan.evaluations().stream().map(InProcessDeadlineGateway::toGatewayEvaluation).toList());
  }

  private static DeadlineGatewayEvaluation toGatewayEvaluation(DeadlineEvaluation evaluation) {
    return new DeadlineGatewayEvaluation(
        evaluation.elementCode().name(),
        DeadlineGatewayStatus.valueOf(evaluation.status().name()),
        evaluation.sourceRuleId(),
        toSourceNames(evaluation.sourceTypes()),
        evaluation.nextDueDate(),
        evaluation.nextDueKm(),
        evaluation.preventsOperation(),
        evaluation.explanation());
  }

  private static Set<String> toSourceNames(Set<DeadlineRuleSourceType> sourceTypes) {
    return sourceTypes.stream().map(Enum::name).collect(Collectors.toSet());
  }
}
