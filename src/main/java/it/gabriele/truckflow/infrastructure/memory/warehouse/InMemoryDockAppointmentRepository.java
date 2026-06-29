package it.gabriele.truckflow.infrastructure.memory.warehouse;

import it.gabriele.truckflow.application.port.out.DockAppointmentRepository;
import it.gabriele.truckflow.domain.warehouse.DockAppointment;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DockAppointment. */
public final class InMemoryDockAppointmentRepository extends InMemoryRepository<DockAppointment>
    implements DockAppointmentRepository {

  public InMemoryDockAppointmentRepository() {
    super(appointment -> appointment.appointmentCode());
  }
}
