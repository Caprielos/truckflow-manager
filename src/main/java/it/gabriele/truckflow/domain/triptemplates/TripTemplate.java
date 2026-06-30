package it.gabriele.truckflow.domain.triptemplates;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public final class TripTemplate {

  private final TripTemplateId id;
  private final TripTemplateCode code;
  private String name;
  private String description;
  private TripTemplateType type;
  private TripTemplateStatus status;
  private List<TripTemplateSegment> segments;
  private RouteSpecification routeSpecification;
  private String notes;

  public TripTemplate(
      TripTemplateId id,
      TripTemplateCode code,
      String name,
      String description,
      TripTemplateType type,
      TripTemplateStatus status,
      List<TripTemplateSegment> segments,
      RouteSpecification routeSpecification,
      String notes) {
    this.id = id == null ? TripTemplateId.random() : id;
    this.code = TripTemplateValidation.requireNonNull(code, "code");
    this.name = TripTemplateValidation.requireText(name, "name");
    this.description = TripTemplateValidation.normalize(description);
    this.type = TripTemplateValidation.requireNonNull(type, "type");
    this.status = TripTemplateValidation.requireNonNull(status, "status");
    this.segments = validateSegments(segments, status);
    this.routeSpecification =
        routeSpecification == null ? RouteSpecification.empty() : routeSpecification;
    this.notes = TripTemplateValidation.normalize(notes);
  }

  public TripTemplateId id() {
    return id;
  }

  public TripTemplateCode code() {
    return code;
  }

  public String name() {
    return name;
  }

  public String description() {
    return description;
  }

  public TripTemplateType type() {
    return type;
  }

  public TripTemplateStatus status() {
    return status;
  }

  public List<TripTemplateSegment> segments() {
    return List.copyOf(segments);
  }

  public RouteSpecification routeSpecification() {
    return routeSpecification;
  }

  public String notes() {
    return notes;
  }

  public boolean isActive() {
    return status == TripTemplateStatus.ACTIVE;
  }

  public int segmentCount() {
    return segments.size();
  }

  public boolean isContinuous() {
    if (segments.size() < 2) {
      return true;
    }

    for (int index = 0; index < segments.size() - 1; index++) {
      if (!segments.get(index).connectsTo(segments.get(index + 1))) {
        return false;
      }
    }

    return true;
  }

  public void rename(String name, String description) {
    this.name = TripTemplateValidation.requireText(name, "name");
    this.description = TripTemplateValidation.normalize(description);
  }

  public void changeType(TripTemplateType type) {
    this.type = TripTemplateValidation.requireNonNull(type, "type");
  }

  public void replaceSegments(List<TripTemplateSegment> segments) {
    this.segments = validateSegments(segments, status);
  }

  public void replaceRouteSpecification(RouteSpecification routeSpecification) {
    this.routeSpecification =
        routeSpecification == null ? RouteSpecification.empty() : routeSpecification;
  }

  public void updateNotes(String notes) {
    this.notes = TripTemplateValidation.normalize(notes);
  }

  public void activate() {
    status = TripTemplateStatus.ACTIVE;
    segments = validateSegments(segments, status);
  }

  public void suspend() {
    status = TripTemplateStatus.SUSPENDED;
  }

  public void archive() {
    status = TripTemplateStatus.ARCHIVED;
  }

  public void discontinue() {
    status = TripTemplateStatus.DISCONTINUED;
  }

  private static List<TripTemplateSegment> validateSegments(
      List<TripTemplateSegment> segments, TripTemplateStatus status) {
    if (segments == null) {
      segments = List.of();
    }

    TripTemplateValidation.requireNoNullElements(segments, "segments");

    if (status == TripTemplateStatus.ACTIVE && segments.isEmpty()) {
      throw new IllegalArgumentException("Active trip templates must have at least one segment.");
    }

    var sequenceNumbers = new HashSet<Integer>();
    for (TripTemplateSegment segment : segments) {
      if (!sequenceNumbers.add(segment.sequenceNumber())) {
        throw new IllegalArgumentException(
            "Trip template segment sequence numbers must be unique.");
      }
    }

    return segments.stream()
        .sorted(Comparator.comparingInt(TripTemplateSegment::sequenceNumber))
        .toList();
  }
}
