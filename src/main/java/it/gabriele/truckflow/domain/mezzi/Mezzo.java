package it.gabriele.truckflow.domain.mezzi;

import java.util.UUID;

public record Mezzo(UUID id, String targa, String descrizione) {

  public Mezzo {
    id = id == null ? UUID.randomUUID() : id;
  }
}
