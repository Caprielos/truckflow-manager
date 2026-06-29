package it.gabriele.truckflow.web.deadlineservice;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

/** Richiesta REST per valutare più oggetti nello stesso momento logico. */
public record EvaluateDeadlineBatchRequest(
    @Valid @NotEmpty List<DeadlineSubjectRequest> subjects, LocalDate evaluationDate) {}
