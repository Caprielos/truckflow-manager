package it.gabriele.truckflow.application.port.in.warehouse;

import it.gabriele.truckflow.domain.warehouse.DockAppointment;

public interface BookDockAppointmentUseCase {
  DockAppointment handle(Command command);

  record Command(DockAppointment appointment) {}
}
