package it.gabriele.truckflow.domain.dataimport;

/**
 * Regole sui lotti di importazione esterna.
 */
public final class ImportRules {

    private ImportRules() {
    }

    public static boolean batchCanBePostedToDomain(ImportBatch batch) {
        validateBatch(batch);
        return !batch.getRecords().isEmpty()
                && batch.getRecords().stream().allMatch(ImportRecord::canBePostedToDomain);
    }

    public static boolean batchRequiresManualReview(ImportBatch batch) {
        validateBatch(batch);
        return batch.hasRejectedRecords()
                || batch.getRecords().stream().anyMatch(record -> record.getStatus() == ImportRecordStatus.DUPLICATE);
    }

    public static boolean sourceUsuallyCreatesEconomicEntries(ExternalDataSourceType sourceType) {
        if (sourceType == null) {
            throw new IllegalArgumentException("Il tipo fonte import è obbligatorio.");
        }
        return sourceType.usuallyContainsEconomicData();
    }

    private static void validateBatch(ImportBatch batch) {
        if (batch == null) {
            throw new IllegalArgumentException("Il lotto import è obbligatorio.");
        }
    }
}
