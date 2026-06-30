package it.gabriele.truckflow.application.autisti;

import it.gabriele.truckflow.domain.autisti.Autista;
import it.gabriele.truckflow.domain.autisti.Patente;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class AutistaService {

  private final Map<UUID, Autista> autisti = new ConcurrentHashMap<>();

  public Autista crea(String nome, String cognome, LocalDate dataNascita, Set<Patente> patenti) {
    Autista autista = new Autista(UUID.randomUUID(), nome, cognome, dataNascita, patenti);
    autisti.put(autista.id(), autista);
    return autista;
  }

  public List<Autista> trovaTutti() {
    return autisti.values().stream()
        .sorted(Comparator.comparing(Autista::cognome).thenComparing(Autista::nome))
        .toList();
  }

  public Optional<Autista> trovaPerId(UUID id) {
    return Optional.ofNullable(autisti.get(id));
  }

  public void elimina(UUID id) {
    autisti.remove(id);
  }
}
