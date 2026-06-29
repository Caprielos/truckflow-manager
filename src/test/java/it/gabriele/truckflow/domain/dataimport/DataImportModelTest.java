package it.gabriele.truckflow.domain.dataimport;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataImportModelTest {

  @Test
  void shouldModelExternalCostImportBatch() {
    ImportRecord fuel =
        ImportRecord.of(
            "REC-001",
            ExternalDataSourceType.FUEL_CARD,
            "DKV/123",
            ImportRecordStatus.VALIDATED,
            "MIS-001",
            Money.of("310.50", "EUR"),
            LocalDateTime.of(2026, 6, 28, 18, 30),
            Notes.empty());
    ImportRecord toll =
        ImportRecord.of(
            "REC-002",
            ExternalDataSourceType.FUEL_CARD,
            "DKV/124",
            ImportRecordStatus.VALIDATED,
            "MIS-001",
            Money.of("52.20", "EUR"),
            LocalDateTime.of(2026, 6, 28, 19, 10),
            Notes.empty());

    ImportBatch batch =
        ImportBatch.of(
            "BATCH-001",
            ExternalDataSourceType.FUEL_CARD,
            LocalDateTime.of(2026, 6, 29, 8, 0),
            List.of(fuel, toll),
            Notes.empty());

    assertEquals(2, batch.countValidatedRecords());
    assertFalse(batch.hasRejectedRecords());
    assertTrue(ImportRules.batchCanBePostedToDomain(batch));
    assertEquals(
        Money.of("362.70", "EUR"), batch.calculateAmountTotal(Currency.getInstance("EUR")));
  }
}
