package it.gabriele.truckflow.domain.roadinspection;

/** Tipo di non conformità rilevata durante controllo. */
public enum InspectionFindingType {
  DOCUMENT_MISSING,
  TECHNICAL_DEFECT,
  ADR_NON_COMPLIANCE,
  ATP_NON_COMPLIANCE,
  WASTE_NON_COMPLIANCE,
  FOOD_SAFETY_NON_COMPLIANCE,
  LIVESTOCK_NON_COMPLIANCE,
  OVERSIZED_PERMIT_ISSUE,
  TACHOGRAPH_VIOLATION,
  OVERLOAD,
  LOAD_SECURING_ISSUE,
  CUSTOMS_DOCUMENT_ISSUE
}
