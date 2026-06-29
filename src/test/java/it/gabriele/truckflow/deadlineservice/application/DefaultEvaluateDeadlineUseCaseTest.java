package it.gabriele.truckflow.deadlineservice.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineObjectRef;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineRuleSourceType;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationEngine;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationPlan;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationStatus;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRuleIntervalType;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePack;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePackRule;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePackStatus;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRuleSlotStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DefaultEvaluateDeadlineUseCaseTest {

  @Test
  void shouldEvaluateSingleSubjectThroughApplicationUseCase() {
    EvaluateDeadlineUseCase useCase =
        new DefaultEvaluateDeadlineUseCase(
            provider(rulePack(activeRoadworthinessRule())), new DeadlineEvaluationEngine());

    DeadlineEvaluationPlan plan =
        useCase.evaluate(
            new EvaluateDeadlineCommand(
                subject(
                    Set.of(ManagedElementCode.VEHICLE_ROADWORTHINESS_TEST),
                    Map.of("dueDate.VEHICLE_ROADWORTHINESS_TEST", "2026-06-01")),
                LocalDate.of(2026, 6, 30)));

    assertEquals(DeadlineEvaluationStatus.BLOCKING, plan.overallStatus());
    assertFalse(plan.canOperate());
    assertEquals("IT_VEHICLE_ROADWORTHINESS_N3", plan.evaluations().getFirst().sourceRuleId());
  }

  @Test
  void shouldEvaluateBatchWithSameApplicationBoundary() {
    EvaluateDeadlineUseCase singleUseCase =
        new DefaultEvaluateDeadlineUseCase(
            provider(rulePack(activeEngineOilRule())), new DeadlineEvaluationEngine());
    EvaluateDeadlineBatchUseCase batchUseCase =
        new DefaultEvaluateDeadlineBatchUseCase(singleUseCase);

    List<DeadlineEvaluationPlan> plans =
        batchUseCase.evaluateBatch(
            new EvaluateDeadlineBatchCommand(
                List.of(
                    subject(
                        Set.of(ManagedElementCode.VEHICLE_ENGINE_OIL),
                        Map.of(
                            "lastDate.VEHICLE_ENGINE_OIL",
                            "2026-01-01",
                            "lastKm.VEHICLE_ENGINE_OIL",
                            "100000",
                            "currentKm",
                            "186000")),
                    subject(
                        Set.of(ManagedElementCode.VEHICLE_ENGINE_OIL),
                        Map.of(
                            "lastDate.VEHICLE_ENGINE_OIL",
                            "2026-01-01",
                            "lastKm.VEHICLE_ENGINE_OIL",
                            "100000",
                            "currentKm",
                            "150000"))),
                LocalDate.of(2026, 6, 30)));

    assertEquals(2, plans.size());
    assertEquals(DeadlineEvaluationStatus.DUE_SOON, plans.get(0).overallStatus());
    assertEquals(DeadlineEvaluationStatus.OK, plans.get(1).overallStatus());
  }

  @Test
  void shouldExposeSimpleFacadeForFutureRestOrGrpcLayer() {
    DeadlineServiceFacade facade = DeadlineServiceFacade.usingDefaultRulePack();

    DeadlineEvaluationPlan plan =
        facade.evaluate(
            subject(Set.of(ManagedElementCode.VEHICLE_ENGINE_OIL), Map.of()),
            LocalDate.of(2026, 6, 30));

    assertTrue(plan.canOperate());
    assertEquals(DeadlineEvaluationStatus.CONFIGURATION_MISSING, plan.overallStatus());
  }

  private static DeadlineRulePackProvider provider(DeadlineRulePack rulePack) {
    return subject -> rulePack;
  }

  private static DeadlineSubject subject(
      Set<ManagedElementCode> elements, Map<String, String> facts) {
    return new DeadlineSubject(
        new DeadlineObjectRef("DEFAULT", "VEHICLE", "VEH-001", "AB123CD"),
        "IT",
        "IVECO",
        "S-WAY",
        elements,
        facts);
  }

  private static DeadlineRulePack rulePack(DeadlineRulePackRule rule) {
    return new DeadlineRulePack(
        "test-rule-pack", "2026.1", DeadlineRulePackStatus.DRAFT, "IT", "DEFAULT", List.of(rule));
  }

  private static DeadlineRulePackRule activeRoadworthinessRule() {
    return new DeadlineRulePackRule(
        "IT_VEHICLE_ROADWORTHINESS_N3",
        ManagedElementCode.VEHICLE_ROADWORTHINESS_TEST,
        Set.of(DeadlineRuleSourceType.EU_LAW, DeadlineRuleSourceType.NATIONAL_LAW),
        DeadlineRuleSlotStatus.ACTIVE,
        false,
        "Revisione mezzo bloccante.",
        DeadlineRuleIntervalType.DATE_BASED,
        365,
        null,
        30,
        0,
        true);
  }

  private static DeadlineRulePackRule activeEngineOilRule() {
    return new DeadlineRulePackRule(
        "IVECO_SWAY_ENGINE_OIL",
        ManagedElementCode.VEHICLE_ENGINE_OIL,
        Set.of(DeadlineRuleSourceType.MANUFACTURER_RULEBOOK),
        DeadlineRuleSlotStatus.ACTIVE,
        false,
        "Olio motore Iveco S-Way.",
        DeadlineRuleIntervalType.DATE_OR_DISTANCE,
        365,
        90000L,
        30,
        5000,
        false);
  }
}
