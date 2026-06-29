# Scenario reale: da dati finti a flusso applicativo

Questa guida spiega perché abbiamo aggiunto infrastructure/memory e test di scenario.

## Obiettivo

Vogliamo rispondere a questa domanda:

```text
Il progetto funziona come sistema, non solo come singole classi?
```

## Cosa facciamo nel test

1. Creiamo repository in memoria.
2. Creiamo dati realistici.
3. Salviamo dati nei repository.
4. Chiamiamo use case.
5. Verifichiamo risultati.

## Perché non usare un main?

Un `main` serve per avviare un programma. Un test di scenario invece serve per verificare automaticamente il comportamento.

Con il test puoi lanciare sempre:

```bash
mvn clean test
```

## Esempio parcheggio

```text
creo ParkingSpot A12
creo ParkedResource trattore + semirimorchio
chiamo AssignParkingSpotUseCase
controllo che ParkingAssignment sia attiva
```

## Esempio missione

```text
creo ordine
creo spedizione
creo driver
creo convoglio
creo route plan
chiamo PlanTransportMissionUseCase
controllo che missione sia pianificata
```

## Esempio economics/payroll

```text
creo righe ricavo
creo righe costo
calcolo marginalità
calcolo costo autista
verifico utile/perdita
```
