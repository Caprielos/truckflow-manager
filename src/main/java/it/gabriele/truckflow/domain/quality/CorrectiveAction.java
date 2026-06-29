package it.gabriele.truckflow.domain.quality;

import it.gabriele.truckflow.domain.shared.Notes;
import java.time.LocalDate;
import java.util.Objects;

/** Azione correttiva associata a evento qualità, claim o non conformità. */
public final class CorrectiveAction {

  private static final int MAX_CODE_LENGTH = 50;
  private static final int MAX_TITLE_LENGTH = 150;

  private final String actionCode;
  private final String ownerCode;
  private final String title;
  private final LocalDate dueDate;
  private final boolean completed;
  private final Notes notes;

  private CorrectiveAction(
      String actionCode,
      String ownerCode,
      String title,
      LocalDate dueDate,
      boolean completed,
      Notes notes) {
    this.actionCode = validateCode(actionCode, "Il codice azione correttiva è obbligatorio.");
    this.ownerCode = validateCode(ownerCode, "Il responsabile azione correttiva è obbligatorio.");
    this.title = validateTitle(title);

    if (dueDate == null) {
      throw new IllegalArgumentException("La scadenza azione correttiva è obbligatoria.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note azione correttiva sono obbligatorie.");
    }

    this.dueDate = dueDate;
    this.completed = completed;
    this.notes = notes;
  }

  public static CorrectiveAction open(
      String actionCode, String ownerCode, String title, LocalDate dueDate, Notes notes) {
    return new CorrectiveAction(actionCode, ownerCode, title, dueDate, false, notes);
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }

    String normalizedCode = code.trim().toUpperCase();

    if (normalizedCode.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }

    if (normalizedCode.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }

    if (!normalizedCode.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedCode;
  }

  private static String validateTitle(String title) {
    if (title == null) {
      throw new IllegalArgumentException("Il titolo azione correttiva è obbligatorio.");
    }

    String normalizedTitle = title.trim();

    if (normalizedTitle.isEmpty()) {
      throw new IllegalArgumentException("Il titolo azione correttiva non può essere vuoto.");
    }

    if (normalizedTitle.length() > MAX_TITLE_LENGTH) {
      throw new IllegalArgumentException(
          "Il titolo azione correttiva non può superare " + MAX_TITLE_LENGTH + " caratteri.");
    }

    return normalizedTitle;
  }

  public CorrectiveAction complete() {
    return new CorrectiveAction(actionCode, ownerCode, title, dueDate, true, notes);
  }

  public String getActionCode() {
    return actionCode;
  }

  public String getOwnerCode() {
    return ownerCode;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public boolean isCompleted() {
    return completed;
  }

  public Notes getNotes() {
    return notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CorrectiveAction that)) return false;
    return completed == that.completed
        && actionCode.equals(that.actionCode)
        && ownerCode.equals(that.ownerCode)
        && title.equals(that.title)
        && dueDate.equals(that.dueDate)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionCode, ownerCode, title, dueDate, completed, notes);
  }
}
