package it.gabriele.truckflow.web.config;

import it.gabriele.truckflow.application.port.out.deadline.DeadlineGateway;
import it.gabriele.truckflow.deadlineservice.application.DeadlineRulePackProvider;
import it.gabriele.truckflow.deadlineservice.application.DeadlineServiceFacade;
import it.gabriele.truckflow.deadlineservice.application.DefaultEvaluateDeadlineBatchUseCase;
import it.gabriele.truckflow.deadlineservice.application.DefaultEvaluateDeadlineUseCase;
import it.gabriele.truckflow.deadlineservice.application.EvaluateDeadlineBatchUseCase;
import it.gabriele.truckflow.deadlineservice.application.EvaluateDeadlineUseCase;
import it.gabriele.truckflow.deadlineservice.evaluation.DeadlineEvaluationEngine;
import it.gabriele.truckflow.deadlineservice.infrastructure.rulepack.ResourceDeadlineRulePackProvider;
import it.gabriele.truckflow.infrastructure.client.deadline.InProcessDeadlineGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Configurazione Spring del futuro compliance-deadline-service integrato nel monolite. */
@Configuration
public class DeadlineServiceConfiguration {

  @Bean
  public DeadlineRulePackProvider deadlineRulePackProvider() {
    return ResourceDeadlineRulePackProvider.defaultResource();
  }

  @Bean
  public DeadlineEvaluationEngine deadlineEvaluationEngine() {
    return new DeadlineEvaluationEngine();
  }

  @Bean
  public EvaluateDeadlineUseCase evaluateDeadlineUseCase(
      DeadlineRulePackProvider deadlineRulePackProvider,
      DeadlineEvaluationEngine deadlineEvaluationEngine) {
    return new DefaultEvaluateDeadlineUseCase(deadlineRulePackProvider, deadlineEvaluationEngine);
  }

  @Bean
  public EvaluateDeadlineBatchUseCase evaluateDeadlineBatchUseCase(
      EvaluateDeadlineUseCase evaluateDeadlineUseCase) {
    return new DefaultEvaluateDeadlineBatchUseCase(evaluateDeadlineUseCase);
  }

  @Bean
  public DeadlineServiceFacade deadlineServiceFacade(
      EvaluateDeadlineUseCase evaluateDeadlineUseCase,
      EvaluateDeadlineBatchUseCase evaluateDeadlineBatchUseCase) {
    return new DeadlineServiceFacade(evaluateDeadlineUseCase, evaluateDeadlineBatchUseCase);
  }

  @Bean
  public DeadlineGateway deadlineGateway(DeadlineServiceFacade deadlineServiceFacade) {
    return new InProcessDeadlineGateway(deadlineServiceFacade);
  }
}
