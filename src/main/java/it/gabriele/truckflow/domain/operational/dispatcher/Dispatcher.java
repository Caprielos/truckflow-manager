package it.gabriele.truckflow.domain.operational.dispatcher;

import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalMetadata;
import it.gabriele.truckflow.domain.operational.common.OperationalProfile;
import it.gabriele.truckflow.domain.operational.common.OperationalScope;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.users.UserId;
import java.util.HashSet;
import java.util.Set;

public final class Dispatcher {

  private final DispatcherId id;
  private final OperationalCode code;
  private final UserId userId;
  private OperationalProfile profile;
  private Set<OperationalScope> scopes;
  private OperationalStatus status;
  private OperationalMetadata metadata;
  private String notes;

  public Dispatcher(
      DispatcherId id,
      OperationalCode code,
      UserId userId,
      OperationalProfile profile,
      Set<OperationalScope> scopes,
      OperationalStatus status,
      OperationalMetadata metadata,
      String notes) {
    this.id = id == null ? DispatcherId.random() : id;
    this.code = code == null ? OperationalCode.empty() : code;
    this.userId = requireNonNull(userId, "userId");
    this.profile = requireNonNull(profile, "profile");
    this.scopes = validateScopes(scopes);
    this.status = requireNonNull(status, "status");
    this.metadata = requireNonNull(metadata, "metadata");
    this.notes = normalize(notes);

    ensureActiveHasScopes();
  }

  public DispatcherId id() {
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

  public Set<OperationalScope> scopes() {
    return Set.copyOf(scopes);
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

  public boolean hasScope(String code) {
    String normalizedCode = requireText(code, "code").toUpperCase();
    return scopes.stream().anyMatch(scope -> scope.code().equals(normalizedCode));
  }

  public void addScope(OperationalScope scope, String updatedBy) {
    requireNonNull(scope, "scope");

    var updatedScopes = new HashSet<>(scopes);
    updatedScopes.add(scope);
    scopes = validateScopes(updatedScopes);
    touch(updatedBy);
  }

  public void removeScope(OperationalScope scope, String updatedBy) {
    requireNonNull(scope, "scope");

    var updatedScopes = new HashSet<>(scopes);
    updatedScopes.remove(scope);
    scopes = validateScopes(updatedScopes);
    ensureActiveHasScopes();
    touch(updatedBy);
  }

  public void activate(String updatedBy) {
    status = OperationalStatus.ACTIVE;
    ensureActiveHasScopes();
    touch(updatedBy);
  }

  public void suspend(String updatedBy) {
    status = OperationalStatus.SUSPENDED;
    touch(updatedBy);
  }

  public void markNotEligible(String updatedBy) {
    status = OperationalStatus.NOT_ELIGIBLE;
    touch(updatedBy);
  }

  public void updateProfile(OperationalProfile profile, String updatedBy) {
    this.profile = requireNonNull(profile, "profile");
    touch(updatedBy);
  }

  public void updateNotes(String notes, String updatedBy) {
    this.notes = normalize(notes);
    touch(updatedBy);
  }

  private void ensureActiveHasScopes() {
    if (status == OperationalStatus.ACTIVE && scopes.isEmpty()) {
      throw new IllegalStateException("An active dispatcher must have at least one scope.");
    }
  }

  private void touch(String updatedBy) {
    metadata = metadata.updatedNow(requireText(updatedBy, "updatedBy"));
  }

  private static Set<OperationalScope> validateScopes(Set<OperationalScope> scopes) {
    if (scopes == null) {
      return Set.of();
    }

    if (scopes.stream().anyMatch(scope -> scope == null)) {
      throw new IllegalArgumentException("Scopes cannot contain null values.");
    }

    return Set.copyOf(scopes);
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
