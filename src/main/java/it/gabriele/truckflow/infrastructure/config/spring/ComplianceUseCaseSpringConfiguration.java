package it.gabriele.truckflow.infrastructure.config.spring;

import it.gabriele.truckflow.application.port.in.compliance.ActivateComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.ArchiveComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.DiscontinueComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.FindComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.RegisterComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.compliance.SuspendComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.usecase.compliance.ActivateComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.ArchiveComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.DiscontinueComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.FindComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.RegisterComplianceRequirementService;
import it.gabriele.truckflow.application.usecase.compliance.SuspendComplianceRequirementService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Spring bean wiring for compliance application use cases. */
@Configuration
public class ComplianceUseCaseSpringConfiguration {

  @Bean
  public RegisterComplianceRequirementUseCase registerComplianceRequirementUseCase(
      ComplianceRequirementRepository complianceRequirementRepository) {
    return new RegisterComplianceRequirementService(complianceRequirementRepository);
  }

  @Bean
  public FindComplianceRequirementUseCase findComplianceRequirementUseCase(
      ComplianceRequirementRepository complianceRequirementRepository) {
    return new FindComplianceRequirementService(complianceRequirementRepository);
  }

  @Bean
  public ActivateComplianceRequirementUseCase activateComplianceRequirementUseCase(
      ComplianceRequirementRepository complianceRequirementRepository) {
    return new ActivateComplianceRequirementService(complianceRequirementRepository);
  }

  @Bean
  public SuspendComplianceRequirementUseCase suspendComplianceRequirementUseCase(
      ComplianceRequirementRepository complianceRequirementRepository) {
    return new SuspendComplianceRequirementService(complianceRequirementRepository);
  }

  @Bean
  public ArchiveComplianceRequirementUseCase archiveComplianceRequirementUseCase(
      ComplianceRequirementRepository complianceRequirementRepository) {
    return new ArchiveComplianceRequirementService(complianceRequirementRepository);
  }

  @Bean
  public DiscontinueComplianceRequirementUseCase discontinueComplianceRequirementUseCase(
      ComplianceRequirementRepository complianceRequirementRepository) {
    return new DiscontinueComplianceRequirementService(complianceRequirementRepository);
  }
}
