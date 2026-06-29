package it.gabriele.truckflow.domain.livestock;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

/** Regole di benessere animale per idoneità veicolo e viaggio. */
public final class LivestockRules {

  private static final Duration LONG_JOURNEY_THRESHOLD = Duration.ofHours(8);

  private LivestockRules() {}

  public static boolean hasEnoughSpace(
      LivestockVehicleProfile profile, LivestockTripPlan tripPlan) {
    Objects.requireNonNull(profile, "Il profilo mezzo animali è obbligatorio.");
    Objects.requireNonNull(tripPlan, "Il piano viaggio animali è obbligatorio.");
    return profile.usableAreaSquareMeters() >= tripPlan.requiredTotalAreaSquareMeters();
  }

  public static boolean canDepart(
      LivestockVehicleProfile profile, LivestockTripPlan tripPlan, LocalDate date) {
    return profile.isValidOn(date)
        && profile.authorizedSpecies().contains(tripPlan.species())
        && profile.ventilationAvailable()
        && profile.partitionsAvailable()
        && profile.cleaningAndDisinfectionValid()
        && tripPlan.veterinaryDocumentsPresent()
        && tripPlan.cleaningDisinfectionPlanned()
        && hasEnoughSpace(profile, tripPlan)
        && (!isLongJourney(tripPlan) || profile.wateringSystemAvailable())
        && (!isLongJourney(tripPlan) || tripPlan.routeRestStopsPlanned());
  }

  public static boolean isLongJourney(LivestockTripPlan tripPlan) {
    Objects.requireNonNull(tripPlan, "Il piano viaggio animali è obbligatorio.");
    return tripPlan.plannedDuration().compareTo(LONG_JOURNEY_THRESHOLD) > 0;
  }
}
