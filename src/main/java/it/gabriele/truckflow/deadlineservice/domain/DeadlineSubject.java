package it.gabriele.truckflow.deadlineservice.domain;

import java.util.Map;
import java.util.Set;

/**
 * Descrizione neutra dell'oggetto da valutare: contiene solo riferimenti, elementi e fatti, non
 * classi del dominio principale.
 */
public record DeadlineSubject(
    DeadlineObjectRef objectRef,
    String configuredCountry,
    String manufacturer,
    String model,
    Set<ManagedElementCode> elements,
    Map<String, String> facts) {

  public DeadlineSubject {
    if (objectRef == null) {
      throw new IllegalArgumentException("Il riferimento oggetto è obbligatorio.");
    }
    configuredCountry = normalize(configuredCountry);
    manufacturer = normalize(manufacturer);
    model = normalize(model);
    elements = elements == null ? Set.of() : Set.copyOf(elements);
    facts = facts == null ? Map.of() : Map.copyOf(facts);
  }

  public boolean hasElement(ManagedElementCode code) {
    return elements.contains(code);
  }

  public boolean hasFact(String key) {
    return key != null && facts.containsKey(key);
  }

  public String fact(String key) {
    return facts.get(key);
  }

  private static String normalize(String value) {
    return value == null ? "" : value.strip();
  }
}
