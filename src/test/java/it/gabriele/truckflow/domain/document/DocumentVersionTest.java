package it.gabriele.truckflow.domain.document;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.Instant;
import org.junit.jupiter.api.Test;

/** Testa versioning documentale enterprise. */
class DocumentVersionTest {

  @Test
  void shouldCreateCurrentDocumentVersion() {
    DocumentVersion version = currentVersion();

    assertEquals("CMR-001", version.getDocumentNumber());
    assertEquals(1, version.getVersionNumber());
    assertTrue(version.isCurrent());
    assertEquals("dispatcher@example.com", version.getCreatedBy());
  }

  @Test
  void shouldSupersedeCurrentVersion() {
    DocumentVersion superseded = currentVersion().supersede();

    assertEquals(DocumentVersionStatus.SUPERSEDED, superseded.getStatus());
    assertFalse(superseded.isCurrent());
  }

  @Test
  void shouldRejectInvalidVersionNumber() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            DocumentVersion.current(
                "CMR-001",
                0,
                Instant.parse("2026-06-01T08:00:00Z"),
                "dispatcher@example.com",
                "abc123",
                Notes.empty()));
  }

  private static DocumentVersion currentVersion() {
    return DocumentVersion.current(
        "cmr-001",
        1,
        Instant.parse("2026-06-01T08:00:00Z"),
        "dispatcher@example.com",
        "abc123",
        Notes.empty());
  }
}
