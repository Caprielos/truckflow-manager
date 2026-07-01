package it.gabriele.truckflow.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import it.gabriele.truckflow.application.command.ApplicationCommand;
import it.gabriele.truckflow.application.exception.ApplicationException;
import it.gabriele.truckflow.application.exception.DuplicateResourceException;
import it.gabriele.truckflow.application.exception.ResourceNotFoundException;
import it.gabriele.truckflow.application.exception.UseCaseValidationException;
import it.gabriele.truckflow.application.port.in.UseCase;
import it.gabriele.truckflow.application.result.ApplicationResult;
import org.junit.jupiter.api.Test;

class ApplicationFoundationTest {

  @Test
  void applicationExceptionNormalizesBlankMessageToStableFallback() {
    ApplicationException exception = new ApplicationException("   ");

    assertEquals("Application error", exception.getMessage());
  }

  @Test
  void resourceNotFoundExceptionDescribesMissingResourceWithoutDependingOnInfrastructure() {
    ResourceNotFoundException exception = new ResourceNotFoundException("Shipment", " SH-001 ");

    assertEquals("Shipment", exception.resourceName());
    assertEquals("SH-001", exception.identifier());
    assertEquals("Shipment not found: SH-001", exception.getMessage());
  }

  @Test
  void duplicateResourceExceptionDescribesDuplicatedResourceWithoutDatabaseConcepts() {
    DuplicateResourceException exception = new DuplicateResourceException("Location", " LOC-001 ");

    assertEquals("Location", exception.resourceName());
    assertEquals("LOC-001", exception.identifier());
    assertEquals("Location already exists: LOC-001", exception.getMessage());
  }

  @Test
  void useCaseValidationExceptionRejectsNullAndBlankApplicationInputs() {
    assertThrows(
        UseCaseValidationException.class,
        () -> UseCaseValidationException.requireNonNull(null, "command"));

    assertThrows(
        UseCaseValidationException.class,
        () -> UseCaseValidationException.requireNotBlank("   ", "shipmentCode"));

    assertDoesNotThrow(() -> UseCaseValidationException.requireNonNull(new Object(), "command"));
    assertDoesNotThrow(() -> UseCaseValidationException.requireNotBlank("SH-001", "shipmentCode"));
  }

  @Test
  void genericUseCaseContractExecutesCommandAndReturnsApplicationResult() {
    UseCase<TestCommand, TestResult> useCase =
        command -> new TestResult(command.value().toUpperCase());

    TestResult result = useCase.execute(new TestCommand("truckflow"));

    assertEquals("TRUCKFLOW", result.value());
    assertInstanceOf(ApplicationResult.class, result);
  }

  private record TestCommand(String value) implements ApplicationCommand {}

  private record TestResult(String value) implements ApplicationResult {}
}
