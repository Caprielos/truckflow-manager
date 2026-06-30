package it.gabriele.truckflow.domain.documenti;

import java.time.LocalDate;
import java.util.UUID;

public record Documento(UUID id, String nome, LocalDate dataScadenza) {

  public Documento {
    id = id == null ? UUID.randomUUID() : id;
  }

  public boolean isScaduto(LocalDate dataRiferimento) {
    return dataScadenza != null && dataScadenza.isBefore(dataRiferimento);
  }
}
