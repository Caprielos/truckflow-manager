package it.gabriele.truckflow.deadlineservice.evaluation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineObjectRef;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineRuleSourceType;
import it.gabriele.truckflow.deadlineservice.domain.DeadlineSubject;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRuleIntervalType;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePack;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePackResourceLoader;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePackRule;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRulePackStatus;
import it.gabriele.truckflow.deadlineservice.rulepack.DeadlineRuleSlotStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DeadlineEvaluationEngineTest {
  private final DeadlineEvaluationEngine engine = new DeadlineEvaluationEngine();
  private final LocalDate today = LocalDate.of(2026, 6, 30);

  @Test
  void shouldReturnConfigurationMissingWhenDefaultRulePackContainsOnlyEmptySlots() {
    DeadlineEvaluationPlan plan =
        engine.evaluate(
            subject(Set.of(ManagedElementCode.VEHICLE_ENGINE_OIL), Map.of()),
            DeadlineRulePackResourceLoader.loadDefault(),
            today);

    assertEquals(DeadlineEvaluationStatus.CONFIGURATION_MISSING, plan.overallStatus());
    assertTrue(plan.canOperate());
    assertEquals(1, plan.evaluations().size());
    assertEquals("SLOT_VEHICLE_ENGINE_OIL", plan.evaluations().getFirst().sourceRuleId());
  }

  @Test
  void shouldReturnOkForConfiguredDateAndDistanceRuleNotCloseToDeadline() {
    DeadlineEvaluationPlan plan =
        engine.evaluate(
            subject(
                Set.of(ManagedElementCode.VEHICLE_ENGINE_OIL),
                Map.of(
                    "lastDate.VEHICLE_ENGINE_OIL", "2026-01-01",
                    "lastKm.VEHICLE_ENGINE_OIL", "100000",
                    "currentKm", "150000")),
            rulePack(
                activeRule(
                    "IVECO_SWAY_ENGINE_OIL",
                    ManagedElementCode.VEHICLE_ENGINE_OIL,
                    DeadlineRuleIntervalType.DATE_OR_DISTANCE,
                    365,
                    90000L,
                    30,
                    5000L,
                    false)),
            today);

    DeadlineEvaluation evaluation = plan.evaluations().getFirst();
    assertEquals(DeadlineEvaluationStatus.OK, plan.overallStatus());
    assertTrue(plan.canOperate());
    assertEquals(LocalDate.of(2027, 1, 1), evaluation.nextDueDate());
    assertEquals(190000L, evaluation.nextDueKm());
  }

  @Test
  void shouldReturnDueSoonForConfiguredRuleCloseToDueDistance() {
    DeadlineEvaluationPlan plan =
        engine.evaluate(
            subject(
                Set.of(ManagedElementCode.VEHICLE_ENGINE_OIL),
                Map.of(
                    "lastDate.VEHICLE_ENGINE_OIL", "2026-01-01",
                    "lastKm.VEHICLE_ENGINE_OIL", "100000",
                    "currentKm", "186000")),
            rulePack(
                activeRule(
                    "IVECO_SWAY_ENGINE_OIL",
                    ManagedElementCode.VEHICLE_ENGINE_OIL,
                    DeadlineRuleIntervalType.DATE_OR_DISTANCE,
                    365,
                    90000L,
                    30,
                    5000L,
                    false)),
            today);

    assertEquals(DeadlineEvaluationStatus.DUE_SOON, plan.overallStatus());
    assertTrue(plan.canOperate());
  }

  @Test
  void shouldBlockOperationsForOverdueBlockingLegalRule() {
    DeadlineEvaluationPlan plan =
        engine.evaluate(
            subject(
                Set.of(ManagedElementCode.VEHICLE_ROADWORTHINESS_TEST),
                Map.of("dueDate.VEHICLE_ROADWORTHINESS_TEST", "2026-06-01")),
            rulePack(
                activeRule(
                    "IT_VEHICLE_ROADWORTHINESS_N3",
                    ManagedElementCode.VEHICLE_ROADWORTHINESS_TEST,
                    DeadlineRuleIntervalType.DATE_BASED,
                    365,
                    null,
                    30,
                    0L,
                    true)),
            today);

    assertEquals(DeadlineEvaluationStatus.BLOCKING, plan.overallStatus());
    assertFalse(plan.canOperate());
    assertEquals(DeadlineEvaluationStatus.OVERDUE, plan.evaluations().getFirst().status());
    assertTrue(plan.evaluations().getFirst().preventsOperation());
  }

  @Test
  void shouldEvaluateContinuousMonitoringEventAsBlocking() {
    DeadlineEvaluationPlan plan =
        engine.evaluate(
            subject(
                Set.of(ManagedElementCode.TELEMATICS_UNAUTHORIZED_OPENING),
                Map.of("eventStatus.TELEMATICS_UNAUTHORIZED_OPENING", "BLOCKING")),
            rulePack(
                activeRule(
                    "SECURITY_UNAUTHORIZED_OPENING",
                    ManagedElementCode.TELEMATICS_UNAUTHORIZED_OPENING,
                    DeadlineRuleIntervalType.CONTINUOUS_EVENT,
                    null,
                    null,
                    0,
                    0L,
                    true)),
            today);

    assertEquals(DeadlineEvaluationStatus.BLOCKING, plan.overallStatus());
    assertFalse(plan.canOperate());
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

  private static DeadlineRulePackRule activeRule(
      String ruleId,
      ManagedElementCode elementCode,
      DeadlineRuleIntervalType intervalType,
      Integer intervalDays,
      Long intervalKm,
      int warningDaysBefore,
      long warningKmBefore,
      boolean blocksOperation) {
    return new DeadlineRulePackRule(
        ruleId,
        elementCode,
        Set.of(DeadlineRuleSourceType.MANUFACTURER_RULEBOOK),
        DeadlineRuleSlotStatus.ACTIVE,
        false,
        "Regola attiva di test.",
        intervalType,
        intervalDays,
        intervalKm,
        warningDaysBefore,
        warningKmBefore,
        blocksOperation);
  }
}
