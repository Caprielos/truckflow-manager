package it.gabriele.truckflow.application.port.in.warehouse;

import it.gabriele.truckflow.domain.warehouse.DockAppointment;
import java.time.LocalDateTime;

public interface CheckInDockAppointmentUseCase {
  DockAppointment handle(Command command);

  record Command(String appointmentCode, LocalDateTime checkInAt) {}
}
