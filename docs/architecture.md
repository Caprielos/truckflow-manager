# Architettura del progetto

TruckFlow Manager sarà progettato con un'architettura a livelli.

## Livelli principali

- Controller
- Service
- Repository
- Database

## Flusso previsto

Richiesta utente/API
→ Controller
→ Service
→ Repository
→ Database

Questa struttura permette di separare le responsabilità e rendere il codice più ordinato.
