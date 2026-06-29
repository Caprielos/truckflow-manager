package it.gabriele.truckflow.domain.workflow;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa workflow configurabili. */
class WorkflowModelTest {

  @Test
  void shouldActivateDefinitionAndStartInstance() {
    WorkflowDefinition definition = workflowDefinition().activate();
    WorkflowInstance instance =
        WorkflowInstance.start(
            "wf-inst-001", definition, Instant.parse("2026-06-01T08:00:00Z"), Notes.empty());

    assertTrue(definition.isActive());
    assertEquals("OPEN", instance.getCurrentStepCode());
    assertEquals(WorkflowStatus.IN_PROGRESS, instance.getStatus());
  }

  @Test
  void shouldMoveWorkflowAndCompleteIt() {
    WorkflowDefinition definition = workflowDefinition().activate();
    WorkflowInstance completed =
        WorkflowInstance.start(
                "wf-inst-001", definition, Instant.parse("2026-06-01T08:00:00Z"), Notes.empty())
            .moveTo("APPROVAL")
            .waitApproval()
            .complete(Instant.parse("2026-06-01T10:00:00Z"));

    assertEquals(WorkflowStatus.COMPLETED, completed.getStatus());
    assertFalse(completed.isActive());
  }

  private static WorkflowDefinition workflowDefinition() {
    return WorkflowDefinition.draft(
        "claim-workflow",
        "Gestione claim cliente",
        List.of(
            WorkflowStep.of("open", "Apertura claim", 1, false),
            WorkflowStep.of("approval", "Approvazione chiusura", 2, true)),
        Notes.empty());
  }
}
