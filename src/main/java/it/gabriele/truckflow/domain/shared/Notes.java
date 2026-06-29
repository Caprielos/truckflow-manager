package it.gabriele.truckflow.domain.shared;

import java.util.Objects;

/**
 * Rappresenta note testuali del dominio. Le note vengono normalizzate rimuovendo spazi iniziali e
 * finali.
 */
public final class Notes {

  private static final int MAX_LENGTH = 2000;

  private final String text;

  private Notes(String text) {
    if (text == null) {
      throw new IllegalArgumentException("Il testo delle note è obbligatorio.");
    }

    String normalizedText = text.trim();

    if (normalizedText.length() > MAX_LENGTH) {
      throw new IllegalArgumentException(
          "Le note non possono superare " + MAX_LENGTH + " caratteri.");
    }

    this.text = normalizedText;
  }

  /** Crea note partendo da un testo. */
  public static Notes of(String text) {
    return new Notes(text);
  }

  /** Crea note vuote. */
  public static Notes empty() {
    return new Notes("");
  }

  public String getText() {
    return text;
  }

  /** Verifica se le note sono vuote. */
  public boolean isEmpty() {
    return text.isEmpty();
  }

  /** Verifica se le note contengono testo. */
  public boolean hasText() {
    return !isEmpty();
  }

  /** Verifica se le note contengono una parola o frase. */
  public boolean contains(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Il testo da cercare è obbligatorio.");
    }

    return text.toLowerCase().contains(value.toLowerCase());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Notes notes)) return false;
    return text.equals(notes.text);
  }

  @Override
  public int hashCode() {
    return Objects.hash(text);
  }

  @Override
  public String toString() {
    return text;
  }
}
