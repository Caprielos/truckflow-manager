package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.application.command.compliance.ActivateComplianceRequirementCommand;
import it.gabriele.truckflow.application.command.compliance.ArchiveComplianceRequirementCommand;
import it.gabriele.truckflow.application.command.compliance.DiscontinueComplianceRequirementCommand;
import it.gabriele.truckflow.application.command.compliance.FindComplianceRequirementCommand;
import it.gabriele.truckflow.application.command.compliance.RegisterComplianceRequirementCommand;
import it.gabriele.truckflow.application.command.compliance.SuspendComplianceRequirementCommand;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.usecase.compliance.ActivateComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.ArchiveComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.DiscontinueComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.FindComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.RegisterComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.SuspendComplianceRequirementService;
import it.gabriele.truckflow.domain.compliance.ComplianceCategory;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdiction;
import it.gabriele.truckflow.domain.compliance.ComplianceJurisdictionScope;
import it.gabriele.truckflow.domain.compliance.ComplianceObligationLevel;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementCode;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementId;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementStatus;
import it.gabriele.truckflow.domain.compliance.ComplianceRequirementType;
import it.gabriele.truckflow.domain.compliance.ComplianceRule;
import it.gabriele.truckflow.domain.compliance.ComplianceSeverity;
import it.gabriele.truckflow.domain.compliance.ComplianceSource;
import it.gabriele.truckflow.domain.compliance.ComplianceSourceType;
import it.gabriele.truckflow.domain.compliance.ComplianceTarget;
import it.gabriele.truckflow.domain.compliance.ComplianceTargetType;
import it.gabriele.truckflow.infrastructure.memory.compliance.InMemoryComplianceRequirementRepository;
import org.junit.jupiter.api.Test;

class ApplicationComplianceUseCaseExpansionTest {

  @Test
  void complianceRequirementUseCasesRegisterFindAndManageStatusesUsingCopyOnWrite() {
    var context = new ComplianceUseCaseContext();

    var registered = context.registerRequirement.execute(requirementCommand("CMP-ADR-APP-001"));

    assertEquals(ComplianceRequirementCode.of("cmp-adr-app-001"), registered.code());
    assertEquals(ComplianceRequirementStatus.ACTIVE, registered.status());
    assertEquals(ComplianceTargetType.CARGO, registered.targetType());
    assertEquals(ComplianceJurisdictionScope.EUROPEAN_UNION, registered.jurisdictionScope());
    assertTrue(registered.active());
    assertTrue(registered.mandatory());
    assertTrue(registered.critical());

    var found =
        context.findRequirement.execute(new FindComplianceRequirementCommand(registered.id()));
    assertEquals(registered, found);

    var storedActive = context.requirementRepository.findById(registered.id()).orElseThrow();
    var suspended =
        context.suspendRequirement.execute(
            new SuspendComplianceRequirementCommand(registered.id()));
    assertEquals(ComplianceRequirementStatus.SUSPENDED, suspended.status());
    assertFalse(suspended.active());
    assertEquals(ComplianceRequirementStatus.ACTIVE, storedActive.status());

    var activeAgain =
        context.activateRequirement.execute(
            new ActivateComplianceRequirementCommand(registered.id()));
    assertEquals(ComplianceRequirementStatus.ACTIVE, activeAgain.status());

    var archived =
        context.archiveRequirement.execute(
            new ArchiveComplianceRequirementCommand(registered.id()));
    assertEquals(ComplianceRequirementStatus.ARCHIVED, archived.status());

    var discontinued =
        context.discontinueRequirement.execute(
            new DiscontinueComplianceRequirementCommand(registered.id()));
    assertEquals(ComplianceRequirementStatus.DISCONTINUED, discontinued.status());
  }

