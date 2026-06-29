package it.gabriele.truckflow.domain.cargo;

import static org.junit.jupiter.api.Assertions.*;

import it.gabriele.truckflow.domain.shared.Dimension;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TemperatureRange;
import it.gabriele.truckflow.domain.shared.Volume;
import it.gabriele.truckflow.domain.shared.Weight;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Testa CargoLoad. */
class CargoLoadTest {

  @Test
  void shouldCreateCargoLoadFromList() {
    CargoItem firstItem = generalItem("Bancale 1", 100, Dimension.ofMeters(1, 1, 1));
    CargoItem secondItem = generalItem("Bancale 2", 150, Dimension.ofMeters(2, 1, 1));

    CargoLoad load = CargoLoad.of(List.of(firstItem, secondItem));

    assertEquals(2, load.getItemCount());
    assertEquals(List.of(firstItem, secondItem), load.getItems());
  }

  @Test
  void shouldCreateCargoLoadFromItems() {
    CargoItem firstItem = generalItem("Bancale 1", 100, Dimension.ofMeters(1, 1, 1));
    CargoItem secondItem = generalItem("Bancale 2", 150, Dimension.ofMeters(2, 1, 1));

    CargoLoad load = CargoLoad.of(firstItem, secondItem);

    assertEquals(2, load.getItemCount());
  }

  @Test
  void shouldNotAllowNullOrEmptyList() {
    assertThrows(IllegalArgumentException.class, () -> CargoLoad.of((List<CargoItem>) null));
    assertThrows(IllegalArgumentException.class, () -> CargoLoad.of(List.of()));
  }

  @Test
  void shouldNotAllowNullFirstItem() {
    assertThrows(IllegalArgumentException.class, () -> CargoLoad.of((CargoItem) null));
  }

  @Test
  void shouldNotAllowNullItemsInsideList() {
    CargoItem item = generalItem("Bancale", 100, Dimension.ofMeters(1, 1, 1));

    assertThrows(
        IllegalArgumentException.class, () -> CargoLoad.of(java.util.Arrays.asList(item, null)));
  }

  @Test
  void shouldReturnUnmodifiableItems() {
    CargoItem item = generalItem("Bancale", 100, Dimension.ofMeters(1, 1, 1));
    CargoLoad load = CargoLoad.of(item);

    assertThrows(
        UnsupportedOperationException.class,
        () -> load.getItems().add(generalItem("Altro bancale", 50, Dimension.ofMeters(1, 1, 1))));
  }

  @Test
  void shouldCalculateTotalWeight() {
    CargoItem firstItem = generalItem("Bancale 1", 100, Dimension.ofMeters(1, 1, 1));
    CargoItem secondItem = generalItem("Bancale 2", 150, Dimension.ofMeters(2, 1, 1));

    CargoLoad load = CargoLoad.of(firstItem, secondItem);

    assertEquals(Weight.ofKilograms(250), load.calculateTotalWeight());
  }

  @Test
  void shouldCalculateTotalVolume() {
    CargoItem firstItem = generalItem("Bancale 1", 100, Dimension.ofMeters(1, 1, 1));
    CargoItem secondItem = generalItem("Bancale 2", 150, Dimension.ofMeters(2, 2, 1));

    CargoLoad load = CargoLoad.of(firstItem, secondItem);

    assertEquals(Volume.ofCubicMeters(5), load.calculateTotalVolume());
  }

  @Test
  void shouldDetectTemperatureControlRequirement() {
    CargoItem normalItem = generalItem("Bancale", 100, Dimension.ofMeters(1, 1, 1));
    CargoItem refrigeratedItem = refrigeratedItem();

    CargoLoad load = CargoLoad.of(normalItem, refrigeratedItem);

    assertTrue(load.requiresTemperatureControl());
  }

  @Test
  void shouldDetectCategory() {
    CargoItem item = generalItem("Vetri", 80, Dimension.ofMeters(1, 1, 1), CargoCategory.FRAGILE);

    CargoLoad load = CargoLoad.of(item);

    assertTrue(load.hasCategory(CargoCategory.FRAGILE));
    assertFalse(load.hasCategory(CargoCategory.FOOD));
  }

  @Test
  void shouldNotCheckNullCategory() {
    CargoItem item = generalItem("Bancale", 100, Dimension.ofMeters(1, 1, 1));
    CargoLoad load = CargoLoad.of(item);

    assertThrows(IllegalArgumentException.class, () -> load.hasCategory(null));
  }

  @Test
  void shouldCheckIfAllItemsFitInsideCargoSpace() {
    CargoItem firstItem = generalItem("Bancale 1", 100, Dimension.ofMeters(1, 1, 1));
    CargoItem secondItem = generalItem("Bancale 2", 150, Dimension.ofMeters(2, 1, 1));

    CargoLoad load = CargoLoad.of(firstItem, secondItem);

    assertTrue(load.allItemsFitInside(Dimension.ofMeters(2, 2, 2)));
  }

  @Test
  void shouldDetectWhenOneItemDoesNotFitInsideCargoSpace() {
    CargoItem firstItem = generalItem("Bancale 1", 100, Dimension.ofMeters(1, 1, 1));
    CargoItem secondItem = generalItem("Bancale grande", 150, Dimension.ofMeters(3, 1, 1));

    CargoLoad load = CargoLoad.of(firstItem, secondItem);

    assertFalse(load.allItemsFitInside(Dimension.ofMeters(2, 2, 2)));
  }

  @Test
  void shouldNotCheckFitWithNullDimension() {
    CargoItem item = generalItem("Bancale", 100, Dimension.ofMeters(1, 1, 1));
    CargoLoad load = CargoLoad.of(item);

    assertThrows(IllegalArgumentException.class, () -> load.allItemsFitInside(null));
  }

  @Test
  void shouldConsiderEquivalentCargoLoadsEqual() {
    CargoItem item = generalItem("Bancale", 100, Dimension.ofMeters(1, 1, 1));

    CargoLoad first = CargoLoad.of(item);
    CargoLoad second = CargoLoad.of(item);

    assertEquals(first, second);
    assertEquals(first.hashCode(), second.hashCode());
  }

  private static CargoItem generalItem(String description, double kilograms, Dimension dimension) {
    return generalItem(description, kilograms, dimension, CargoCategory.GENERAL);
  }

  private static CargoItem generalItem(
      String description, double kilograms, Dimension dimension, CargoCategory category) {
    return CargoItem.of(
        description, category, Weight.ofKilograms(kilograms), dimension, Notes.empty());
  }

  private static CargoItem refrigeratedItem() {
    return CargoItem.temperatureControlled(
        "Latte fresco",
        CargoCategory.REFRIGERATED_FOOD,
        Weight.ofKilograms(200),
        Dimension.ofMeters(1, 1, 1),
        TemperatureRange.ofCelsius(2, 8),
        Notes.of("Mantenere refrigerato"));
  }
}
