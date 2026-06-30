package it.gabriele.truckflow.domain.abilitazioni;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class CatalogoAbilitazioni {

  private CatalogoAbilitazioni() {}

  public static List<AbilitazioneCatalogo> tutte() {
    return List.of(AbilitazioneCatalogo.values());
  }

  public static List<AbilitazioneCatalogo> perCategoria(CategoriaAbilitazione categoria) {
    return Arrays.stream(AbilitazioneCatalogo.values())
        .filter(abilitazione -> abilitazione.categoria() == categoria)
        .toList();
  }

  public static Optional<AbilitazioneCatalogo> trovaPerCodice(String codice) {
    return Arrays.stream(AbilitazioneCatalogo.values())
        .filter(abilitazione -> abilitazione.codice().equals(codice))
        .findFirst();
  }
}
