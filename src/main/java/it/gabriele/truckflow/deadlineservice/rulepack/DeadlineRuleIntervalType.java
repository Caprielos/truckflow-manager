package it.gabriele.truckflow.deadlineservice.rulepack;

/** Tipo di intervallo usato da una regola compilata del rule pack. */
public enum DeadlineRuleIntervalType {
  NOT_CONFIGURED,
  DATE_BASED,
  DISTANCE_BASED,
  DATE_OR_DISTANCE,
  CONTINUOUS_EVENT
}
