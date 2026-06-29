# 07 - Test di scenario spiegato

Il file `TruckFlowApplicationScenarioTest` mostra come i pezzi lavorano insieme.

## Scenario parcheggio

1. crea repository in memoria;
2. crea un posto parcheggio;
3. salva il posto;
4. crea una risorsa parcheggiata: trattore + semirimorchio;
5. chiama `AssignParkingSpotUseCase`;
6. verifica che il convoglio sia pronto.

## Scenario missione

1. crea ordine accettato;
2. crea spedizione;
3. salva autista, convoglio e route plan;
4. pianifica missione;
5. avanza missione e la chiude;
6. verifica stato `COMPLETED`.

## Scenario economics/payroll

1. crea report lavoro autista;
2. crea policy paghe;
3. calcola payroll;
4. calcola economics missione;
5. verifica che la missione sia profittevole.

## Scenario magazzino

1. crea articolo;
2. crea posizione magazzino;
3. registra acquisto;
4. registra consumo;
5. verifica giacenza e riordino.
