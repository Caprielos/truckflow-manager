package it.gabriele.truckflow.application.usecase.warehouse;

import it.gabriele.truckflow.application.port.in.CheckInDockAppointmentUseCase;
import it.gabriele.truckflow.application.port.out.DockAppointmentRepository;
import it.gabriele.truckflow.domain.warehouse.DockAppointment;
import it.gabriele.truckflow.domain.warehouse.DockAppointmentStatus;

/** Implementazione default di CheckInDockAppointmentUseCase. */
public final class DefaultCheckInDockAppointmentUseCase implements CheckInDockAppointmentUseCase {

  private final DockAppointmentRepository appointmentRepository;

  public DefaultCheckInDockAppointmentUseCase(DockAppointmentRepository appointmentRepository) {
    this.appointmentRepository = appointmentRepository;
  }

  @Override
  public DockAppointment handle(Command command) {
    DockAppointment current =
        appointmentRepository.getRequired(command.appointmentCode(), "Appuntamento baia");
    DockAppointment checkedIn =
        new DockAppointment(
            current.appointmentCode(),
            current.dockCode(),
            current.missionCode(),
            current.plannedStart(),
            current.plannedEnd(),
            command.checkInAt(),
            DockAppointmentStatus.CHECKED_IN);
    appointmentRepository.save(checkedIn);
    return checkedIn;
  }
}
