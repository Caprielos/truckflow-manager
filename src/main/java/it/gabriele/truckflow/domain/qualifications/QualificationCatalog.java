package it.gabriele.truckflow.domain.qualifications;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class QualificationCatalog {

  private static final Map<String, Qualification> QUALIFICATIONS_BY_CODE =
      Arrays.stream(Qualification.values())
          .collect(Collectors.toUnmodifiableMap(Qualification::code, Function.identity()));

  private QualificationCatalog() {}

  public static List<Qualification> all() {
    return List.of(Qualification.values());
  }

  public static List<Qualification> byCategory(QualificationCategory category) {
    return Arrays.stream(Qualification.values())
        .filter(qualification -> qualification.category() == category)
        .toList();
  }

  public static Optional<Qualification> findByCode(String code) {
    return Optional.ofNullable(QUALIFICATIONS_BY_CODE.get(code));
  }
}
