# Modello di dominio

Questo documento descrive i concetti principali del dominio di TruckFlow Manager.

## Spedizione

Una spedizione rappresenta un trasporto richiesto da un cliente.

In futuro una spedizione potrà contenere informazioni come:

- cliente;
- punto di partenza;
- punto di destinazione;
- autista assegnato;
- camion assegnato;
- data di partenza;
- data di consegna prevista;
- stato corrente.

## Stati di una spedizione

Attualmente il sistema prevede i seguenti stati:

| Stato | Significato |
|---|---|
| CREATED | La spedizione è stata creata ma non è ancora assegnata |
| ASSIGNED | La spedizione ha un autista e un camion assegnati |
| IN_TRANSIT | La spedizione è in corso |
| DELIVERED | La spedizione è stata consegnata |
| CANCELLED | La spedizione è stata annullata |

## Transizioni consentite

| Da | A | Consentita |
|---|---|---|
| CREATED | ASSIGNED | Sì |
| CREATED | CANCELLED | Sì |
| ASSIGNED | IN_TRANSIT | Sì |
| ASSIGNED | CANCELLED | Sì |
| IN_TRANSIT | DELIVERED | Sì |
| DELIVERED | Qualsiasi altro stato | No |
| CANCELLED | Qualsiasi altro stato | No |

## Regola principale

Una spedizione consegnata o annullata non può tornare a uno stato precedente.
