package it.gabriele.truckflow.application.port.in;

import it.gabriele.truckflow.domain.tachograph.DrivingTimeViolation;
import java.util.List;

public interface DetectTachographViolationsUseCase {
  Result handle(Command command);

  record Command(String driverCode) {}

  record Result(
      long drivingMinutes,
      boolean dailyLimitExceeded,
      List<DrivingTimeViolation> activeViolations) {}
}
