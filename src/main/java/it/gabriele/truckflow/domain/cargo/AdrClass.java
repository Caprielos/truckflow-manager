package it.gabriele.truckflow.domain.cargo;

/**
 * Rappresenta la classe ADR della merce pericolosa.
 */
public enum AdrClass {

    CLASS_1_EXPLOSIVES("1"),
    CLASS_2_GASES("2"),
    CLASS_3_FLAMMABLE_LIQUIDS("3"),
    CLASS_4_1_FLAMMABLE_SOLIDS("4.1"),
    CLASS_4_2_SPONTANEOUS_COMBUSTION("4.2"),
    CLASS_4_3_WATER_REACTIVE("4.3"),
    CLASS_5_1_OXIDIZING_SUBSTANCES("5.1"),
    CLASS_5_2_ORGANIC_PEROXIDES("5.2"),
    CLASS_6_1_TOXIC_SUBSTANCES("6.1"),
    CLASS_6_2_INFECTIOUS_SUBSTANCES("6.2"),
    CLASS_7_RADIOACTIVE_MATERIAL("7"),
    CLASS_8_CORROSIVE_SUBSTANCES("8"),
    CLASS_9_MISCELLANEOUS("9");

    private final String code;

    AdrClass(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public boolean isExplosives() {
        return this == CLASS_1_EXPLOSIVES;
    }

    public boolean isGas() {
        return this == CLASS_2_GASES;
    }

    public boolean isFlammableLiquid() {
        return this == CLASS_3_FLAMMABLE_LIQUIDS;
    }

    public boolean isRadioactive() {
        return this == CLASS_7_RADIOACTIVE_MATERIAL;
    }
}
