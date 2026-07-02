# 4. Application Layer spiegato semplice

L'Application Layer è la parte che coordina le azioni del programma.

Non è il cervello delle regole profonde: quello è il dominio. L'application layer è più simile a un responsabile operativo che dice: “per fare questa azione, segui questi passaggi”.

## Cosa fa

Esempi di azioni applicative:

- registrare una location;
- cercare una location;
- registrare un cargo;
- creare una shipment;
- aggiungere item o leg a una shipment;
- confermare o cancellare una shipment;
- registrare un documento;
- attivare o archiviare un documento;
- registrare veicoli;
- gestire ruoli operativi;
- registrare requisiti compliance.

## Come lavora

Un use case applicativo di solito fa questo:

1. riceve un command;
2. controlla che i dati minimi ci siano;
3. usa un repository per leggere o salvare;
4. chiama il dominio;
5. restituisce un result.

## Perché non mette tutto nel dominio

Il dominio deve contenere le regole, non l'orchestrazione tecnica.

Per esempio, sapere che una shipment non può avere dati invalidi è una regola di dominio. Sapere da quale repository leggerla è una responsabilità applicativa o infrastrutturale.

## Perché non usa direttamente database o Spring

L'application layer non deve sapere come vengono salvati i dati.

Deve solo conoscere un contratto, chiamato repository port. Dietro quel contratto può esserci un repository in-memory, file-backed o in futuro database.

Questo rende i use case più facili da testare e da cambiare.

## Cosa abbiamo completato

Il Punto 6 ha costruito e congelato il primo Application Layer completo, da 6A a 6M.

Alla fine del Punto 6, l'application layer era pronto per essere collegato a una infrastruttura tecnica reale.
