package it.gabriele.truckflow.domain.cargo;

import java.util.Objects;
import java.util.Set;

/**
 * Rappresenta il profilo ADR di una merce pericolosa.
 * Esempi: UN 1203 benzina, UN 1202 gasolio, UN 1965 gas idrocarburi liquefatto.
 */
public final class DangerousGoodsProfile {

    private static final int MAX_PROPER_SHIPPING_NAME_LENGTH = 250;
    private static final int MAX_CLASSIFICATION_CODE_LENGTH = 20;
    private static final int MAX_TUNNEL_RESTRICTION_CODE_LENGTH = 20;

    private final String unNumber;
    private final String properShippingName;
    private final AdrClass adrClass;
    private final String classificationCode;
    private final PackingGroup packingGroup;
    private final Set<HazardLabel> hazardLabels;
    private final String tunnelRestrictionCode;
    private final int transportCategory;
    private final boolean requiresTankTransport;

    private DangerousGoodsProfile(
            String unNumber,
            String properShippingName,
            AdrClass adrClass,
            String classificationCode,
            PackingGroup packingGroup,
            Set<HazardLabel> hazardLabels,
            String tunnelRestrictionCode,
            int transportCategory,
            boolean requiresTankTransport
    ) {
        this.unNumber = validateUnNumber(unNumber);
        this.properShippingName = validateProperShippingName(properShippingName);

        if (adrClass == null) {
            throw new IllegalArgumentException("La classe ADR è obbligatoria.");
        }

        this.classificationCode = validateClassificationCode(classificationCode);
        this.packingGroup = packingGroup;
        this.hazardLabels = validateHazardLabels(hazardLabels);
        this.tunnelRestrictionCode = validateTunnelRestrictionCode(tunnelRestrictionCode);
        this.transportCategory = validateTransportCategory(transportCategory);
        this.requiresTankTransport = requiresTankTransport;
        this.adrClass = adrClass;
    }

    public static DangerousGoodsProfile of(
            String unNumber,
            String properShippingName,
            AdrClass adrClass,
            String classificationCode,
            PackingGroup packingGroup,
            Set<HazardLabel> hazardLabels,
            String tunnelRestrictionCode,
            int transportCategory,
            boolean requiresTankTransport
    ) {
        return new DangerousGoodsProfile(
                unNumber,
                properShippingName,
                adrClass,
                classificationCode,
                packingGroup,
                hazardLabels,
                tunnelRestrictionCode,
                transportCategory,
                requiresTankTransport
        );
    }

    private static String validateUnNumber(String unNumber) {
        if (unNumber == null) {
            throw new IllegalArgumentException("Il numero ONU è obbligatorio.");
        }

        String normalizedUnNumber = unNumber.trim().toUpperCase().replaceAll("\\s+", "");

        if (normalizedUnNumber.matches("\\d{4}")) {
            return "UN " + normalizedUnNumber;
        }

        if (normalizedUnNumber.matches("UN\\d{4}")) {
            return "UN " + normalizedUnNumber.substring(2);
        }

        throw new IllegalArgumentException("Il numero ONU deve essere nel formato UN 1203 oppure 1203.");
    }

    private static String validateProperShippingName(String properShippingName) {
        if (properShippingName == null) {
            throw new IllegalArgumentException("Il proper shipping name è obbligatorio.");
        }

        String normalizedName = properShippingName.trim();

        if (normalizedName.isEmpty()) {
            throw new IllegalArgumentException("Il proper shipping name non può essere vuoto.");
        }

        if (normalizedName.length() > MAX_PROPER_SHIPPING_NAME_LENGTH) {
            throw new IllegalArgumentException("Il proper shipping name non può superare "
                    + MAX_PROPER_SHIPPING_NAME_LENGTH + " caratteri.");
        }

        return normalizedName;
    }

    private static String validateClassificationCode(String classificationCode) {
        if (classificationCode == null) {
            return "";
        }

        String normalizedClassificationCode = classificationCode.trim().toUpperCase();

        if (normalizedClassificationCode.isEmpty()) {
            return "";
        }

        if (normalizedClassificationCode.length() > MAX_CLASSIFICATION_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice di classificazione non può superare "
                    + MAX_CLASSIFICATION_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedClassificationCode.matches("[A-Z0-9.+\\-]+")) {
            throw new IllegalArgumentException("Il codice di classificazione contiene caratteri non validi.");
        }

        return normalizedClassificationCode;
    }

