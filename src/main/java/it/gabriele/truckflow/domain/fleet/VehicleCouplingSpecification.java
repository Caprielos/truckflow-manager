package it.gabriele.truckflow.domain.fleet;

import java.util.Objects;

/**
 * Dati di aggancio: ralla/kingpin per semirimorchi, timone/occhione per rimorchi.
 */
public final class VehicleCouplingSpecification {

    private final CouplingType couplingType;
    private final Double fifthWheelHeightMeters;
    private final KingpinDiameter kingpinDiameter;
    private final Double drawbarLengthMeters;
    private final String drawbarEyeType;

    private VehicleCouplingSpecification(
            CouplingType couplingType,
            Double fifthWheelHeightMeters,
            KingpinDiameter kingpinDiameter,
            Double drawbarLengthMeters,
            String drawbarEyeType
    ) {
        if (couplingType == null) {
            throw new IllegalArgumentException("Il tipo aggancio è obbligatorio.");
        }
        if (fifthWheelHeightMeters != null && fifthWheelHeightMeters <= 0) {
            throw new IllegalArgumentException("L'altezza ralla deve essere positiva.");
        }
        if (drawbarLengthMeters != null && drawbarLengthMeters <= 0) {
            throw new IllegalArgumentException("La lunghezza timone deve essere positiva.");
        }
        this.couplingType = couplingType;
        this.fifthWheelHeightMeters = fifthWheelHeightMeters;
        this.kingpinDiameter = kingpinDiameter;
        this.drawbarLengthMeters = drawbarLengthMeters;
        this.drawbarEyeType = drawbarEyeType == null ? null : drawbarEyeType.trim();
    }

    public static VehicleCouplingSpecification none() {
        return new VehicleCouplingSpecification(CouplingType.NONE, null, null, null, null);
    }

    public static VehicleCouplingSpecification fifthWheel(Double heightMeters, KingpinDiameter kingpinDiameter) {
        return new VehicleCouplingSpecification(CouplingType.FIFTH_WHEEL, heightMeters, kingpinDiameter, null, null);
    }

    public static VehicleCouplingSpecification drawbar(Double drawbarLengthMeters, String drawbarEyeType) {
        return new VehicleCouplingSpecification(CouplingType.DRAWBAR_EYE, null, null, drawbarLengthMeters, drawbarEyeType);
    }

    public CouplingType getCouplingType() {
        return couplingType;
    }

    public Double getFifthWheelHeightMeters() {
        return fifthWheelHeightMeters;
    }

    public KingpinDiameter getKingpinDiameter() {
        return kingpinDiameter;
    }

    public Double getDrawbarLengthMeters() {
        return drawbarLengthMeters;
    }

    public String getDrawbarEyeType() {
        return drawbarEyeType;
    }

    public boolean hasFifthWheelData() {
        return couplingType == CouplingType.FIFTH_WHEEL;
    }

    public boolean hasDrawbarData() {
        return couplingType == CouplingType.DRAWBAR_EYE
                || couplingType == CouplingType.CENTER_AXLE_DRAWBAR;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VehicleCouplingSpecification that)) return false;
        return couplingType == that.couplingType
                && Objects.equals(fifthWheelHeightMeters, that.fifthWheelHeightMeters)
                && kingpinDiameter == that.kingpinDiameter
                && Objects.equals(drawbarLengthMeters, that.drawbarLengthMeters)
                && Objects.equals(drawbarEyeType, that.drawbarEyeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couplingType, fifthWheelHeightMeters, kingpinDiameter, drawbarLengthMeters, drawbarEyeType);
    }
}
