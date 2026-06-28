# Documentazione TruckFlow Manager

Questa cartella contiene la documentazione completa del dominio **TruckFlow Manager**.

La documentazione è stata riscritta sulla versione aggiornata del progetto, dove il vecchio package `shipment` fuori da `domain` è stato rimosso e il dominio è organizzato attorno a package coerenti sotto:

```text
it.gabriele.truckflow.domain
```

## Documenti principali

- `project-overview.md`: visione generale del progetto.
- `architecture.md`: architettura e separazione dei layer.
- `current-version-notes.md`: cosa è cambiato nella versione attuale.
- `domain-overview.md`: modello di dominio complessivo.
- `domain-package-map.md`: mappa dei package.
- `domain-rules.md`: regole principali.
- `domain-reference-complete.md`: catalogo tecnico completo.
- `testing-guide.md`: guida ai test.
- `implementation-roadmap.md`: prossimi step realistici.

## Guide consigliate

- `guides/shipment-vs-mission.md`
- `guides/realistic-fleet-model.md`
- `guides/operational-modules.md`
- `guides/next-application-layer.md`

## Package documentati

Ogni package del dominio ha un file dedicato dentro `packages/`.
