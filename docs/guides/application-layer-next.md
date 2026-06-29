# Application layer next

## Perché serve

Ora il domain è ricco. Per trasformare il progetto in applicazione vera serve un layer che esegua casi d'uso.

Il domain sa calcolare e validare. L'application layer coordina.

## Esempio di use case

```text
PlanTransportMissionUseCase
```

Dovrebbe:

1. caricare ordine/spedizione;
2. caricare cargo;
3. caricare mezzi disponibili;
4. caricare autisti disponibili;
5. controllare compliance;
6. valutare dispatch candidates;
7. stimare economics e payroll;
8. scegliere o proporre candidati;
9. salvare la missione pianificata.

## Package consigliati

```text
application/usecase
application/ports
application/service
```

## Cosa non fare

Non mettere database o controller dentro domain.

Non mettere regole business nei controller REST.

Non far dipendere il domain da Spring.
