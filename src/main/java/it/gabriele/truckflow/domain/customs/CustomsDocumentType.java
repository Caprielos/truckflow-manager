package it.gabriele.truckflow.domain.customs;

/** Documento richiesto per dogana, export/import o transiti internazionali. */
public enum CustomsDocumentType {
  COMMERCIAL_INVOICE,
  PACKING_LIST,
  CMR,
  EXPORT_DECLARATION,
  IMPORT_DECLARATION,
  TRANSIT_DOCUMENT,
  CERTIFICATE_OF_ORIGIN,
  SANITARY_CERTIFICATE,
  OTHER
}
