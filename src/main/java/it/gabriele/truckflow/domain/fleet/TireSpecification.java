package it.gabriele.truckflow.domain.fleet;

import java.util.Objects;

/**
 * Rappresenta le informazioni principali sugli pneumatici montati su un mezzo.
 */
public final class TireSpecification {

    private static final int MAX_BRAND_LENGTH = 80;
    private static final int MAX_MODEL_LENGTH = 120;
    private static final int MAX_SIZE_LENGTH = 30;
    private static final int MAX_SPEED_RATING_LENGTH = 3;

    private final String brand;
    private final String model;
    private final String size;
    private final int loadIndex;
    private final String speedRating;

    private TireSpecification(
            String brand,
            String model,
            String size,
            int loadIndex,
            String speedRating
    ) {
        this.brand = validateText(brand, "La marca dello pneumatico è obbligatoria.", MAX_BRAND_LENGTH);
        this.model = validateText(model, "Il modello dello pneumatico è obbligatorio.", MAX_MODEL_LENGTH);
        this.size = validateText(size, "La misura dello pneumatico è obbligatoria.", MAX_SIZE_LENGTH);
        this.loadIndex = validateLoadIndex(loadIndex);
        this.speedRating = validateSpeedRating(speedRating);
    }

    public static TireSpecification of(
            String brand,
            String model,
            String size,
            int loadIndex,
            String speedRating
    ) {
        return new TireSpecification(brand, model, size, loadIndex, speedRating);
    }

    private static String validateText(String value, String nullMessage, int maxLength) {
        if (value == null) {
            throw new IllegalArgumentException(nullMessage);
        }

        String normalizedValue = value.trim();

        if (normalizedValue.isEmpty()) {
            throw new IllegalArgumentException(nullMessage);
        }

        if (normalizedValue.length() > maxLength) {
            throw new IllegalArgumentException("Il testo non può superare " + maxLength + " caratteri.");
        }

        return normalizedValue;
    }

    private static int validateLoadIndex(int loadIndex) {
        if (loadIndex <= 0) {
            throw new IllegalArgumentException("L'indice di carico dello pneumatico deve essere maggiore di zero.");
        }

        return loadIndex;
    }

    private static String validateSpeedRating(String speedRating) {
        if (speedRating == null) {
            throw new IllegalArgumentException("Il codice velocità dello pneumatico è obbligatorio.");
        }

        String normalizedSpeedRating = speedRating.trim().toUpperCase();

        if (normalizedSpeedRating.isEmpty()) {
            throw new IllegalArgumentException("Il codice velocità dello pneumatico non può essere vuoto.");
        }

        if (normalizedSpeedRating.length() > MAX_SPEED_RATING_LENGTH) {
            throw new IllegalArgumentException("Il codice velocità dello pneumatico non può superare " + MAX_SPEED_RATING_LENGTH + " caratteri.");
        }

        if (!normalizedSpeedRating.matches("[A-Z0-9]+")) {
            throw new IllegalArgumentException("Il codice velocità dello pneumatico può contenere solo lettere e numeri.");
        }

        return normalizedSpeedRating;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getSize() {
        return size;
    }

    public int getLoadIndex() {
        return loadIndex;
    }

    public String getSpeedRating() {
        return speedRating;
    }

    public String formatSingleLine() {
        return brand + " " + model + " - " + size + " - " + loadIndex + speedRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TireSpecification that)) return false;
        return loadIndex == that.loadIndex
                && brand.equals(that.brand)
                && model.equals(that.model)
                && size.equals(that.size)
                && speedRating.equals(that.speedRating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, size, loadIndex, speedRating);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
