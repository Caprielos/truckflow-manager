package it.gabriele.truckflow.application.usecase;

import it.gabriele.truckflow.application.common.ApplicationError;
import it.gabriele.truckflow.application.common.ApplicationResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationResultTest {

    @Test
    void shouldRepresentSuccessfulApplicationResult() {
        ApplicationResult<String> result = ApplicationResult.success("ok");

        assertTrue(result.isSuccess());
        assertFalse(result.isFailure());
        assertEquals("ok", result.getValueOrThrow());
    }

    @Test
    void shouldRepresentFailedApplicationResult() {
        ApplicationResult<String> result = ApplicationResult.failure(ApplicationError.of("VALIDATION", "Dato non valido"));

        assertFalse(result.isSuccess());
        assertTrue(result.isFailure());
        assertEquals("VALIDATION", result.getErrors().getFirst().code());
        assertThrows(IllegalStateException.class, result::getValueOrThrow);
    }
}
