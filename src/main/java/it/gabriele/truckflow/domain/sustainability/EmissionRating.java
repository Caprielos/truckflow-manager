package it.gabriele.truckflow.domain.sustainability;

/** Classificazione interna della stima emissioni. */
public enum EmissionRating {
  LOW(1),
  MEDIUM(2),
  HIGH(3),
  VERY_HIGH(4);

  private final int level;

  EmissionRating(int level) {
    this.level = level;
  }

  public int getLevel() {
    return level;
  }

  public boolean isWorseThan(EmissionRating other) {
    if (other == null) {
      throw new IllegalArgumentException("Il rating da confrontare è obbligatorio.");
    }

    return level > other.level;
  }
}
