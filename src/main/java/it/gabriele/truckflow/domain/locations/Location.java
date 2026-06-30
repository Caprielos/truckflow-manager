package it.gabriele.truckflow.domain.locations;

public final class Location {

  private final LocationId id;
  private final LocationCode code;
  private String name;
  private LocationType type;
  private LocationStatus status;
  private LocationAddress address;
  private GeoCoordinates coordinates;
  private String notes;

  public Location(
      LocationId id,
      LocationCode code,
      String name,
      LocationType type,
      LocationStatus status,
      LocationAddress address,
      GeoCoordinates coordinates,
      String notes) {
    this.id = id == null ? LocationId.random() : id;
    this.code = LocationValidation.requireNonNull(code, "code");
    this.name = LocationValidation.requireText(name, "name");
    this.type = LocationValidation.requireNonNull(type, "type");
    this.status = LocationValidation.requireNonNull(status, "status");
    this.address = address == null ? LocationAddress.empty() : address;
    this.coordinates = coordinates;
    this.notes = LocationValidation.normalize(notes);
  }

  public LocationId id() {
    return id;
  }

  public LocationCode code() {
    return code;
  }

  public String name() {
    return name;
  }

  public LocationType type() {
    return type;
  }

  public LocationStatus status() {
    return status;
  }

  public LocationAddress address() {
    return address;
  }

  public GeoCoordinates coordinates() {
    return coordinates;
  }

  public String notes() {
    return notes;
  }

  public boolean isActive() {
    return status == LocationStatus.ACTIVE;
  }

  public boolean isYard() {
    return type == LocationType.YARD;
  }

  public void rename(String name) {
    this.name = LocationValidation.requireText(name, "name");
  }

  public void changeType(LocationType type) {
    this.type = LocationValidation.requireNonNull(type, "type");
  }

  public void replaceAddress(LocationAddress address) {
    this.address = address == null ? LocationAddress.empty() : address;
  }

  public void replaceCoordinates(GeoCoordinates coordinates) {
    this.coordinates = coordinates;
  }

  public void updateNotes(String notes) {
    this.notes = LocationValidation.normalize(notes);
  }

  public void activate() {
    status = LocationStatus.ACTIVE;
  }

  public void suspend() {
    status = LocationStatus.SUSPENDED;
  }

  public void archive() {
    status = LocationStatus.ARCHIVED;
  }

  public void discontinue() {
    status = LocationStatus.DISCONTINUED;
  }
}
