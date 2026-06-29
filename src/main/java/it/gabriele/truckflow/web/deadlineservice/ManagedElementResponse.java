package it.gabriele.truckflow.web.deadlineservice;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineRuleSourceType;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCategory;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementDefinition;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementOwnerType;
import java.util.Set;

/** DTO REST che espone il catalogo degli elementi valutabili dal deadline-service. */
public record ManagedElementResponse(
    ManagedElementCode code,
    ManagedElementCategory category,
    ManagedElementOwnerType ownerType,
    Set<DeadlineRuleSourceType> expectedSources,
    boolean dynamicByConfiguredCountry,
    boolean dynamicByManufacturerModel,
    boolean requiresOperationalWorkflow,
    boolean requiresContinuousMonitoring,
    boolean requiresSecurityControl,
    boolean canBlockOperations) {

  static ManagedElementResponse fromDomain(ManagedElementDefinition definition) {
    return new ManagedElementResponse(
        definition.code(),
        definition.category(),
        definition.ownerType(),
        definition.expectedSources(),
        definition.dynamicByConfiguredCountry(),
        definition.dynamicByManufacturerModel(),
        definition.requiresOperationalWorkflow(),
        definition.requiresContinuousMonitoring(),
        definition.requiresSecurityControl(),
        definition.canBlockOperations());
  }
}
