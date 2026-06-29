package it.gabriele.truckflow.domain.foodsafety;

/** Tipologia alimentare/farmaceutica con esigenze igieniche e termiche. */
public enum FoodProductType {
  DRY_AMBIENT(false),
  FRESH_CHILLED(true),
  FROZEN(true),
  DEEP_FROZEN(true),
  BULK_FOOD(false),
  FOOD_GRADE_TANK(false),
  PHARMA_TEMPERATURE_CONTROLLED(true),
  PERISHABLE(true);

  private final boolean temperatureControlled;

  FoodProductType(boolean temperatureControlled) {
    this.temperatureControlled = temperatureControlled;
  }

  public boolean requiresTemperatureControl() {
    return temperatureControlled;
  }
}
