package it.gabriele.truckflow.domain.manutenzione;

import java.time.LocalDate;
import java.util.UUID;

public record ManutenzioneItem(
    UUID id, String descrizione, LocalDate dataPrevista, boolean completata) {

  public ManutenzioneItem {
    id = id == null ? UUID.randomUUID() : id;
  }
}
