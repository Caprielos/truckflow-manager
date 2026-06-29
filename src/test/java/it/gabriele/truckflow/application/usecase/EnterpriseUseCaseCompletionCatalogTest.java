package it.gabriele.truckflow.application.usecase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.gabriele.truckflow.application.port.in.ApplyRoadInspectionOutcomeUseCase;
import it.gabriele.truckflow.application.port.in.CalculateMissionRealMarginUseCase;
import it.gabriele.truckflow.application.port.in.CollectDigitalProofOfDeliveryUseCase;
import it.gabriele.truckflow.application.port.in.DetectTachographViolationsUseCase;
import it.gabriele.truckflow.application.port.in.EvaluateDriverAssignmentUseCase;
import it.gabriele.truckflow.application.port.in.EvaluateEnterpriseMissionReadinessUseCase;
import it.gabriele.truckflow.application.port.in.EvaluateRegulatoryRequirementUseCase;
import it.gabriele.truckflow.application.port.in.EvaluateRouteOptimizationPlanUseCase;
import it.gabriele.truckflow.application.port.in.EvaluateSecurityPolicyUseCase;
import it.gabriele.truckflow.application.port.in.EvaluateSubcontractorEligibilityUseCase;
import it.gabriele.truckflow.application.port.in.SelectCountryRegulationUseCase;
import it.gabriele.truckflow.application.port.in.ValidateAdrComplianceUseCase;
import it.gabriele.truckflow.application.port.in.ValidateAtpComplianceUseCase;
import it.gabriele.truckflow.application.port.in.ValidateDigitalProofOfDeliveryUseCase;
import it.gabriele.truckflow.application.port.in.ValidateFoodSafetyComplianceUseCase;
import it.gabriele.truckflow.application.port.in.ValidateLivestockComplianceUseCase;
import it.gabriele.truckflow.application.port.in.ValidateOversizedComplianceUseCase;
import it.gabriele.truckflow.application.port.in.ValidatePhysicalTransportComplianceUseCase;
import it.gabriele.truckflow.application.port.in.ValidateWasteComplianceUseCase;
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
