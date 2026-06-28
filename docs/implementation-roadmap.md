# Implementation roadmap

## Stato attuale

Il domain è già molto ampio e copre la maggior parte dei concetti di un gestionale logistico/flotta.

Sono presenti:

- ordini, spedizioni e missioni;
- merci e ADR;
- flotta, veicoli, convogli, schede tecniche;
- autisti, patenti e abilitazioni;
- company licenses;
- documenti;
- manutenzione;
- pneumatici;
- carburante;
- telematica;
- load security;
- pricing e billing;
- audit, notification, reporting, identity e configuration.

## Prossimo passo consigliato

Creare l’application layer.

Esempio primo use case:

```text
CreateTransportOrderUseCase
CreateTransportOrderCommand
TransportOrderRepository
InMemoryTransportOrderRepository
CreateTransportOrderUseCaseTest
```

## Fasi successive

### 1. Application layer

Casi d’uso e repository port.

### 2. Infrastructure in-memory

Repository in RAM per test e prototipo.

### 3. Database

Repository JPA o altro storage persistente.

### 4. Web/API

Controller REST e interfaccia utente.

### 5. Integrazioni esterne

GPS, mappe, pedaggi, carte carburante, provider documentali.

## Regola di lavoro

Dopo ogni modifica importante:

```bash
mvn clean test
```

Poi commit e push solo se i test sono verdi.
