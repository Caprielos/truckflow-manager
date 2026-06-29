package it.gabriele.truckflow.web.common;

import java.time.LocalDateTime;
import java.util.List;

/** Risposta standard per errori REST. */
public record RestErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path,
    List<String> details) {}
