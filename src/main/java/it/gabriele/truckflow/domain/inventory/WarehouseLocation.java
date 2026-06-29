package it.gabriele.truckflow.domain.inventory;

import java.util.Objects;

/**
 * Posizione fisica in magazzino: deposito, zona, scaffale e contenitore.
 */
public final class WarehouseLocation {

    private static final int MAX_CODE_LENGTH = 50;

    private final String facilityCode;
    private final String zoneCode;
    private final String shelfCode;
    private final String binCode;

    private WarehouseLocation(String facilityCode, String zoneCode, String shelfCode, String binCode) {
        this.facilityCode = validateCode(facilityCode, "Il codice struttura magazzino è obbligatorio.");
        this.zoneCode = validateCode(zoneCode, "Il codice zona magazzino è obbligatorio.");
        this.shelfCode = validateCode(shelfCode, "Il codice scaffale magazzino è obbligatorio.");
        this.binCode = validateCode(binCode, "Il codice contenitore magazzino è obbligatorio.");
    }

    public static WarehouseLocation of(String facilityCode, String zoneCode, String shelfCode, String binCode) {
        return new WarehouseLocation(facilityCode, zoneCode, shelfCode, binCode);
    }

    private static String validateCode(String code, String message) {
        if (code == null) {
            throw new IllegalArgumentException(message);
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        if (normalized.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice posizione non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice posizione può contenere solo lettere, numeri, trattini e underscore.");
        }
        return normalized;
    }

    public String getFacilityCode() { return facilityCode; }
    public String getZoneCode() { return zoneCode; }
    public String getShelfCode() { return shelfCode; }
    public String getBinCode() { return binCode; }

    public String getFullCode() {
        return facilityCode + "/" + zoneCode + "/" + shelfCode + "/" + binCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WarehouseLocation that)) return false;
        return facilityCode.equals(that.facilityCode)
                && zoneCode.equals(that.zoneCode)
                && shelfCode.equals(that.shelfCode)
                && binCode.equals(that.binCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facilityCode, zoneCode, shelfCode, binCode);
    }
}
