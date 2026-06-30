package it.gabriele.truckflow.domain.users;

import it.gabriele.truckflow.domain.users.exceptions.InvalidUserException;
import java.util.Locale;

public record Username(String value) {

  private static final int MIN_LENGTH = 3;
  private static final int MAX_LENGTH = 50;
  private static final String ALLOWED_PATTERN = "[a-z0-9._-]+";

  public Username {
    value = normalize(value);

    if (value.length() < MIN_LENGTH) {
      throw new InvalidUserException("Username must contain at least 3 characters.");
    }

    if (value.length() > MAX_LENGTH) {
      throw new InvalidUserException("Username cannot exceed 50 characters.");
    }

    if (!value.matches(ALLOWED_PATTERN)) {
      throw new InvalidUserException(
          "Username can contain only lowercase letters, numbers, dots, underscores and hyphens.");
    }
  }

  private static String normalize(String value) {
    if (value == null) {
      throw new InvalidUserException("Username is required.");
    }

    return value.trim().toLowerCase(Locale.ROOT);
  }
}
