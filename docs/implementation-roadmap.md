# Roadmap implementativa

## Fatto

```text
domain model realistico
application layer
repository port
repository in memoria
test di scenario
```

## Prossimo step consigliato

Il prossimo step naturale è il web layer:

```text
src/main/java/it/gabriele/truckflow/web
```

con controller REST per chiamare gli use case.

## Step dopo

1. REST API.
2. DTO request/response.
3. Infrastructure persistence con database.
4. Autenticazione e autorizzazione reali.
5. Generazione PDF documenti.
6. Import CSV/API reali.
7. Frontend o demo API.

## Nota

Non conviene aggiungere altri 200 enum nel domain senza flussi applicativi. Ora il progetto ha bisogno soprattutto di API e persistenza reale.
