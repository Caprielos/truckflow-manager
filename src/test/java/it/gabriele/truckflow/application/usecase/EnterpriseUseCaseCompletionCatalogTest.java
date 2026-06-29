package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.gabriele.truckflow.application.port.in.adr.ValidateAdrComplianceUseCase;
import it.gabriele.truckflow.application.port.in.atp.ValidateAtpComplianceUseCase;
import it.gabriele.truckflow.application.port.in.driverscheduling.EvaluateDriverAssignmentUseCase;
import it.gabriele.truckflow.application.port.in.financeops.CalculateMissionRealMarginUseCase;
import it.gabriele.truckflow.application.port.in.foodsafety.ValidateFoodSafetyComplianceUseCase;
import it.gabriele.truckflow.application.port.in.livestock.ValidateLivestockComplianceUseCase;
import it.gabriele.truckflow.application.port.in.operation.EvaluateEnterpriseMissionReadinessUseCase;
import it.gabriele.truckflow.application.port.in.oversized.ValidateOversizedComplianceUseCase;
import it.gabriele.truckflow.application.port.in.pod.CollectDigitalProofOfDeliveryUseCase;
import it.gabriele.truckflow.application.port.in.pod.ValidateDigitalProofOfDeliveryUseCase;
import it.gabriele.truckflow.application.port.in.regulation.EvaluateRegulatoryRequirementUseCase;
import it.gabriele.truckflow.application.port.in.regulation.SelectCountryRegulationUseCase;
import it.gabriele.truckflow.application.port.in.roadinspection.ApplyRoadInspectionOutcomeUseCase;
import it.gabriele.truckflow.application.port.in.roadtransport.ValidatePhysicalTransportComplianceUseCase;
import it.gabriele.truckflow.application.port.in.routeoptimization.EvaluateRouteOptimizationPlanUseCase;
import it.gabriele.truckflow.application.port.in.securitypolicy.EvaluateSecurityPolicyUseCase;
import it.gabriele.truckflow.application.port.in.suppliercontract.EvaluateSubcontractorEligibilityUseCase;
import it.gabriele.truckflow.application.port.in.tachograph.DetectTachographViolationsUseCase;
import it.gabriele.truckflow.application.port.in.waste.ValidateWasteComplianceUseCase;
import it.gabriele.truckflow.application.usecase.adr.DefaultValidateAdrComplianceUseCase;
import it.gabriele.truckflow.application.usecase.atp.DefaultValidateAtpComplianceUseCase;
import it.gabriele.truckflow.application.usecase.driverscheduling.DefaultEvaluateDriverAssignmentUseCase;
import it.gabriele.truckflow.application.usecase.financeops.DefaultCalculateMissionRealMarginUseCase;
import it.gabriele.truckflow.application.usecase.foodsafety.DefaultValidateFoodSafetyComplianceUseCase;
import it.gabriele.truckflow.application.usecase.livestock.DefaultValidateLivestockComplianceUseCase;
import it.gabriele.truckflow.application.usecase.operation.DefaultEvaluateEnterpriseMissionReadinessUseCase;
import it.gabriele.truckflow.application.usecase.oversized.DefaultValidateOversizedComplianceUseCase;
import it.gabriele.truckflow.application.usecase.pod.DefaultValidateDigitalProofOfDeliveryUseCase;
import it.gabriele.truckflow.application.usecase.regulation.DefaultSelectCountryRegulationUseCase;
import it.gabriele.truckflow.application.usecase.roadinspection.DefaultApplyRoadInspectionOutcomeUseCase;
import it.gabriele.truckflow.application.usecase.roadtransport.DefaultValidatePhysicalTransportComplianceUseCase;
import it.gabriele.truckflow.application.usecase.routeoptimization.DefaultEvaluateRouteOptimizationPlanUseCase;
import it.gabriele.truckflow.application.usecase.securitypolicy.DefaultEvaluateSecurityPolicyUseCase;
import it.gabriele.truckflow.application.usecase.suppliercontract.DefaultEvaluateSubcontractorEligibilityUseCase;
import it.gabriele.truckflow.application.usecase.tachograph.DefaultDetectTachographViolationsUseCase;
import it.gabriele.truckflow.application.usecase.waste.DefaultValidateWasteComplianceUseCase;
import org.junit.jupiter.api.Test;

class EnterpriseUseCaseCompletionCatalogTest {

  @Test
  void shouldExposeEnterpriseUseCaseFamilies() {
    assertNotNull(SelectCountryRegulationUseCase.class);
    assertNotNull(EvaluateRegulatoryRequirementUseCase.class);
    assertNotNull(ValidatePhysicalTransportComplianceUseCase.class);
    assertNotNull(ValidateAdrComplianceUseCase.class);
    assertNotNull(ValidateAtpComplianceUseCase.class);
    assertNotNull(ValidateWasteComplianceUseCase.class);
    assertNotNull(ValidateFoodSafetyComplianceUseCase.class);
    assertNotNull(ValidateLivestockComplianceUseCase.class);
    assertNotNull(ValidateOversizedComplianceUseCase.class);
    assertNotNull(ApplyRoadInspectionOutcomeUseCase.class);
    assertNotNull(ValidateDigitalProofOfDeliveryUseCase.class);
    assertNotNull(DetectTachographViolationsUseCase.class);
    assertNotNull(EvaluateDriverAssignmentUseCase.class);
    assertNotNull(EvaluateSubcontractorEligibilityUseCase.class);
    assertNotNull(CalculateMissionRealMarginUseCase.class);
    assertNotNull(EvaluateRouteOptimizationPlanUseCase.class);
    assertNotNull(EvaluateSecurityPolicyUseCase.class);
    assertNotNull(EvaluateEnterpriseMissionReadinessUseCase.class);
    assertNotNull(CollectDigitalProofOfDeliveryUseCase.class);
  }

  @Test
  void shouldProvideDefaultEnterpriseUseCaseImplementations() {
    assertNotNull(DefaultSelectCountryRegulationUseCase.class);
    assertNotNull(DefaultValidatePhysicalTransportComplianceUseCase.class);
    assertNotNull(DefaultValidateAdrComplianceUseCase.class);
    assertNotNull(DefaultValidateAtpComplianceUseCase.class);
    assertNotNull(DefaultValidateWasteComplianceUseCase.class);
    assertNotNull(DefaultValidateFoodSafetyComplianceUseCase.class);
    assertNotNull(DefaultValidateLivestockComplianceUseCase.class);
    assertNotNull(DefaultValidateOversizedComplianceUseCase.class);
    assertNotNull(DefaultApplyRoadInspectionOutcomeUseCase.class);
    assertNotNull(DefaultValidateDigitalProofOfDeliveryUseCase.class);
    assertNotNull(DefaultDetectTachographViolationsUseCase.class);
    assertNotNull(DefaultEvaluateDriverAssignmentUseCase.class);
    assertNotNull(DefaultEvaluateSubcontractorEligibilityUseCase.class);
    assertNotNull(DefaultCalculateMissionRealMarginUseCase.class);
    assertNotNull(DefaultEvaluateRouteOptimizationPlanUseCase.class);
    assertNotNull(DefaultEvaluateSecurityPolicyUseCase.class);
    assertNotNull(DefaultEvaluateEnterpriseMissionReadinessUseCase.class);
  }
}
