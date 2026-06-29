package it.gabriele.truckflow.domain.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Testa il Value Object Notes. */
class NotesTest {

  @Test
  void shouldCreateNotes() {
    Notes notes = Notes.of("Merce fragile");

    assertEquals("Merce fragile", notes.getText());
  }

  @Test
  void shouldTrimText() {
    Notes notes = Notes.of("   Merce fragile   ");

    assertEquals("Merce fragile", notes.getText());
  }

  @Test
  void shouldCreateEmptyNotes() {
    Notes notes = Notes.empty();

    assertTrue(notes.isEmpty());
    assertFalse(notes.hasText());
  }

  @Test
  void shouldTreatBlankTextAsEmptyNotes() {
    Notes notes = Notes.of("   ");

    assertTrue(notes.isEmpty());
    assertFalse(notes.hasText());
  }

  @Test
  void shouldDetectText() {
    Notes notes = Notes.of("Consegnare solo al responsabile del magazzino");

    assertFalse(notes.isEmpty());
    assertTrue(notes.hasText());
  }

  @Test
  void shouldNotAllowNullText() {
    assertThrows(IllegalArgumentException.class, () -> Notes.of(null));
  }

  @Test
  void shouldNotAllowTooLongText() {
    String tooLongText = "a".repeat(2001);

    assertThrows(IllegalArgumentException.class, () -> Notes.of(tooLongText));
  }

  @Test
  void shouldAllowMaximumLengthText() {
    String maxLengthText = "a".repeat(2000);

    Notes notes = Notes.of(maxLengthText);

    assertEquals(2000, notes.getText().length());
  }

  @Test
  void shouldCheckIfTextContainsValueIgnoringCase() {
    Notes notes = Notes.of("Merce fragile da non sovrapporre");

    assertTrue(notes.contains("FRAGILE"));
    assertTrue(notes.contains("fragile"));
    assertFalse(notes.contains("refrigerata"));
  }

  @Test
  void shouldNotSearchNullText() {
    Notes notes = Notes.of("Merce fragile");

    assertThrows(IllegalArgumentException.class, () -> notes.contains(null));
  }

  @Test
  void shouldConsiderEquivalentNotesEqual() {
    Notes first = Notes.of("  Merce fragile  ");
    Notes second = Notes.of("Merce fragile");

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }
}
