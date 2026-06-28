package it.gabriele.truckflow.domain.tire;

import java.util.Objects;

public final class Tire {

    private final String tireCode;
    private final TireStatus status;
    private final double treadDepthMillimeters;
    private final long installedAtKilometers;
    private final long currentKilometers;

    private Tire(String tireCode, TireStatus status, double treadDepthMillimeters, long installedAtKilometers, long currentKilometers) {
        if (tireCode == null || tireCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Il codice gomma è obbligatorio.");
        }
        if (status == null) {
            throw new IllegalArgumentException("Lo stato gomma è obbligatorio.");
        }
        if (treadDepthMillimeters < 0 || Double.isNaN(treadDepthMillimeters) || Double.isInfinite(treadDepthMillimeters)) {
            throw new IllegalArgumentException("Il battistrada deve essere valido e non negativo.");
        }
        if (installedAtKilometers < 0 || currentKilometers < 0) {
            throw new IllegalArgumentException("I chilometri gomma non possono essere negativi.");
        }
        if (currentKilometers < installedAtKilometers) {
            throw new IllegalArgumentException("I km attuali non possono essere inferiori ai km di installazione.");
        }
        this.tireCode = tireCode.trim().toUpperCase();
        this.status = status;
        this.treadDepthMillimeters = treadDepthMillimeters;
        this.installedAtKilometers = installedAtKilometers;
        this.currentKilometers = currentKilometers;
    }

    public static Tire of(String tireCode, TireStatus status, double treadDepthMillimeters, long installedAtKilometers, long currentKilometers) {
        return new Tire(tireCode, status, treadDepthMillimeters, installedAtKilometers, currentKilometers);
    }

    public String getTireCode() {
        return tireCode;
    }

    public TireStatus getStatus() {
        return status;
    }

    public double getTreadDepthMillimeters() {
        return treadDepthMillimeters;
    }

    public long getInstalledAtKilometers() {
        return installedAtKilometers;
    }

    public long getCurrentKilometers() {
        return currentKilometers;
    }

    public long calculateKilometersInUse() {
        return currentKilometers - installedAtKilometers;
    }

    public boolean isBelowLegalMinimum() {
        return treadDepthMillimeters < 1.6;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tire tire)) return false;
        return Double.compare(treadDepthMillimeters, tire.treadDepthMillimeters) == 0
                && installedAtKilometers == tire.installedAtKilometers
                && currentKilometers == tire.currentKilometers
                && tireCode.equals(tire.tireCode)
                && status == tire.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tireCode, status, treadDepthMillimeters, installedAtKilometers, currentKilometers);
    }
}
