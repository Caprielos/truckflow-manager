# TruckFlow Manager — Documentazione enterprise e didattica

Questa documentazione è stata riscritta per avere tre letture diverse dello stesso progetto:

1. **Lettura aziendale / cliente**: che problema risolve TruckFlow Manager, a chi serve, quali servizi offre.
2. **Lettura tecnica**: architettura, package, classi, use case, repository e test.
3. **Lettura didattica**: spiegazione molto semplice dei termini Java e del ragionamento tra le classi.

Il progetto attuale contiene:

- **264 classi domain** circa, divise per area aziendale.
- **73 classi application** tra use case, port e classi comuni.
- **46 classi infrastructure/memory** per provare il sistema senza database.
- **94 file di test**.
- Report Maven nel progetto zippato: **823 test, 0 failure, 0 errori, 0 skipped**.

## Da dove iniziare

Se vuoi capire il progetto come portfolio/backend:

1. `docs/START-HERE.md`
2. `docs/client/product-brief.md`
3. `docs/technical/architecture-complete.md`
4. `docs/package-guide/package-map-explained.md`
5. `docs/learning/00-learning-path.md`

Se vuoi imparare il codice:

1. `docs/learning/01-java-words-explained.md`
2. `docs/learning/02-how-to-read-a-class.md`
3. `docs/learning/03-interface-record-final-generics.md`
4. `docs/learning/04-use-case-command-result.md`
5. `docs/learning/05-tests-for-beginners.md`

Se vuoi vedere i diagrammi:

- `docs/diagrams/README.md`
- `docs/diagrams/class-diagram-core.md`
- `docs/diagrams/class-diagram-application.md`
- `docs/diagrams/class-diagram-economics.md`
- `docs/diagrams/class-diagram-parking-facility.md`
- `docs/diagrams/class-diagram-tests.md`

## Comando principale di verifica

```bash
mvn clean test
```

Questo comando compila il progetto ed esegue tutti i test.
