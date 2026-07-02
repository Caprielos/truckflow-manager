package it.gabriele.truckflow.infrastructure.config.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Root Spring wiring configuration for the current non-web infrastructure runtime.
 *
 * <p>This configuration composes repository and use-case beans explicitly. It does not scan the
 * domain or application layers for Spring annotations and it does not introduce REST, persistence,
 * security, or external-service adapters.
 */
@Configuration
@Import({
  InMemoryRepositorySpringConfiguration.class,
  CargoUseCaseSpringConfiguration.class,
  ComplianceUseCaseSpringConfiguration.class,
  DocumentUseCaseSpringConfiguration.class,
  LocationUseCaseSpringConfiguration.class,
  OperationalUseCaseSpringConfiguration.class,
  ShipmentUseCaseSpringConfiguration.class,
  VehicleUseCaseSpringConfiguration.class
})
public class TruckFlowSpringWiringConfiguration {}
