# Project overview

TruckFlow Manager è una base backend Java per un Fleet / Transport Management System.

Il progetto modella quattro livelli concettuali:

```text
cliente / ordine
→ cosa chiede il cliente

shipment
→ spedizione da organizzare

operation / mission
→ viaggio reale con autista, convoglio e route plan

economics / billing / payroll
→ costi, ricavi, utile/perdita, stipendio autista e fatturazione
```

## Obiettivo

L'obiettivo non è creare un semplice esercizio con `Truck`, `Driver` e `Shipment`, ma un modello vicino a un gestionale reale, dove entrano in gioco:

- veicoli e rimorchi;
- convogli già agganciati;
- autisti, patenti, CQC e ADR;
- merce normale, ADR, rifiuti, refrigerata, alimentare, animali vivi;
- documenti come bolla/DDT, CMR, POD, ADR, FIR e temperature log;
- costi camion, rimorchi, allestimenti, gomme, assicurazioni, manutenzione;
- IVA, ricavi, spese, debito e cassa;
- payroll autista con ore guida, attesa, notturno, festivo e premi;
- depositi, magazzini, piazzali, parcheggi e posti numerati;
- magazzino ricambi e materiali;
- import dati esterni;
- use case application e repository in memoria.

## Cosa è già presente

```text
domain
→ completo e ricco

application
→ use case e repository port

infrastructure/memory
→ repository in memoria per test e demo

test di scenario
→ prove realistiche di flussi completi
```

## Cosa manca ancora

Il progetto non ha ancora:

- API REST;
- database persistente;
- autenticazione reale web;
- frontend;
- generazione PDF vera;
- integrazioni esterne reali.

Questi saranno step successivi, dopo aver stabilizzato application + memory.
