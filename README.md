# TruckFlow Manager — Documentazione

Questa cartella contiene la documentazione aggiornata del progetto **TruckFlow Manager**.

La documentazione è stata riscritta per descrivere il dominio attuale dopo il refactor realistico della flotta, dei mezzi, delle merci, degli autisti, della compliance e dei moduli operativi.

## Come leggere questi documenti

1. Parti da `project-overview.md`.
2. Leggi `architecture.md` per capire la separazione tra domain, application, infrastructure e web.
3. Leggi `domain-overview.md` per capire il ragionamento generale del dominio.
4. Usa `domain-package-map.md` per navigare i package.
5. Apri `packages/*.md` quando vuoi capire un package specifico.
6. Usa `domain-reference-complete.md` come catalogo tecnico completo.

## Struttura

```text
docs/
├── README.md
├── project-overview.md
├── architecture.md
├── domain-overview.md
├── domain-package-map.md
├── domain-rules.md
├── glossary.md
├── testing-guide.md
├── implementation-roadmap.md
├── domain-reference-complete.md
├── packages/
└── architecture-decisions/
```

## Principio base

Il dominio deve rappresentare la realtà del trasporto, ma senza diventare dipendente da database, Spring, API esterne, frontend o file system.

Per questo il progetto usa:

- Entity per oggetti con identità.
- Value Object per dati immutabili e validati.
- Enum per liste chiuse di valori.
- Rules per regole di business pure.
- Application layer futuro per i casi d’uso.
- Infrastructure futura per database, file, API esterne e integrazioni.

## Stato attuale

Il progetto contiene un dominio ampio e già molto realistico:

- ordini, spedizioni e missioni;
- merci e profili ADR;
- mezzi, allestimenti, schede tecniche e convogli;
- autisti, patenti, CQC, ADR e certificati;
- aziende e licenze operative;
- documenti obbligatori;
- disponibilità;
- manutenzione;
- pneumatici;
- carburante;
- telematica;
- tracking;
- pricing;
- billing;
- sostenibilità;
- notifiche;
- audit;
- reporting.

Il prossimo grande passo architetturale sarà costruire l’**application layer** sopra questo domain.
