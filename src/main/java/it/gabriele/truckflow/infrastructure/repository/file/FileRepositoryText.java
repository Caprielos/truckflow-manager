package it.gabriele.truckflow.infrastructure.repository.file;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Small text conversion helpers used by file-backed repository adapters. */
public final class FileRepositoryText {

  private static final String COLLECTION_SEPARATOR = ",";
  private static final String STRUCTURE_SEPARATOR = "~";

  private FileRepositoryText() {}

  public static String value(BigDecimal value) {
    return value == null ? "" : value.toPlainString();
  }

  public static BigDecimal decimal(String value) {
    return value == null || value.isBlank() ? null : new BigDecimal(value);
  }

  public static String value(Integer value) {
    return value == null ? "" : value.toString();
  }

  public static Integer integer(String value) {
    return value == null || value.isBlank() ? null : Integer.valueOf(value);
  }

  public static <E extends Enum<E>> String enumSet(Collection<E> values) {
    if (values == null || values.isEmpty()) {
      return "";
    }
    return values.stream()
        .map(Enum::name)
        .sorted()
        .collect(Collectors.joining(COLLECTION_SEPARATOR));
  }

  public static <E extends Enum<E>> java.util.Set<E> parseEnumSet(String value, Class<E> enumType) {
    if (value == null || value.isBlank()) {
      return java.util.Set.of();
    }
    return java.util.Arrays.stream(value.split(COLLECTION_SEPARATOR, -1))
        .filter(token -> !token.isBlank())
        .map(token -> Enum.valueOf(enumType, token))
        .collect(Collectors.toUnmodifiableSet());
  }

  public static String encodedStrings(Collection<String> values) {
    if (values == null || values.isEmpty()) {
      return "";
    }
    return values.stream()
        .map(FileRepositoryText::encodeInner)
        .sorted()
        .collect(Collectors.joining(COLLECTION_SEPARATOR));
  }

  public static java.util.Set<String> parseEncodedStrings(String value) {
    if (value == null || value.isBlank()) {
      return java.util.Set.of();
    }
    return java.util.Arrays.stream(value.split(COLLECTION_SEPARATOR, -1))
        .filter(token -> !token.isBlank())
        .map(FileRepositoryText::decodeInner)
        .collect(Collectors.toUnmodifiableSet());
  }

  public static <T> String encodedStructures(
      Collection<T> values, Function<T, List<String>> fieldsMapper) {
    if (values == null || values.isEmpty()) {
      return "";
    }
    return values.stream()
        .map(fieldsMapper)
        .map(FileRepositoryText::encodedStructure)
        .sorted()
        .collect(Collectors.joining(COLLECTION_SEPARATOR));
  }

  public static List<List<String>> parseEncodedStructures(String value) {
    if (value == null || value.isBlank()) {
      return List.of();
    }
    return java.util.Arrays.stream(value.split(COLLECTION_SEPARATOR, -1))
        .filter(token -> !token.isBlank())
        .map(FileRepositoryText::parseEncodedStructure)
        .toList();
  }

  private static String encodedStructure(List<String> fields) {
    return fields.stream()
        .map(FileRepositoryText::encodeInner)
        .collect(Collectors.joining(STRUCTURE_SEPARATOR));
  }

  private static List<String> parseEncodedStructure(String value) {
    return java.util.Arrays.stream(value.split(STRUCTURE_SEPARATOR, -1))
        .map(FileRepositoryText::decodeInner)
        .toList();
  }

  private static String encodeInner(String value) {
    return Base64.getUrlEncoder()
        .withoutPadding()
        .encodeToString(Objects.toString(value, "").getBytes(StandardCharsets.UTF_8));
  }

  private static String decodeInner(String value) {
    return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
  }
}
