package it.gabriele.truckflow.domain.cargo;

/**
 * Rappresenta la categoria operativa della merce trasportata.
 * La categoria merce attiva vincoli su mezzo, autista, documenti,
 * allestimenti, fissaggio del carico e costi di missione.
 */
public enum CargoCategory {

    GENERAL(false, false, false, false, false, false, false, false, false),
    PALLETIZED_DRY_GOODS(false, true, false, false, false, false, false, false, false),

    FOOD(false, false, false, false, false, false, true, false, false),
    REFRIGERATED_FOOD(true, false, false, false, false, false, true, false, false),
    PHARMACEUTICAL(true, false, false, false, false, false, false, false, false),
    TEMPERATURE_CONTROLLED_GOODS(true, false, false, false, false, false, false, false, false),

    FRAGILE(false, false, false, false, false, false, false, false, false),
    ELECTRONICS(false, false, false, false, false, false, false, false, true),
    HIGH_VALUE_GOODS(false, false, false, false, false, false, false, false, true),

    HAZARDOUS_MATERIAL(false, false, true, false, false, false, false, false, false),
    DANGEROUS_GOODS(false, false, true, false, false, false, false, false, false),

    OVERSIZED(false, false, false, false, false, false, false, true, false),
    MACHINERY(false, false, false, false, false, false, false, true, false),
    VEHICLES(false, false, false, false, false, false, false, false, true),
    CONTAINERIZED_GOODS(false, false, false, false, false, false, false, false, false),

    LIQUID(false, false, false, true, false, false, false, false, false),
    FOOD_GRADE_LIQUID(false, false, false, true, false, false, true, false, false),
    FUEL(false, false, true, true, false, false, false, false, false),
    GAS(false, false, true, false, false, false, false, false, false),

    CONSTRUCTION_MATERIAL(false, false, false, false, true, false, false, false, false),
    BULK_DRY(false, false, false, false, true, false, false, false, false),
    BULK_INERT_GOODS(false, false, false, false, true, false, false, false, false),
    AGRICULTURAL_BULK(false, false, false, false, true, false, true, false, false),
    HAY_BALES(false, false, false, false, false, false, true, false, false),
    COILS(false, false, false, false, false, false, false, false, false),
    CONCRETE(false, false, false, false, true, false, false, false, false),

    WASTE_NON_DANGEROUS(false, false, false, false, true, true, false, false, false),
    WASTE_DANGEROUS(false, false, true, false, true, true, false, false, false),
    LIVESTOCK(false, false, false, false, false, false, true, false, false);

    private final boolean requiresTemperatureControl;
    private final boolean palletized;
    private final boolean requiresAdrData;
    private final boolean liquid;
    private final boolean bulk;
    private final boolean waste;
    private final boolean sanitarySensitive;
    private final boolean oversized;
    private final boolean highValueOrVehicle;

    CargoCategory(
            boolean requiresTemperatureControl,
            boolean palletized,
            boolean requiresAdrData,
            boolean liquid,
            boolean bulk,
            boolean waste,
            boolean sanitarySensitive,
            boolean oversized,
            boolean highValueOrVehicle
    ) {
        this.requiresTemperatureControl = requiresTemperatureControl;
        this.palletized = palletized;
        this.requiresAdrData = requiresAdrData;
        this.liquid = liquid;
        this.bulk = bulk;
        this.waste = waste;
        this.sanitarySensitive = sanitarySensitive;
        this.oversized = oversized;
        this.highValueOrVehicle = highValueOrVehicle;
    }

    public boolean requiresTemperatureControl() {
        return requiresTemperatureControl;
    }

    public boolean isPalletized() {
        return palletized;
    }

    public boolean requiresAdrData() {
        return requiresAdrData;
    }

    public boolean isLiquid() {
        return liquid;
    }

    public boolean isBulk() {
        return bulk;
    }

    public boolean isWaste() {
        return waste;
    }

    public boolean requiresSanitaryOrVeterinaryDocuments() {
        return sanitarySensitive || this == LIVESTOCK || this == FOOD_GRADE_LIQUID;
    }

    public boolean requiresFirDocument() {
        return waste;
    }

    public boolean requiresEerCode() {
        return waste;
    }

    public boolean isOversized() {
        return oversized;
    }

    public boolean isHighValueOrVehicle() {
        return highValueOrVehicle;
    }
}
