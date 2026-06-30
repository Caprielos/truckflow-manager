package it.gabriele.truckflow.domain.users;

import java.util.Locale;
import java.util.Set;

public record UserPreferences(String language, String theme, boolean notificationsEnabled) {

  private static final String DEFAULT_LANGUAGE = "en";
  private static final String DEFAULT_THEME = "light";
  private static final Set<String> SUPPORTED_THEMES = Set.of("light", "dark");

  public UserPreferences {
    language = normalize(language).isBlank() ? DEFAULT_LANGUAGE : normalize(language).toLowerCase();
    theme = normalize(theme).isBlank() ? DEFAULT_THEME : normalize(theme).toLowerCase(Locale.ROOT);

    if (!SUPPORTED_THEMES.contains(theme)) {
      throw new IllegalArgumentException("Theme must be either 'light' or 'dark'.");
    }
  }

  public static UserPreferences defaults() {
    return new UserPreferences(DEFAULT_LANGUAGE, DEFAULT_THEME, true);
  }

  public UserPreferences withLanguage(String language) {
    return new UserPreferences(language, theme, notificationsEnabled);
  }

  public UserPreferences withTheme(String theme) {
    return new UserPreferences(language, theme, notificationsEnabled);
  }

  public UserPreferences withNotificationsEnabled(boolean notificationsEnabled) {
    return new UserPreferences(language, theme, notificationsEnabled);
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