    private static Set<HazardLabel> validateHazardLabels(Set<HazardLabel> hazardLabels) {
        if (hazardLabels == null) {
            throw new IllegalArgumentException("Le etichette di pericolo sono obbligatorie.");
        }

        if (hazardLabels.isEmpty()) {
            throw new IllegalArgumentException("La merce pericolosa deve avere almeno una etichetta di pericolo.");
        }

        if (hazardLabels.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Le etichette di pericolo non possono contenere valori nulli.");
        }

        return Set.copyOf(hazardLabels);
    }

    private static String validateTunnelRestrictionCode(String tunnelRestrictionCode) {
        if (tunnelRestrictionCode == null) {
            return "";
        }

        String normalizedTunnelCode = tunnelRestrictionCode.trim().toUpperCase();

        if (normalizedTunnelCode.isEmpty()) {
            return "";
        }

        if (normalizedTunnelCode.length() > MAX_TUNNEL_RESTRICTION_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice tunnel non può superare "
                    + MAX_TUNNEL_RESTRICTION_CODE_LENGTH + " caratteri.");
        }

        if (!normalizedTunnelCode.matches("[A-Z0-9()/+\\-]+")) {
            throw new IllegalArgumentException("Il codice tunnel contiene caratteri non validi.");
        }

        return normalizedTunnelCode;
    }

    private static int validateTransportCategory(int transportCategory) {
        if (transportCategory < 0 || transportCategory > 4) {
            throw new IllegalArgumentException("La categoria di trasporto deve essere compresa tra 0 e 4.");
        }

        return transportCategory;
    }

    public String getUnNumber() {
        return unNumber;
    }

    public String getProperShippingName() {
        return properShippingName;
    }

    public AdrClass getAdrClass() {
        return adrClass;
    }

    public String getClassificationCode() {
        return classificationCode;
    }

    public PackingGroup getPackingGroup() {
        return packingGroup;
    }

    public Set<HazardLabel> getHazardLabels() {
        return hazardLabels;
    }

    public String getTunnelRestrictionCode() {
        return tunnelRestrictionCode;
    }

    public int getTransportCategory() {
        return transportCategory;
    }

    public boolean requiresTankTransport() {
        return requiresTankTransport;
    }

    public boolean hasPackingGroup() {
        return packingGroup != null;
    }

    public boolean isGas() {
        return adrClass.isGas();
    }

    public boolean isFlammableLiquid() {
        return adrClass.isFlammableLiquid();
    }

    public boolean isExplosive() {
        return adrClass.isExplosives();
    }

    public boolean isRadioactive() {
        return adrClass.isRadioactive();
    }

    public boolean hasHazardLabel(HazardLabel hazardLabel) {
        if (hazardLabel == null) {
            throw new IllegalArgumentException("L'etichetta di pericolo da verificare è obbligatoria.");
        }

        return hazardLabels.contains(hazardLabel);
    }

    public boolean isUnNumber(String unNumber) {
        return this.unNumber.equals(validateUnNumber(unNumber));
    }

    public boolean requiresAdrBasicCertificate() {
        return true;
    }

    public boolean requiresAdrTankCertificate() {
        return requiresTankTransport;
    }

    public String formatSingleLine() {
        return unNumber + " - " + properShippingName + " - class " + adrClass.getCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DangerousGoodsProfile that)) return false;
        return transportCategory == that.transportCategory
                && requiresTankTransport == that.requiresTankTransport
                && unNumber.equals(that.unNumber)
                && properShippingName.equals(that.properShippingName)
                && adrClass == that.adrClass
                && classificationCode.equals(that.classificationCode)
                && packingGroup == that.packingGroup
                && hazardLabels.equals(that.hazardLabels)
                && tunnelRestrictionCode.equals(that.tunnelRestrictionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                unNumber,
                properShippingName,
                adrClass,
                classificationCode,
                packingGroup,
                hazardLabels,
                tunnelRestrictionCode,
                transportCategory,
                requiresTankTransport
        );
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
