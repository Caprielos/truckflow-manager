package it.gabriele.truckflow.infrastructure.mapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class PersistenceMappingBlueprintTest {

  private static final Path INFRASTRUCTURE_ROOT =
      Path.of("src/main/java/it/gabriele/truckflow/infrastructure");
  private static final Path MAPPING_ROOT = INFRASTRUCTURE_ROOT.resolve("mapping");
  private static final Path DOMAIN_ROOT = Path.of("src/main/java/it/gabriele/truckflow/domain");
  private static final Path APPLICATION_ROOT =
      Path.of("src/main/java/it/gabriele/truckflow/application");

  @Test
  void mappingBlueprintCatalogCoversCurrentApplicationContexts() {
    Set<String> contexts =
        PersistenceMappingBlueprintCatalog.all().stream()
            .map(PersistenceMappingBlueprint::contextName)
            .collect(Collectors.toUnmodifiableSet());

    assertTrue(contexts.contains("locations"));
    assertTrue(contexts.contains("cargo"));
    assertTrue(contexts.contains("shipments"));
    assertTrue(contexts.contains("documents"));
    assertTrue(contexts.contains("vehicles.unit"));
    assertTrue(contexts.contains("vehicles.combination"));
    assertTrue(contexts.contains("operational.driver"));
    assertTrue(contexts.contains("operational.mechanic"));
    assertTrue(contexts.contains("operational.warehouse"));
    assertTrue(contexts.contains("operational.dispatcher"));
    assertTrue(contexts.contains("operational.manager"));
    assertTrue(contexts.contains("compliance"));
  }

  @Test
  void everyBlueprintKeepsStableIdentityCodeAndStateWhereExpected() {
    for (PersistenceMappingBlueprint blueprint : PersistenceMappingBlueprintCatalog.all()) {
      assertFalse(blueprint.fields().isEmpty(), () -> "Missing fields for " + blueprint);
      assertTrue(
          blueprint.hasMandatoryDomainField("id"),
          () -> "Every current aggregate blueprint needs a mandatory id: " + blueprint);

      boolean codeOrFleetCode =
          blueprint.hasMandatoryDomainField("code")
              || blueprint.hasMandatoryDomainField("fleetCode");
      assertTrue(
          codeOrFleetCode,
          () -> "Every current aggregate blueprint needs a business code: " + blueprint);

      assertTrue(
          blueprint.hasMandatoryDomainField("status"),
          () -> "Every current aggregate blueprint needs a lifecycle status: " + blueprint);
    }
  }

  @Test
  void blueprintLookupIsExplicitAndNullSafe() {
    assertTrue(PersistenceMappingBlueprintCatalog.findByContext("shipments").isPresent());
    assertTrue(PersistenceMappingBlueprintCatalog.findByContext(" unknown ").isEmpty());
    assertTrue(PersistenceMappingBlueprintCatalog.findByContext(null).isEmpty());
    assertTrue(PersistenceMappingBlueprintCatalog.findByContext(" ").isEmpty());
  }

  @Test
  void mappingBlueprintRecordsRejectInvalidInput() {
    PersistenceFieldMapping validField =
        new PersistenceFieldMapping(
            "id", "id", PersistenceMappingKind.VALUE_OBJECT, true, "Stable identifier.");

    assertThrows(
        IllegalArgumentException.class,
        () ->
            new PersistenceFieldMapping(
                " ", "id", PersistenceMappingKind.VALUE_OBJECT, true, "Invalid."));
    assertThrows(
        NullPointerException.class,
        () -> new PersistenceFieldMapping("id", "id", null, true, "Invalid."));
    assertThrows(
        IllegalArgumentException.class,
        () -> new PersistenceMappingBlueprint("ctx", "Domain", "Model", List.of(), "Invalid."));
    assertThrows(
        NullPointerException.class,
        () -> new PersistenceMappingBlueprint("ctx", "Domain", "Model", null, "Invalid."));

    PersistenceMappingBlueprint blueprint =
        new PersistenceMappingBlueprint(
            "ctx", "Domain", "Model", List.of(validField), "Valid blueprint.");
    assertEquals("ctx", blueprint.contextName());
    assertTrue(blueprint.hasMandatoryDomainField("id"));
  }

  @Test
  void mappingBlueprintDoesNotIntroducePrematurePersistenceFrameworks() throws IOException {
    List<String> forbiddenPatterns =
        List.of(
            "@Entity",
            "@Table",
            "@Column",
            "JpaRepository",
            "CrudRepository",
            "JdbcTemplate",
            "MongoRepository",
            "EntityManager");

    List<Path> violations = filesContaining(MAPPING_ROOT, forbiddenPatterns);

    assertTrue(
        violations.isEmpty(),
        () -> "Persistence mapping blueprint must stay framework-neutral: " + violations);
    assertFalse(Files.exists(INFRASTRUCTURE_ROOT.resolve("jpa")));
    assertFalse(Files.exists(INFRASTRUCTURE_ROOT.resolve("database")));
    assertFalse(Files.exists(INFRASTRUCTURE_ROOT.resolve("persistence")));
  }

  @Test
  void coreLayersStillDoNotDependOnInfrastructureMapping() throws IOException {
    List<String> forbiddenPatterns = List.of("it.gabriele.truckflow.infrastructure.mapping");

    assertTrue(filesContaining(DOMAIN_ROOT, forbiddenPatterns).isEmpty());
    assertTrue(filesContaining(APPLICATION_ROOT, forbiddenPatterns).isEmpty());
  }

  @Test
  void persistenceMappingBlueprintDocumentationIsPresent() {
    assertTrue(Files.exists(Path.of("docs/old_style/33-persistence-mapping-blueprint.md")));
    assertTrue(Files.exists(Path.of("docs/old_style/32-spring-wiring-foundation.md")));
    assertTrue(Files.exists(Path.of("docs/digital/index.html")));
  }

  private static List<Path> filesContaining(Path root, List<String> patterns) throws IOException {
    if (!Files.exists(root)) {
      return List.of();
    }

    try (Stream<Path> stream = Files.walk(root)) {
      return stream
          .filter(path -> path.toString().endsWith(".java"))
          .filter(path -> containsAny(path, patterns))
          .toList();
    }
  }

  private static boolean containsAny(Path path, List<String> patterns) {
    try {
      String content = Files.readString(path);
      return patterns.stream().anyMatch(content::contains);
    } catch (IOException exception) {
      throw new IllegalStateException("Unable to read source file " + path, exception);
    }
  }
}
