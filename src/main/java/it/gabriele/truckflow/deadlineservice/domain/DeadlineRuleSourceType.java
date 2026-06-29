package it.gabriele.truckflow.deadlineservice.domain;

/** Fonte da cui può derivare una scadenza, un controllo o un obbligo operativo. */
public enum DeadlineRuleSourceType {
  EU_LAW,
  NATIONAL_LAW,
  MANUFACTURER_RULEBOOK,
  INTERNAL_OPERATIONAL_POLICY,
  CUSTOMER_CONTRACT,
  TELEMATICS_EVENT,
  SECURITY_EVENT
}
