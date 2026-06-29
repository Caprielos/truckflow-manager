# Application layer spiegato semplice

L'application layer è il livello che risponde alla domanda:

```text
Che azione reale deve fare il sistema?
```

Il domain sa le regole. L'application decide il flusso.

## Esempio pratico

Per parcheggiare un convoglio già pronto:

1. arriva una richiesta: `AssignParkingSpotUseCase.Command`;
2. lo use case cerca il posto tramite `ParkingSpotRepository`;
3. crea un `ParkingAssignment` usando il domain;
4. salva l'assegnazione tramite `ParkingAssignmentRepository`;
5. restituisce l'assegnazione creata.

## Cartelle

```text
application/common
application/port/in
application/port/out
application/usecase
```

### common

Contiene classi comuni come:

```text
ApplicationResult
ApplicationError
ResourceNotFoundException
```

### port/in

Contiene le azioni che il sistema offre.

Esempio:

```text
AssignParkingSpotUseCase
PlanTransportMissionUseCase
```

### port/out

Contiene le dipendenze che l'application richiede.

Esempio:

```text
ParkingSpotRepository
TransportMissionRepository
```

### usecase

Contiene le implementazioni concrete.

Esempio:

```text
DefaultAssignParkingSpotUseCase
DefaultPlanTransportMissionUseCase
```

## Cosa non deve fare l'application

L'application non deve diventare un secondo domain.

Non deve contenere regole pesanti tipo:

```text
un autista CE può guidare questo convoglio?
la merce ADR richiede certificato?
la missione è profittevole?
```

Quelle regole stanno nel domain.

L'application deve orchestrare:

```text
carica dati
chiama regole domain
salva risultato
restituisce output
```
