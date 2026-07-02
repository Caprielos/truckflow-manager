package it.gabriele.truckflow.infrastructure.config.spring;

import it.gabriele.truckflow.application.port.in.cargo.FindCargoUnitUseCase;
import it.gabriele.truckflow.application.port.in.cargo.RegisterCargoUnitUseCase;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.application.usecase.cargo.FindCargoUnitService;
import it.gabriele.truckflow.application.usecase.cargo.RegisterCargoUnitService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Spring bean wiring for cargo application use cases. */
@Configuration
public class CargoUseCaseSpringConfiguration {

  @Bean
  public RegisterCargoUnitUseCase registerCargoUnitUseCase(
      CargoUnitRepository cargoUnitRepository) {
    return new RegisterCargoUnitService(cargoUnitRepository);
  }

  @Bean
  public FindCargoUnitUseCase findCargoUnitUseCase(CargoUnitRepository cargoUnitRepository) {
    return new FindCargoUnitService(cargoUnitRepository);
  }
}
