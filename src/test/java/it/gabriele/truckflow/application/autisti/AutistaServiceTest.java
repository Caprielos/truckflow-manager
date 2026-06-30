package it.gabriele.truckflow.application.autisti;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.domain.autisti.Patente;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AutistaServiceTest {

  @Test
  void creaAutistaESalvaInMemoria() {
    AutistaService service = new AutistaService();

    var autista =
        service.crea("Mario", "Rossi", LocalDate.of(1990, 1, 15), Set.of(Patente.B, Patente.C));

    assertEquals("Mario", autista.nome());
    assertEquals("Rossi", autista.cognome());
    assertTrue(autista.haPatente(Patente.C));
    assertTrue(service.trovaPerId(autista.id()).isPresent());
    assertEquals(1, service.trovaTutti().size());
  }
}
