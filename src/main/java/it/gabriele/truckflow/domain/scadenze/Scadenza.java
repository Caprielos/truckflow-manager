package it.gabriele.truckflow.domain.scadenze;

import java.time.LocalDate;
import java.util.UUID;

public record Scadenza(UUID id, String descrizione, LocalDate dataScadenza) {

  public Scadenza {
    id = id == null ? UUID.randomUUID() : id;
  }

  public boolean isScaduta(LocalDate dataRiferimento) {
    return dataScadenza != null && dataScadenza.isBefore(dataRiferimento);
  }
}
