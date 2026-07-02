package it.gabriele.truckflow.infrastructure.config.spring;

import it.gabriele.truckflow.application.port.in.locations.FindLocationUseCase;
import it.gabriele.truckflow.application.port.in.locations.RegisterLocationUseCase;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.application.usecase.locations.FindLocationService;
import it.gabriele.truckflow.application.usecase.locations.RegisterLocationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Spring bean wiring for location application use cases. */
@Configuration
public class LocationUseCaseSpringConfiguration {

  @Bean
  public RegisterLocationUseCase registerLocationUseCase(LocationRepository locationRepository) {
    return new RegisterLocationService(locationRepository);
  }

  @Bean
  public FindLocationUseCase findLocationUseCase(LocationRepository locationRepository) {
    return new FindLocationService(locationRepository);
  }
}
