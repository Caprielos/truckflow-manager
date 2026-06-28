# Package `compliance` — Compliance trasversale

## Scopo

Coordina regole tra autista, mezzo, carico e spedizione.

## Classi ed enum

| Nome | Tipo | Ruolo sintetico |
|---|---|---|
| `ComplianceRules` | Classe | Classe di regole di business del package. |

## Enum principali

In questo package non ci sono enum principali.



## Collegamenti con altri package

Questo package non vive isolato: le sue classi vengono usate dalle regole di business e dai casi d’uso futuri.

Per capire il flusso completo leggere anche:

- [`../domain-overview.md`](../domain-overview.md)
- [`../domain-rules.md`](../domain-rules.md)
- [`../domain-package-map.md`](../domain-package-map.md)

## Test collegati

I test si trovano sotto:

```text
src/test/java/it/gabriele/truckflow/domain/compliance
```

Quando si modifica questo package, eseguire sempre:

```bash
mvn clean test
```
