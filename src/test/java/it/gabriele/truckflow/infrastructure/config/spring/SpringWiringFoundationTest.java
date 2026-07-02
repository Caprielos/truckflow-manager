package it.gabriele.truckflow.infrastructure.config.spring;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.gabriele.truckflow.TruckFlowApplication;
import it.gabriele.truckflow.application.port.in.cargo.FindCargoUnitUseCase;
import it.gabriele.truckflow.application.port.in.cargo.RegisterCargoUnitUseCase;
import it.gabriele.truckflow.application.port.in.compliance.RegisterComplianceRequirementUseCase;
import it.gabriele.truckflow.application.port.in.documents.RegisterDocumentUseCase;
import it.gabriele.truckflow.application.port.in.locations.FindLocationUseCase;
import it.gabriele.truckflow.application.port.in.locations.RegisterLocationUseCase;
import it.gabriele.truckflow.application.port.in.operational.RegisterDriverUseCase;
import it.gabriele.truckflow.application.port.in.shipments.CreateShipmentUseCase;
import it.gabriele.truckflow.application.port.in.vehicles.RegisterVehicleUnitUseCase;
import it.gabriele.truckflow.application.port.out.cargo.CargoUnitRepository;
import it.gabriele.truckflow.application.port.out.compliance.ComplianceRequirementRepository;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.application.port.out.locations.LocationRepository;
import it.gabriele.truckflow.application.port.out.operational.DriverRepository;
import it.gabriele.truckflow.application.port.out.shipments.ShipmentRepository;
import it.gabriele.truckflow.application.port.out.vehicles.VehicleUnitRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class SpringWiringFoundationTest {

  private static final Path MAIN_JAVA = Path.of("src/main/java");
  private static final Path DOMAIN_ROOT = MAIN_JAVA.resolve("it/gabriele/truckflow/domain");
  private static final Path APPLICATION_ROOT =
      MAIN_JAVA.resolve("it/gabriele/truckflow/application");
  private static final Path INFRASTRUCTURE_ROOT =
      MAIN_JAVA.resolve("it/gabriele/truckflow/infrastructure");

  @Test
  void springBootstrapClassExistsButKeepsRuntimeNonWebByConfiguration() {
    assertNotNull(TruckFlowApplication.class);
    assertTrue(Files.exists(Path.of("src/main/resources/application.yml")));

    assertDoesNotThrow(
        () -> {
          String configuration = Files.readString(Path.of("src/main/resources/application.yml"));
          assertTrue(configuration.contains("web-application-type: none"));
          assertTrue(configuration.contains("active: memory"));
        });
  }

  @Test
  void memoryProfileWiresRepositoriesAndUseCases() {
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
      context.getEnvironment().setActiveProfiles(SpringProfileNames.MEMORY);
      context.register(TruckFlowSpringWiringConfiguration.class);
      context.refresh();

      assertNotNull(context.getBean(LocationRepository.class));
      assertNotNull(context.getBean(CargoUnitRepository.class));
      assertNotNull(context.getBean(ShipmentRepository.class));
      assertNotNull(context.getBean(DocumentRepository.class));
      assertNotNull(context.getBean(VehicleUnitRepository.class));
      assertNotNull(context.getBean(DriverRepository.class));
      assertNotNull(context.getBean(ComplianceRequirementRepository.class));

      assertNotNull(context.getBean(RegisterLocationUseCase.class));
      assertNotNull(context.getBean(FindLocationUseCase.class));
      assertNotNull(context.getBean(RegisterCargoUnitUseCase.class));
      assertNotNull(context.getBean(FindCargoUnitUseCase.class));
      assertNotNull(context.getBean(CreateShipmentUseCase.class));
      assertNotNull(context.getBean(RegisterDocumentUseCase.class));
      assertNotNull(context.getBean(RegisterVehicleUnitUseCase.class));
      assertNotNull(context.getBean(RegisterDriverUseCase.class));
      assertNotNull(context.getBean(RegisterComplianceRequirementUseCase.class));
    }
  }

  @Test
  void springProfileNamesStayExplicitAndTechnical() {
    assertEquals("memory", SpringProfileNames.MEMORY);
  }

  @Test
  void domainAndApplicationRemainFreeFromSpringAnnotations() throws IOException {
    List<String> forbiddenPatterns =
        List.of(
            "org.springframework",
            "@Component",
            "@Service",
            "@Repository",
            "@Configuration",
            "@Bean",
            "@Autowired");

    List<Path> domainViolations = filesContaining(DOMAIN_ROOT, forbiddenPatterns);
    List<Path> applicationViolations = filesContaining(APPLICATION_ROOT, forbiddenPatterns);

    assertTrue(domainViolations.isEmpty(), () -> "Domain must not use Spring: " + domainViolations);
    assertTrue(
        applicationViolations.isEmpty(),
        () -> "Application must not use Spring: " + applicationViolations);
  }

  @Test
  void springWiringDoesNotIntroducePrematureDeliveryPersistenceOrSecurityAdapters()
      throws IOException {
    List<String> forbiddenPatterns =
        List.of(
            "@RestController",
            "@Controller",
            "@RequestMapping",
            "@GetMapping",
            "@PostMapping",
            "@Entity",
            "@Table",
            "JpaRepository",
            "CrudRepository",
            "SecurityFilterChain",
            "@EnableWebSecurity");

    List<Path> violations = filesContaining(INFRASTRUCTURE_ROOT, forbiddenPatterns);

    assertTrue(
        violations.isEmpty(),
        () -> "Spring wiring foundation must not introduce premature adapters: " + violations);
  }

  @Test
  void springWiringDocumentationIsPresent() {
    assertTrue(Files.exists(Path.of("docs/32-spring-wiring-foundation.md")));
    assertTrue(Files.exists(Path.of("digitalDocs/index.html")));
    assertFalse(
        Files.exists(INFRASTRUCTURE_ROOT.resolve("jpa")),
        "JPA package must not be introduced by Spring wiring foundation.");
    assertFalse(
        Files.exists(INFRASTRUCTURE_ROOT.resolve("web")),
        "Web delivery package must not be introduced by Spring wiring foundation.");
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
