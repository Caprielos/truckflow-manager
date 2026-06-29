package it.gabriele.truckflow.domain.tachograph;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;

/** Regole per tachigrafo digitale, download carta e violazioni ore guida/riposo. */
public final class TachographRules {

  private TachographRules() {}

  public static long totalDrivingMinutes(Collection<TachographActivity> activities) {
    if (activities == null) {
      throw new IllegalArgumentException("Le attività tachigrafiche sono obbligatorie.");
    }
    return activities.stream()
        .filter(activity -> activity.activityType() == TachographActivityType.DRIVING)
        .mapToLong(TachographActivity::durationMinutes)
        .sum();
  }

  public static boolean exceedsDailyDrivingLimit(Collection<TachographActivity> activities) {
    return totalDrivingMinutes(activities) > 9 * 60;
  }

  public static boolean violationRequiresAlert(DrivingTimeViolation violation) {
    Objects.requireNonNull(violation, "La violazione tachigrafo è obbligatoria.");
    return !violation.acknowledged()
        && (violation.minutesOverLimit() > 0
            || violation.type() == DrivingTimeViolationType.CARD_NOT_INSERTED
            || violation.type() == DrivingTimeViolationType.MANUAL_ENTRY_MISSING);
  }

  public static boolean downloadIsUsable(DriverCardDownload download, LocalDate expectedPeriodEnd) {
    Objects.requireNonNull(download, "Lo scarico tachigrafo è obbligatorio.");
    Objects.requireNonNull(expectedPeriodEnd, "La fine periodo attesa è obbligatoria.");
    return download.signedArchive()
        && download.parseSuccessful()
        && !download.periodEnd().isBefore(expectedPeriodEnd);
  }
}
