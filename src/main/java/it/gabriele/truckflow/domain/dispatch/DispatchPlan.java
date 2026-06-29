package it.gabriele.truckflow.domain.dispatch;

import it.gabriele.truckflow.domain.shared.Notes;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Piano dispatch giornaliero o operativo con più candidati di assegnazione.
 */
public final class DispatchPlan {

    private static final int MAX_CODE_LENGTH = 50;

    private final String planCode;
    private final LocalDate planningDate;
    private final List<DispatchAssignmentCandidate> candidates;
    private final Notes notes;

    private DispatchPlan(String planCode, LocalDate planningDate, List<DispatchAssignmentCandidate> candidates, Notes notes) {
        this.planCode = validateCode(planCode);
        if (planningDate == null) {
            throw new IllegalArgumentException("La data piano dispatch è obbligatoria.");
        }
        this.candidates = validateCandidates(candidates);
        if (notes == null) {
            throw new IllegalArgumentException("Le note piano dispatch sono obbligatorie.");
        }
        this.planningDate = planningDate;
        this.notes = notes;
    }

    public static DispatchPlan of(String planCode, LocalDate planningDate, List<DispatchAssignmentCandidate> candidates, Notes notes) {
        return new DispatchPlan(planCode, planningDate, candidates, notes);
    }

    private static String validateCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Il codice piano dispatch è obbligatorio.");
        }
        String normalized = code.trim().toUpperCase();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Il codice piano dispatch non può essere vuoto.");
        }
        if (normalized.length() > MAX_CODE_LENGTH) {
            throw new IllegalArgumentException("Il codice piano dispatch non può superare " + MAX_CODE_LENGTH + " caratteri.");
        }
        if (!normalized.matches("[A-Z0-9_-]+")) {
            throw new IllegalArgumentException("Il codice piano dispatch può contenere solo lettere, numeri, trattini e underscore.");
        }
        return normalized;
    }

    private static List<DispatchAssignmentCandidate> validateCandidates(List<DispatchAssignmentCandidate> candidates) {
        if (candidates == null) {
            throw new IllegalArgumentException("I candidati piano dispatch sono obbligatori.");
        }
        if (candidates.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("I candidati piano dispatch non possono contenere null.");
        }
        long uniqueCodes = candidates.stream().map(DispatchAssignmentCandidate::getCandidateCode).distinct().count();
        if (uniqueCodes != candidates.size()) {
            throw new IllegalArgumentException("Il piano dispatch non può avere candidati duplicati.");
        }
        return List.copyOf(candidates);
    }

    public String getPlanCode() { return planCode; }
    public LocalDate getPlanningDate() { return planningDate; }
    public List<DispatchAssignmentCandidate> getCandidates() { return candidates; }
    public Notes getNotes() { return notes; }

    public List<DispatchAssignmentCandidate> getAssignableCandidates() {
        return candidates.stream().filter(DispatchAssignmentCandidate::isAssignable).toList();
    }

    public List<DispatchAssignmentCandidate> getBlockedCandidates() {
        return candidates.stream().filter(DispatchAssignmentCandidate::hasBlockingIssue).toList();
    }

    public Optional<DispatchAssignmentCandidate> chooseBestAssignableByMargin() {
        return getAssignableCandidates().stream()
                .max(Comparator.comparing(DispatchAssignmentCandidate::calculateGrossMarginAmount));
    }
}
