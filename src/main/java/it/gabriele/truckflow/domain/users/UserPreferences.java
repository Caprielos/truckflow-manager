package it.gabriele.truckflow.domain.users;

public record UserPreferences(
    LanguageCode language, UserTheme theme, boolean notificationsEnabled) {

  public UserPreferences {
    language = language == null ? LanguageCode.DEFAULT : language;
    theme = theme == null ? UserTheme.DEFAULT : theme;
  }

  public UserPreferences(String language, String theme, boolean notificationsEnabled) {
    this(
        LanguageCode.fromOrDefault(language), UserTheme.fromOrDefault(theme), notificationsEnabled);
  }

  public static UserPreferences defaults() {
    return new UserPreferences(LanguageCode.DEFAULT, UserTheme.DEFAULT, true);
  }

  public UserPreferences withLanguage(LanguageCode language) {
    return new UserPreferences(language, theme, notificationsEnabled);
  }

  public UserPreferences withLanguage(String language) {
    return withLanguage(LanguageCode.fromOrDefault(language));
  }

  public UserPreferences withTheme(UserTheme theme) {
    return new UserPreferences(language, theme, notificationsEnabled);
  }

  public UserPreferences withTheme(String theme) {
    return withTheme(UserTheme.fromOrDefault(theme));
  }

  public UserPreferences withNotificationsEnabled(boolean notificationsEnabled) {
    return new UserPreferences(language, theme, notificationsEnabled);
  }
}