  @Test
  void
      complianceRequirementUseCasesRejectDuplicatesMissingResourcesNullInputsAndNullDependencies() {
    var context = new ComplianceUseCaseContext();
    context.registerRequirement.execute(requirementCommand("CMP-DUP-001"));
    var unknownRequirementId = ComplianceRequirementId.random();

    assertThrows(
        DuplicateResourceException.class,
        () -> context.registerRequirement.execute(requirementCommand("cmp-dup-001")));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.findRequirement.execute(
                new FindComplianceRequirementCommand(unknownRequirementId)));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.activateRequirement.execute(
                new ActivateComplianceRequirementCommand(unknownRequirementId)));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.suspendRequirement.execute(
                new SuspendComplianceRequirementCommand(unknownRequirementId)));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.archiveRequirement.execute(
                new ArchiveComplianceRequirementCommand(unknownRequirementId)));
    assertThrows(
        ResourceNotFoundException.class,
        () ->
            context.discontinueRequirement.execute(
                new DiscontinueComplianceRequirementCommand(unknownRequirementId)));

    assertThrows(UseCaseValidationException.class, () -> context.registerRequirement.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.findRequirement.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.activateRequirement.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.suspendRequirement.execute(null));
    assertThrows(UseCaseValidationException.class, () -> context.archiveRequirement.execute(null));
    assertThrows(
        UseCaseValidationException.class, () -> context.discontinueRequirement.execute(null));

    assertThrows(
        UseCaseValidationException.class, () -> new RegisterComplianceRequirementService(null));
    assertThrows(
        UseCaseValidationException.class, () -> new FindComplianceRequirementService(null));
    assertThrows(
        UseCaseValidationException.class, () -> new ActivateComplianceRequirementService(null));
    assertThrows(
        UseCaseValidationException.class, () -> new SuspendComplianceRequirementService(null));
    assertThrows(
        UseCaseValidationException.class, () -> new ArchiveComplianceRequirementService(null));
    assertThrows(
        UseCaseValidationException.class, () -> new DiscontinueComplianceRequirementService(null));
  }

  @Test
  void complianceRequirementCommandRejectsMissingRequiredInputsAndNormalizesText() {
    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterComplianceRequirementCommand(
                null,
                "ADR requirement",
                "Description",
                ComplianceRequirementStatus.ACTIVE,
                ComplianceCategory.CARGO,
                ComplianceRequirementType.CARGO_REGULATORY_REQUIRED,
                ComplianceObligationLevel.MANDATORY,
                ComplianceSeverity.CRITICAL,
                target(),
                rule(),
                source(),
                jurisdiction(),
                "Notes"));

    assertThrows(
        UseCaseValidationException.class,
        () ->
            new RegisterComplianceRequirementCommand(
                ComplianceRequirementCode.of("CMP-BLANK-NAME"),
                "   ",
                "Description",
                ComplianceRequirementStatus.ACTIVE,
                ComplianceCategory.CARGO,
                ComplianceRequirementType.CARGO_REGULATORY_REQUIRED,
                ComplianceObligationLevel.MANDATORY,
                ComplianceSeverity.CRITICAL,
                target(),
                rule(),
                source(),
                jurisdiction(),
                "Notes"));

    var command =
        new RegisterComplianceRequirementCommand(
            ComplianceRequirementCode.of("CMP-NORMALIZE"),
            "  ADR requirement  ",
            null,
            ComplianceRequirementStatus.ACTIVE,
            ComplianceCategory.CARGO,
            ComplianceRequirementType.CARGO_REGULATORY_REQUIRED,
            ComplianceObligationLevel.MANDATORY,
            ComplianceSeverity.CRITICAL,
            target(),
            rule(),
            source(),
            jurisdiction(),
            null);

    assertEquals("ADR requirement", command.name());
    assertEquals("", command.description());
    assertEquals("", command.notes());
  }

  private static RegisterComplianceRequirementCommand requirementCommand(String code) {
    return new RegisterComplianceRequirementCommand(
        ComplianceRequirementCode.of(code),
        "ADR requirement " + code,
        "Dangerous cargo must declare ADR regulatory requirements before planning.",
        ComplianceRequirementStatus.ACTIVE,
        ComplianceCategory.CARGO,
        ComplianceRequirementType.CARGO_REGULATORY_REQUIRED,
        ComplianceObligationLevel.MANDATORY,
        ComplianceSeverity.CRITICAL,
        target(),
        rule(),
        source(),
        jurisdiction(),
        "Application 6L compliance requirement");
  }

  private static ComplianceTarget target() {
    return new ComplianceTarget(ComplianceTargetType.CARGO, "Applies to ADR cargo.");
  }

  private static ComplianceRule rule() {
    return new ComplianceRule(
        "ADR cargo rule",
        "Cargo marked as ADR must require ADR-compatible transport.",
        "ADR transport requirement must be declared.",
        "Descriptive rule only.");
  }

  private static ComplianceSource source() {
    return new ComplianceSource(
        "European ADR framework",
        ComplianceSourceType.EU_REGULATION,
        "ADR",
        "Reference source for dangerous goods transport.",
        "Conceptual source, no automatic legal check.");
  }

  private static ComplianceJurisdiction jurisdiction() {
    return ComplianceJurisdiction.europeanUnion();
  }

  private static final class ComplianceUseCaseContext {

    private final InMemoryComplianceRequirementRepository requirementRepository =
        new InMemoryComplianceRequirementRepository();

    private final RegisterComplianceRequirementService registerRequirement =
        new RegisterComplianceRequirementService(requirementRepository);
    private final FindComplianceRequirementService findRequirement =
        new FindComplianceRequirementService(requirementRepository);
    private final ActivateComplianceRequirementService activateRequirement =
        new ActivateComplianceRequirementService(requirementRepository);
    private final SuspendComplianceRequirementService suspendRequirement =
        new SuspendComplianceRequirementService(requirementRepository);
    private final ArchiveComplianceRequirementService archiveRequirement =
        new ArchiveComplianceRequirementService(requirementRepository);
    private final DiscontinueComplianceRequirementService discontinueRequirement =
        new DiscontinueComplianceRequirementService(requirementRepository);
  }
}
