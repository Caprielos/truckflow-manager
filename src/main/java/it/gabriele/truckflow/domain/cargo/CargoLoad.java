package it.gabriele.truckflow.domain.cargo;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Rappresenta un insieme di colli/articoli da trasportare insieme.
 */
public final class CargoLoad {

    private final List<CargoItem> items;

    private CargoLoad(List<CargoItem> items) {
        if (items == null) {
            throw new IllegalArgumentException("La lista dei carichi è obbligatoria.");
        }

        if (items.isEmpty()) {
            throw new IllegalArgumentException("Il carico deve contenere almeno un articolo.");
        }

        if (items.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista dei carichi non può contenere elementi nulli.");
        }

        this.items = List.copyOf(items);
    }

    public static CargoLoad of(List<CargoItem> items) {
        return new CargoLoad(items);
    }

    public static CargoLoad of(CargoItem firstItem, CargoItem... otherItems) {
        if (firstItem == null) {
            throw new IllegalArgumentException("Il primo articolo del carico è obbligatorio.");
        }

        List<CargoItem> items = new ArrayList<>();
        items.add(firstItem);

        if (otherItems != null) {
            for (CargoItem item : otherItems) {
                items.add(item);
            }
        }

        return new CargoLoad(items);
    }

    public List<CargoItem> getItems() {
        return items;
    }

    public int getItemCount() {
        return items.size();
    }

    public Weight calculateTotalWeight() {
        double totalKilograms = items.stream()
                .mapToDouble(item -> item.getWeight().getKilograms())
                .sum();

        return Weight.ofKilograms(totalKilograms);
    }

    public Volume calculateTotalVolume() {
        double totalCubicMeters = items.stream()
                .mapToDouble(item -> item.calculateVolume().getCubicMeters())
                .sum();

        return Volume.ofCubicMeters(totalCubicMeters);
    }

    public boolean requiresTemperatureControl() {
        return items.stream().anyMatch(CargoItem::requiresTemperatureControl);
    }

    public boolean containsDangerousGoods() {
        return items.stream().anyMatch(CargoItem::isDangerousGoods);
    }

    public boolean hasDangerousGoodsProfile() {
        return items.stream().anyMatch(CargoItem::hasDangerousGoodsProfile);
    }

    public boolean requiresAdrTransport() {
        return items.stream().anyMatch(CargoItem::requiresAdrTransport);
    }

    public boolean requiresAdrTankTransport() {
        return items.stream().anyMatch(CargoItem::requiresAdrTankTransport);
    }

    public boolean containsAdrClass(AdrClass adrClass) {
        if (adrClass == null) {
            throw new IllegalArgumentException("La classe ADR da verificare è obbligatoria.");
        }

        return items.stream().anyMatch(item -> item.isAdrClass(adrClass));
    }

    public boolean hasCategory(CargoCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("La categoria da verificare è obbligatoria.");
        }

        return items.stream().anyMatch(item -> item.getCategory() == category);
    }

    public boolean allItemsFitInside(Dimension cargoSpaceDimension) {
        if (cargoSpaceDimension == null) {
            throw new IllegalArgumentException("Le dimensioni dello spazio di carico sono obbligatorie.");
        }

        return items.stream()
                .allMatch(item -> item.getDimension().fitsInside(cargoSpaceDimension));
    }

    public List<DangerousGoodsProfile> getDangerousGoodsProfiles() {
        return items.stream()
                .filter(CargoItem::hasDangerousGoodsProfile)
                .map(CargoItem::getDangerousGoodsProfile)
                .toList();
    }

    public String formatSingleLine() {
        return "items: " + items.size()
                + " - total weight: " + calculateTotalWeight()
                + " - total volume: " + calculateTotalVolume();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CargoLoad cargoLoad)) return false;
        return items.equals(cargoLoad.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
