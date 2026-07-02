# 3. Domain Layer spiegato semplice

Il Domain Layer è la parte più importante del progetto.

È come il cervello di TruckFlow Manager: contiene le regole vere del mondo che stiamo modellando.

## Cosa contiene

Nel dominio abbiamo modellato diversi contesti:

- `users`: account applicativi;
- `qualifications`: qualifiche e abilitazioni;
- `operational`: persone operative come driver, mechanic, warehouse operator, dispatcher e manager;
- `vehicles`: veicoli, unità, combinazioni e caratteristiche tecniche;
- `cargo`: merci e requisiti della merce;
- `locations`: luoghi logistici;
- `triptemplates`: percorsi tipo;
- `shipments`: richieste di spedizione;
- `documents`: documenti aziendali come concetto puro;
- `compliance`: requisiti astratti di conformità.

## Cosa non contiene

Il Domain Layer non contiene:

- database;
- Spring;
- controller;
- REST API;
- JSON;
- sicurezza web;
- file system;
- repository reali.

Questo è voluto. Il dominio deve restare pulito.

## Esempio semplice

Una spedizione non è solo una riga in una tabella. È un concetto del business.

Può avere:

- un codice;
- uno stato;
- merci;
- tratte;
- requisiti;
- note;
- proprietà operative.

Il dominio deve proteggere queste regole.

## Perché abbiamo fatto prima il dominio

Se il dominio è confuso, tutto il resto diventa confuso.

API, database e interfacce possono cambiare. Le regole importanti del progetto devono invece essere solide.

Per questo abbiamo completato il dominio prima di costruire application e infrastructure.

## Parole tecniche importanti

Nel dominio trovi termini come aggregate, value object, invariant ed entity.

Detto in modo semplice:

- un **aggregate** è un oggetto importante che protegge regole interne;
- un **value object** è un piccolo oggetto che rappresenta un valore significativo;
- una **invariant** è una regola che deve essere sempre rispettata;
- una **entity** è qualcosa con una identità propria.

Se questi termini sono difficili, puoi consultarli nel [`glossario-semplice.md`](glossario-semplice.md).
