# 11. Cosa abbiamo costruito dal Punto 1 al Punto 7

Il progetto è cresciuto per fasi.

## Punto 1 → Punto 5: Domain Layer

In questa fase abbiamo costruito il dominio puro.

Abbiamo modellato i concetti principali di TruckFlow Manager: utenti, qualifiche, persone operative, veicoli, cargo, locations, trip templates, shipments, documents e compliance.

Abbiamo anche aggiunto regole, eccezioni custom, test e documentazione del dominio.

## Punto 6A → Punto 6M: Application Layer

In questa fase abbiamo costruito il livello applicativo.

Abbiamo aggiunto:

- command;
- result;
- port in;
- port out;
- use case service;
- repository port;
- repository in-memory;
- test di hardening;
- documentazione completa.

Alla fine del Punto 6, i primi use case erano solidi e testati.

## Punto 7A → Punto 7H: Infrastructure Layer

In questa fase abbiamo aggiunto l'infrastruttura tecnica.

Abbiamo introdotto:

- foundation infrastructure;
- eccezioni tecniche;
- Spring wiring non-web;
- mapping blueprint;
- repository file-backed per contesti sicuri;
- test infrastrutturali;
- freeze finale del Punto 7.

## Cosa significa oggi

Oggi il progetto non è ancora una API pubblica, ma ha una base molto solida.

Il cuore business è separato dalla parte applicativa e dalla parte tecnica.

Questo rende il progetto pronto per iniziare il Punto 8.
