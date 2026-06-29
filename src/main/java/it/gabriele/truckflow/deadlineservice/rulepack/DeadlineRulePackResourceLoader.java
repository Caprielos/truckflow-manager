package it.gabriele.truckflow.deadlineservice.rulepack;

import it.gabriele.truckflow.deadlineservice.domain.DeadlineRuleSourceType;
import it.gabriele.truckflow.deadlineservice.domain.ManagedElementCode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Loader minimale del file unico deadline-rule-pack.yml, senza dipendenze da parser YAML esterni.
 */
public final class DeadlineRulePackResourceLoader {
  public static final String DEFAULT_RESOURCE = "/deadlineservice/deadline-rule-pack.yml";

  private DeadlineRulePackResourceLoader() {}

  public static DeadlineRulePack loadDefault() {
    InputStream inputStream =
        DeadlineRulePackResourceLoader.class.getResourceAsStream(DEFAULT_RESOURCE);
    if (inputStream == null) {
      throw new IllegalStateException("Risorsa rule pack non trovata: " + DEFAULT_RESOURCE);
    }
    return load(inputStream);
  }

  public static DeadlineRulePack load(InputStream inputStream) {
    try {
      return parse(readLines(inputStream));
    } catch (IOException exception) {
      throw new IllegalStateException("Impossibile leggere il deadline rule pack.", exception);
    }
  }

  private static DeadlineRulePack parse(List<String> lines) {
    Map<String, String> metadata = new LinkedHashMap<>();
    List<Map<String, String>> rawRules = new ArrayList<>();
    Map<String, String> currentRule = null;
    boolean insideRulePack = false;
    boolean insideRules = false;

    for (String rawLine : lines) {
      String line = stripComment(rawLine);
      if (line.isBlank()) {
        continue;
      }
      if (line.equals("rulePack:")) {
        insideRulePack = true;
        insideRules = false;
        continue;
      }
      if (line.equals("rules:")) {
        insideRulePack = false;
        insideRules = true;
        continue;
      }
      if (insideRulePack && line.startsWith("  ")) {
        putKeyValue(metadata, line.strip());
        continue;
      }
      if (insideRules && line.startsWith("  - ")) {
        currentRule = new LinkedHashMap<>();
        rawRules.add(currentRule);
        putKeyValue(currentRule, line.substring(line.indexOf('-') + 1).strip());
        continue;
      }
      if (insideRules && currentRule != null && line.startsWith("    ")) {
        putKeyValue(currentRule, line.strip());
      }
    }

    return new DeadlineRulePack(
        require(metadata, "id"),
        require(metadata, "version"),
        DeadlineRulePackStatus.valueOf(require(metadata, "status")),
        require(metadata, "defaultCountry"),
        require(metadata, "tenantId"),
        rawRules.stream().map(DeadlineRulePackResourceLoader::toRule).toList());
  }

  private static DeadlineRulePackRule toRule(Map<String, String> values) {
    return new DeadlineRulePackRule(
        require(values, "ruleId"),
        ManagedElementCode.valueOf(require(values, "elementCode")),
        parseSourceTypes(require(values, "sourceTypes")),
        DeadlineRuleSlotStatus.valueOf(require(values, "status")),
        Boolean.parseBoolean(require(values, "fillableFromUi")),
        values.getOrDefault("description", ""));
  }

  private static List<String> readLines(InputStream inputStream) throws IOException {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
      return reader.lines().toList();
    }
  }

  private static void putKeyValue(Map<String, String> target, String line) {
    int separatorIndex = line.indexOf(':');
    if (separatorIndex <= 0) {
      return;
    }
    String key = line.substring(0, separatorIndex).strip();
    String value = line.substring(separatorIndex + 1).strip();
    target.put(key, unquote(value));
  }

  private static Set<DeadlineRuleSourceType> parseSourceTypes(String value) {
    String cleaned = value.replace("[", "").replace("]", "").strip();
    if (cleaned.isBlank()) {
      throw new IllegalArgumentException("sourceTypes non può essere vuoto.");
    }
    EnumSet<DeadlineRuleSourceType> sourceTypes = EnumSet.noneOf(DeadlineRuleSourceType.class);
    for (String item : cleaned.split(",")) {
      sourceTypes.add(DeadlineRuleSourceType.valueOf(unquote(item.strip())));
    }
    return sourceTypes;
  }

  private static String stripComment(String line) {
    int commentIndex = line.indexOf('#');
    return commentIndex >= 0 ? line.substring(0, commentIndex) : line;
  }

  private static String unquote(String value) {
    String cleaned = value.strip();
    if (cleaned.length() >= 2 && cleaned.startsWith("\"") && cleaned.endsWith("\"")) {
      return cleaned.substring(1, cleaned.length() - 1);
    }
    return cleaned;
  }

  private static String require(Map<String, String> values, String key) {
    String value = values.get(key);
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("Campo obbligatorio mancante nel rule pack: " + key);
    }
    return value;
  }
}
