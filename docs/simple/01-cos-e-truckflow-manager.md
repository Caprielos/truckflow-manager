# 1. Cos'è TruckFlow Manager

TruckFlow Manager è un progetto Java pensato per simulare, in modo realistico, una piattaforma per aziende di trasporto e logistica.

L'idea non è fare un programma piccolo che salva due dati, ma costruire passo dopo passo la base di un sistema più serio, simile a quelli usati nelle aziende.

## Che tipo di problemi vuole gestire

TruckFlow Manager vuole rappresentare concetti come:

- clienti e spedizioni;
- merci da trasportare;
- luoghi di carico e scarico;
- veicoli e combinazioni di veicoli;
- documenti;
- requisiti di conformità;
- persone operative come autisti, meccanici, dispatcher e responsabili.

Per ora non è ancora un'applicazione con schermate, login o API REST. È una base solida su cui costruire quelle parti dopo.

## Perché non siamo partiti subito dalla grafica o dalle API

Se parti subito da schermate, controller o database, rischi di costruire un progetto che funziona solo in superficie ma ha regole interne confuse.

Noi abbiamo fatto il contrario:

1. prima abbiamo costruito le regole del mondo dei trasporti;
2. poi abbiamo costruito i casi d'uso applicativi;
3. poi abbiamo aggiunto un'infrastruttura tecnica controllata;
4. solo dopo arriveranno API, controller e interfacce esterne.

È come costruire un camion: prima il telaio, poi il motore, poi l'impianto elettrico, poi la carrozzeria. Non parti dalla vernice.

## Stato attuale

Oggi TruckFlow Manager ha:

- un **Domain Layer** ricco e pulito;
- un **Application Layer** con use case e repository port;
- un **Infrastructure Layer** con wiring Spring non-web, repository in-memory e alcuni repository file-backed prototipali;
- test per controllare regole, confini e stabilità.

Il prossimo grande passo sarà il **Punto 8 — API Layer**, dove inizieremo a esporre il sistema verso l'esterno.
