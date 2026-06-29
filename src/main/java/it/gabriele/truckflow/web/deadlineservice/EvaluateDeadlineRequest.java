package it.gabriele.truckflow.web.deadlineservice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/** Richiesta REST per valutare un singolo oggetto rispetto al rule pack attivo. */
public record EvaluateDeadlineRequest(
    @Valid @NotNull DeadlineSubjectRequest subject, LocalDate evaluationDate) {}
