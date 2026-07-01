package it.gabriele.truckflow.application.usecase.operational;

import it.gabriele.truckflow.domain.operational.mechanic.Mechanic;
import java.util.Set;

final class MechanicMutationSupport {

  private MechanicMutationSupport() {}

  static Mechanic copyOf(Mechanic mechanic) {
    return new Mechanic(
        mechanic.id(),
        mechanic.code(),
        mechanic.userId(),
        mechanic.profile(),
        Set.copyOf(mechanic.qualifications()),
        mechanic.status(),
        mechanic.metadata(),
        mechanic.notes());
  }
}
