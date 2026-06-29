package it.gabriele.truckflow.application.usecase.warehouse;

import it.gabriele.truckflow.application.port.in.warehouse.BookDockAppointmentUseCase;
import it.gabriele.truckflow.application.port.out.warehouse.DockAppointmentRepository;
import it.gabriele.truckflow.domain.warehouse.DockAppointment;
import java.util.Objects;

/** Implementazione default di BookDockAppointmentUseCase. */
public final class DefaultBookDockAppointmentUseCase implements BookDockAppointmentUseCase {

  private final DockAppointmentRepository repository;

  public DefaultBookDockAppointmentUseCase(DockAppointmentRepository repository) {
    this.repository = Objects.requireNonNull(repository, "Il repository è obbligatorio.");
  }

  @Override
  public DockAppointment handle(Command command) {
    Objects.requireNonNull(command, "Il comando è obbligatorio.");
    DockAppointment aggregate =
        Objects.requireNonNull(command.appointment(), "L appuntamento baia è obbligatorio.");
    repository.save(aggregate);
    return aggregate;
  }
}
