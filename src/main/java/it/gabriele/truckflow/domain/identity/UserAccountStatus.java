package it.gabriele.truckflow.domain.identity;

/** Stato di un account utente. */
public enum UserAccountStatus {
  INVITED(false, false),
  ACTIVE(true, false),
  LOCKED(false, false),
  DISABLED(false, false),
  DELETED(false, true);

  private final boolean canLogin;
  private final boolean terminal;

  UserAccountStatus(boolean canLogin, boolean terminal) {
    this.canLogin = canLogin;
    this.terminal = terminal;
  }

  public boolean canLogin() {
    return canLogin;
  }

  public boolean isTerminal() {
    return terminal;
  }
}
