package it.gabriele.truckflow.domain.operational.dispatcher;

import it.gabriele.truckflow.domain.operational.common.OperationalCode;
import it.gabriele.truckflow.domain.operational.common.OperationalMetadata;
import it.gabriele.truckflow.domain.operational.common.OperationalProfile;
import it.gabriele.truckflow.domain.operational.common.OperationalScope;
import it.gabriele.truckflow.domain.operational.common.OperationalScopeCode;
import it.gabriele.truckflow.domain.operational.common.OperationalStatus;
import it.gabriele.truckflow.domain.operational.exceptions.InvalidDispatcherException;
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
    this.code = requireNonNull(code, "code");
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
    OperationalScopeCode normalizedCode = OperationalScopeCode.of(code);
    return scopes.stream().anyMatch(scope -> scope.code().equals(normalizedCode));
  }

  public void addScope(OperationalScope scope, String updatedBy) {
    requireNonNull(scope, "scope");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    var updatedScopes = new HashSet<>(scopes);
    updatedScopes.add(scope);
    Set<OperationalScope> validatedScopes = validateScopes(updatedScopes);

    this.scopes = validatedScopes;
    touch(validatedUpdatedBy);
  }

  public void removeScope(OperationalScope scope, String updatedBy) {
    requireNonNull(scope, "scope");
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");

    var updatedScopes = new HashSet<>(scopes);
    updatedScopes.remove(scope);
    Set<OperationalScope> validatedScopes = validateScopes(updatedScopes);
    ensureActiveHasScopes(status, validatedScopes);

    this.scopes = validatedScopes;
    touch(validatedUpdatedBy);
  }

  public void activate(String updatedBy) {
    String validatedUpdatedBy = requireText(updatedBy, "updatedBy");
    ensureActiveHasScopes(OperationalStatus.ACTIVE, scopes);

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

  private void ensureActiveHasScopes() {
    ensureActiveHasScopes(status, scopes);
  }

  private static void ensureActiveHasScopes(
      OperationalStatus status, Set<OperationalScope> scopes) {
    if (status == OperationalStatus.ACTIVE && scopes.isEmpty()) {
      throw new InvalidDispatcherException("An active dispatcher must have at least one scope.");
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
      throw new InvalidDispatcherException("Scopes cannot contain null values.");
    }

    return Set.copyOf(scopes);
  }

  private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
      throw new InvalidDispatcherException(fieldName + " is required.");
    }

    return value;
  }

  private static String requireText(String value, String fieldName) {
    String normalized = normalize(value);

    if (normalized.isBlank()) {
      throw new InvalidDispatcherException(fieldName + " is required.");
    }

    return normalized;
  }

  private static String normalize(String value) {
    return value == null ? "" : value.trim();
  }
}
