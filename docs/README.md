# TruckFlow Manager — Documentazione del dominio

Questa cartella contiene la documentazione del dominio puro di TruckFlow Manager.

Il progetto, allo stato attuale, è concentrato sul **domain layer** e contiene i seguenti package principali:

- `domain.users`
- `domain.qualifications`
- `domain.operational`
- `domain.vehicles`

L’obiettivo di questa documentazione è spiegare in italiano, in modo chiaro e professionale, **perché il dominio è stato modellato così**, quali scelte sono state fatte e cosa ogni package rappresenta.

## Indice consigliato

1. [`01-project-overview.md`](01-project-overview.md) — visione generale del progetto e principi architetturali.
2. [`02-domain-users.md`](02-domain-users.md) — documentazione completa del dominio utenti.
3. [`03-domain-qualifications.md`](03-domain-qualifications.md) — documentazione completa del catalogo abilitazioni.
4. [`04-domain-operational.md`](04-domain-operational.md) — documentazione completa delle figure operative aziendali.
5. [`05-domain-vehicles.md`](05-domain-vehicles.md) — documentazione completa del dominio veicoli e combinazioni.
6. [`06-architecture-decisions.md`](06-architecture-decisions.md) — decisioni architetturali e regole generali del dominio puro.

## Stato del progetto documentato

Questa documentazione descrive la versione del progetto in cui il dominio contiene:

- account applicativi e autorizzazioni di base;
- catalogo statico delle qualificazioni e abilitazioni;
- figure operative reali dell’azienda;
- unità veicolo, allestimenti, combinazioni, schede tecniche, capacità, agganci e ruoli operativi dei mezzi, con `domain.vehicles` organizzato in sottopackage (`unit`, `combination`, `coupling`, `specification`, `body`, `operation`, `common`).


## Nota sul packaging di `domain.vehicles`

Il dominio veicoli è stato riorganizzato in sottopackage per renderlo più leggibile:

- `domain.vehicles.unit` per le unità fisiche;
- `domain.vehicles.combination` per bilici, autotreni e mezzi singoli operativi;
- `domain.vehicles.coupling` per agganci e traino;
- `domain.vehicles.specification` per le schede tecniche;
- `domain.vehicles.body` per allestimenti e profili di allestimento;
- `domain.vehicles.operation` per capacità e ruoli operativi;
- `domain.vehicles.common` per validazioni condivise.

Questa divisione non è per tipo di mezzo, ma per concetto di dominio. È stata scelta perché molte classi sono condivise tra camion, trattori, rimorchi, semirimorchi e mezzi di magazzino.

## Cosa significa “domain puro”

Nel progetto TruckFlow, “domain puro” significa che le classi di dominio descrivono il business e le sue regole essenziali, senza dipendere da framework o infrastruttura.

Quindi il dominio non contiene:

- controller REST;
- database;
- JPA;
- Spring;
- JWT;
- microservizi;
- chiamate HTTP;
- disponibilità giornaliera;
- pianificazione dei viaggi;
- documenti PDF;
- scadenze amministrative;
- GPS o telematica.

Tutte queste parti potranno essere aggiunte in futuro in layer o moduli separati, senza sporcare il dominio.
