package it.gabriele.truckflow.domain.location;

import java.util.Objects;

/**
 * Rappresenta coordinate geografiche.
 * Latitudine e longitudine sono espresse in gradi decimali.
 */
public final class GeoCoordinates {

    private final double latitude;
    private final double longitude;

    private GeoCoordinates(double latitude, double longitude) {
        validateLatitude(latitude);
        validateLongitude(longitude);

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static GeoCoordinates of(double latitude, double longitude) {
        return new GeoCoordinates(latitude, longitude);
    }

    private static void validateLatitude(double latitude) {
        if (Double.isNaN(latitude) || Double.isInfinite(latitude)) {
            throw new IllegalArgumentException("La latitudine deve essere un numero valido.");
        }

        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("La latitudine deve essere compresa tra -90 e 90.");
        }
    }

    private static void validateLongitude(double longitude) {
        if (Double.isNaN(longitude) || Double.isInfinite(longitude)) {
            throw new IllegalArgumentException("La longitudine deve essere un numero valido.");
        }

        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("La longitudine deve essere compresa tra -180 e 180.");
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isNorthernHemisphere() {
        return latitude > 0;
    }

    public boolean isSouthernHemisphere() {
        return latitude < 0;
    }

    public boolean isEasternHemisphere() {
        return longitude > 0;
    }

    public boolean isWesternHemisphere() {
        return longitude < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoCoordinates that)) return false;
        return Double.compare(latitude, that.latitude) == 0
                && Double.compare(longitude, that.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return latitude + ", " + longitude;
    }
}
