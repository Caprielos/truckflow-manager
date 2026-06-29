package it.gabriele.truckflow.web.deadlineservice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.gabriele.truckflow.deadlineservice.application.DeadlineRulePackProvider;
import it.gabriele.truckflow.deadlineservice.application.EvaluateDeadlineBatchCommand;
import it.gabriele.truckflow.deadlineservice.application.EvaluateDeadlineBatchUseCase;
import it.gabriele.truckflow.deadlineservice.application.EvaluateDeadlineCommand;
import it.gabriele.truckflow.deadlineservice.application.EvaluateDeadlineUseCase;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineObjectRef;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCatalog;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationPlan;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePack;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API REST iniziale del futuro compliance-deadline-service.
 *
 * <p>Per ora vive nello stesso processo Spring Boot, ma usa DTO generici e use case dedicati per
 * poter essere estratta facilmente in un microservizio separato.
 */
@Tag(name = "Deadline Service")
@RestController
@RequestMapping("/api/deadline-service")
public class DeadlineServiceController {
  private final EvaluateDeadlineUseCase evaluateDeadlineUseCase;
  private final EvaluateDeadlineBatchUseCase evaluateDeadlineBatchUseCase;
  private final DeadlineRulePackProvider deadlineRulePackProvider;

  public DeadlineServiceController(
      EvaluateDeadlineUseCase evaluateDeadlineUseCase,
      EvaluateDeadlineBatchUseCase evaluateDeadlineBatchUseCase,
      DeadlineRulePackProvider deadlineRulePackProvider) {
    this.evaluateDeadlineUseCase = evaluateDeadlineUseCase;
    this.evaluateDeadlineBatchUseCase = evaluateDeadlineBatchUseCase;
    this.deadlineRulePackProvider = deadlineRulePackProvider;
  }

  @Operation(
      summary = "Catalogo elementi gestiti",
      description =
          "Restituisce tutti gli elementi che il deadline-service sa valutare: documenti, "
              + "manutenzione, rimorchi, carico, magazzino, viaggio, sicurezza e telemetria.")
  @GetMapping("/managed-elements")
  public List<ManagedElementResponse> findManagedElements() {
    return ManagedElementCatalog.all().stream().map(ManagedElementResponse::fromDomain).toList();
  }

  @Operation(
      summary = "Rule pack attivo",
      description = "Restituisce le informazioni sintetiche sul rule pack attualmente caricato.")
  @GetMapping("/rule-pack")
  public DeadlineRulePackSummaryResponse findActiveRulePack() {
    DeadlineRulePack rulePack = deadlineRulePackProvider.activeRulePackFor(sampleSubject());
    return DeadlineRulePackSummaryResponse.fromDomain(rulePack);
  }

  @Operation(
      summary = "Valuta scadenze di un oggetto",
      description =
          "Riceve un DeadlineSubject generico e restituisce lo stato calcolato dal "
              + "deadline-service rispetto al rule pack attivo.")
  @PostMapping("/evaluations")
  public DeadlineEvaluationPlanResponse evaluateDeadline(
      @Valid @RequestBody EvaluateDeadlineRequest request) {
    DeadlineEvaluationPlan plan =
        evaluateDeadlineUseCase.evaluate(
            new EvaluateDeadlineCommand(request.subject().toDomain(), request.evaluationDate()));
    return DeadlineEvaluationPlanResponse.fromDomain(plan);
  }

  @Operation(
      summary = "Valuta scadenze in batch",
      description = "Valuta più oggetti nello stesso momento logico con un'unica richiesta REST.")
  @PostMapping("/evaluations/batch")
  public List<DeadlineEvaluationPlanResponse> evaluateDeadlineBatch(
      @Valid @RequestBody EvaluateDeadlineBatchRequest request) {
    List<DeadlineEvaluationPlan> plans =
        evaluateDeadlineBatchUseCase.evaluateBatch(
            new EvaluateDeadlineBatchCommand(
                request.subjects().stream().map(DeadlineSubjectRequest::toDomain).toList(),
                request.evaluationDate()));
    return plans.stream().map(DeadlineEvaluationPlanResponse::fromDomain).toList();
  }

  private static DeadlineSubject sampleSubject() {
    return new DeadlineSubject(
        new DeadlineObjectRef("DEFAULT", "SYSTEM", "RULE-PACK", ""),
        "IT",
        "",
        "",
        Set.of(ManagedElementCode.VEHICLE_ENGINE_OIL),
        Map.of());
  }
}
