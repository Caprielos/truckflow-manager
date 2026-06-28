package it.gabriele.truckflow.domain.drivetime;

import java.time.Duration;

public final class DriverTimeRules {

    private static final Duration MAX_CONTINUOUS_DRIVING = Duration.ofHours(4).plusMinutes(30);
    private static final Duration STANDARD_DAILY_DRIVING = Duration.ofHours(9);
    private static final Duration EXTENDED_DAILY_DRIVING = Duration.ofHours(10);
    private static final Duration REQUIRED_BREAK = Duration.ofMinutes(45);
    private static final Duration STANDARD_DAILY_REST = Duration.ofHours(11);
    private static final Duration MAX_WEEKLY_DRIVING = Duration.ofHours(56);
    private static final Duration MAX_TWO_WEEK_DRIVING = Duration.ofHours(90);

    private DriverTimeRules() {
    }

    public static boolean requiresBreakAfter(Duration continuousDriving) {
        validateDuration(continuousDriving);
        return continuousDriving.compareTo(MAX_CONTINUOUS_DRIVING) >= 0;
    }

    public static boolean isWithinStandardDailyDriving(Duration dailyDriving) {
        validateDuration(dailyDriving);
        return dailyDriving.compareTo(STANDARD_DAILY_DRIVING) <= 0;
    }

    public static boolean isWithinExtendedDailyDriving(Duration dailyDriving) {
        validateDuration(dailyDriving);
        return dailyDriving.compareTo(EXTENDED_DAILY_DRIVING) <= 0;
    }

    public static Duration requiredBreak() {
        return REQUIRED_BREAK;
    }

    public static Duration standardDailyRest() {
        return STANDARD_DAILY_REST;
    }

    public static boolean isWithinWeeklyDriving(Duration weeklyDriving) {
        validateDuration(weeklyDriving);
        return weeklyDriving.compareTo(MAX_WEEKLY_DRIVING) <= 0;
    }

    public static boolean isWithinTwoWeekDriving(Duration twoWeekDriving) {
        validateDuration(twoWeekDriving);
        return twoWeekDriving.compareTo(MAX_TWO_WEEK_DRIVING) <= 0;
    }

    private static void validateDuration(Duration duration) {
        if (duration == null) {
            throw new IllegalArgumentException("La durata è obbligatoria.");
        }
        if (duration.isNegative()) {
            throw new IllegalArgumentException("La durata non può essere negativa.");
        }
    }
}
