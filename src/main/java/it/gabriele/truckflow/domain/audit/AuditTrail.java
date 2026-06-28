package it.gabriele.truckflow.domain.audit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Sequenza ordinata di eventi audit per uno stesso aggregato.
 */
public final class AuditTrail {

    private final List<AuditEvent> events;

    private AuditTrail(List<AuditEvent> events) {
        if (events == null) {
            throw new IllegalArgumentException("La lista eventi audit è obbligatoria.");
        }

        if (events.isEmpty()) {
            throw new IllegalArgumentException("L'audit trail deve contenere almeno un evento.");
        }

        if (events.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("La lista eventi audit non può contenere valori nulli.");
        }

        validateSameAggregate(events);
        validateUniqueEventIds(events);

        this.events = events.stream()
                .sorted(Comparator.comparing(AuditEvent::getOccurredAt))
                .toList();
    }

    public static AuditTrail of(List<AuditEvent> events) {
        return new AuditTrail(events);
    }

    public static AuditTrail of(AuditEvent firstEvent, AuditEvent... otherEvents) {
        if (firstEvent == null) {
            throw new IllegalArgumentException("Il primo evento audit è obbligatorio.");
        }

        List<AuditEvent> events = new ArrayList<>();
        events.add(firstEvent);

        if (otherEvents != null) {
            for (AuditEvent event : otherEvents) {
                events.add(event);
            }
        }

        return new AuditTrail(events);
    }

    private static void validateSameAggregate(List<AuditEvent> events) {
        AuditEvent first = events.get(0);

        boolean allSameAggregate = events.stream()
                .allMatch(first::isSameAggregate);

        if (!allSameAggregate) {
            throw new IllegalArgumentException("Tutti gli eventi audit devono appartenere allo stesso aggregato.");
        }
    }

    private static void validateUniqueEventIds(List<AuditEvent> events) {
        long uniqueEventIds = events.stream()
                .map(AuditEvent::getEventId)
                .distinct()
                .count();

        if (uniqueEventIds != events.size()) {
            throw new IllegalArgumentException("L'audit trail non può contenere eventi duplicati.");
        }
    }

    public List<AuditEvent> getEvents() {
        return events;
    }

    public int getEventCount() {
        return events.size();
    }

    public String getAggregateType() {
        return events.get(0).getAggregateType();
    }

    public String getAggregateId() {
        return events.get(0).getAggregateId();
    }

    public AuditEvent getFirstEvent() {
        return events.get(0);
    }

    public AuditEvent getLatestEvent() {
        return events.get(events.size() - 1);
    }

    public boolean containsEventId(String eventId) {
        if (eventId == null || eventId.trim().isEmpty()) {
            throw new IllegalArgumentException("L'id evento audit da cercare è obbligatorio.");
        }

        String normalizedEventId = eventId.trim().toUpperCase();

        return events.stream()
                .anyMatch(event -> event.getEventId().equals(normalizedEventId));
    }

    public boolean hasActionType(AuditActionType actionType) {
        if (actionType == null) {
            throw new IllegalArgumentException("Il tipo azione audit da cercare è obbligatorio.");
        }

        return events.stream()
                .anyMatch(event -> event.getActionType() == actionType);
    }

    public boolean hasSecuritySensitiveEvents() {
        return events.stream().anyMatch(AuditEvent::isSecuritySensitive);
    }

    public boolean hasFinancialImpactEvents() {
        return events.stream().anyMatch(AuditEvent::hasFinancialImpact);
    }

    public boolean hasReviewRequiredEvents() {
        return events.stream().anyMatch(AuditEvent::requiresReview);
    }

    public List<AuditEvent> getEventsByActionType(AuditActionType actionType) {
        if (actionType == null) {
            throw new IllegalArgumentException("Il tipo azione audit da filtrare è obbligatorio.");
        }

        return events.stream()
                .filter(event -> event.getActionType() == actionType)
                .toList();
    }

    public String formatSingleLine() {
        return getAggregateType()
                + ":" + getAggregateId()
                + " - events: " + events.size()
                + " - latest: " + getLatestEvent().getActionType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditTrail that)) return false;
        return events.equals(that.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events);
    }

    @Override
    public String toString() {
        return formatSingleLine();
    }
}
