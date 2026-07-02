package it.gabriele.truckflow.infrastructure.mapping;

/**
 * Technical classification used to describe how a domain concept should be represented by a future
 * persistence model.
 */
public enum PersistenceMappingKind {
  /** Aggregate root persisted as a stable technical record. */
  AGGREGATE,

  /** Domain value object flattened into one or more primitive persistence fields. */
  VALUE_OBJECT,

  /** Domain enum persisted as a stable textual value. */
  ENUMERATION,

  /** Domain state persisted as a stable state column. */
  STATE,

  /**
   * Domain collection persisted as child rows, embedded records, or a dedicated technical model.
   */
  COLLECTION,

  /** Reference to another aggregate persisted only through stable identifiers. */
  REFERENCE
}
