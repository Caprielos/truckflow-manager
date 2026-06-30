package it.gabriele.truckflow.domain.operational.mechanic;

import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalMetadata;
import it.gabriele.truckflow.domain.operational.common.OperationalProfile;
import it.gabriele.truckflow.domain.operational.common.OperationalQualification;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.qualifications.Qualification;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.HashSet;
import java.util.Set;

public final class Mechanic {

  private final MechanicId id;
  private final OperationalCode code;
  private final UserId userId;
  private OperationalProfile profile;
  private Set<OperationalQualification> qualifications;
  private OperationalStatus status;
  private OperationalMetadata metadata;
  private String notes;

  public Mechanic(
      MechanicId id,
      OperationalCode code,
      UserId userId,
      OperationalProfile profile,
      Set<OperationalQualification> qualifications,
      OperationalStatus status,
      OperationalMetadata metadata,
      String notes) {
    this.id = id == null ? MechanicId.random() : id;
    this.code = code == null ? OperationalCode.empty() : code;
    this.userId = requireNonNull(userId, "userId");
    this.profile = requireNonNull(profile, "profile");
    this.qualifications = validateQualifications(qualifications);
    this.status = requireNonNull(status, "status");
    this.metadata = requireNonNull(metadata, "metadata");
    this.notes = normalize(notes);

    ensureActiveHasQualifications();
  }

  public MechanicId id() {
    return id;
  }

  public OperationalCode code() {
    return code;
  }

  public UserId userId() {
    return userId;
  }

  public OperationalProfile profile() {
    return profile;
  }

  public Set<OperationalQualification> qualifications() {
    return Set.copyOf(qualifications);
  }

  public OperationalStatus status() {
    return status;
  }

  public OperationalMetadata metadata() {
    return metadata;
  }

  public String notes() {
    return notes;
  }

  public boolean isActive() {
    return status == OperationalStatus.ACTIVE;
  }

  public boolean hasQualification(Qualification qualification) {
    requireNonNull(qualification, "qualification");
    return qualifications.stream()
        .anyMatch(
            operationalQualification -> operationalQualification.qualification() == qualification);
  }

  public void addQualification(OperationalQualification qualification, String updatedBy) {
    requireNonNull(qualification, "qualification");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    var updatedQualifications = new HashSet<>(qualifications);
    updatedQualifications.add(qualification);
    Set<OperationalQualification> validatedQualifications =
        validateQualifications(updatedQualifications);

    this.qualifications = validatedQualifications;
    touch(validatedUpdatedBy);
  }

  public void removeQualification(OperationalQualification qualification, String updatedBy) {
    requireNonNull(qualification, "qualification");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    var updatedQualifications = new HashSet<>(qualifications);
    updatedQualifications.remove(qualification);
    Set<OperationalQualification> validatedQualifications =
        validateQualifications(updatedQualifications);
    ensureActiveHasQualifications(status, validatedQualifications);

    this.qualifications = validatedQualifications;
    touch(validatedUpdatedBy);
  }

  public void activate(String updatedBy) {
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");
    ensureActiveHasQualifications(OperationalStatus.ACTIVE, qualifications);

    this.status = OperationalStatus.ACTIVE;
    touch(validatedUpdatedBy);
  }

  public void suspend(String updatedBy) {
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.status = OperationalStatus.SUSPENDED;
    touch(validatedUpdatedBy);
  }

  public void markNotEligible(String updatedBy) {
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.status = OperationalStatus.NOT_ELIGIBLE;
    touch(validatedUpdatedBy);
  }

  public void updateProfile(OperationalProfile profile, String updatedBy) {
    OperationalProfile updatedProfile = requireNonNull(profile, "profile");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.profile = updatedProfile;
    touch(validatedUpdatedBy);
  }

  public void updateNotes(String notes, String updatedBy) {
    String updatedNotes = normalize(notes);
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    this.notes = updatedNotes;
    touch(validatedUpdatedBy);
  }

  private void ensureActiveHasQualifications() {
    ensureActiveHasQualifications(status, qualifications);
  }

  private static void ensureActiveHasQualifications(
      OperationalStatus status, Set<OperationalQualification> qualifications) {
    if (status == OperationalStatus.ACTIVE && qualifications.isEmpty()) {
      throw new IllegalStateException("An active mechanic must have at least one qualification.");
    }
  }

  private void touch(String updatedBy) {
    metadata = metadata.updatedNow(requireText(updatedBy, "updatedBy"));
  }

  private static Set<OperationalQualification> validateQualifications(
      Set<OperationalQualification> qualifications) {
    if (qualifications == null) {
      return Set.of();
    }

    if (qualifications.stream().anyMatch(qualification -> qualification == null)) {
      throw new IllegalArgumentException("Qualifications cannot contain null values.");
    }

    return Set.copyOf(qualifications);
  }

  private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return value;
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new IllegalArgumentException(fieldName + " is required.");
    }

    return normalized;
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
