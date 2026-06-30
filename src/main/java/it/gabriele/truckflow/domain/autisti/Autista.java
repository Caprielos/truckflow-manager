package it.gabriele.truckflow.domain.autisti;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record Autista(
    UUID id, String nome, String cognome, LocalDate dataNascita, Set<Patente> patenti) {

  public Autista {
    id = id == null ? UUID.randomUUID() : id;
    patenti = patenti == null ? Set.of() : Set.copyOf(patenti);
  }

  public String nomeCompleto() {
    return nome + " " + cognome;
  }

  public boolean haPatente(Patente patente) {
    return patenti.contains(patente);
  }
}
