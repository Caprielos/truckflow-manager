# Domain Rules

Questo documento raccoglie le regole principali distribuite nei package `*Rules` e nelle entity/value object.

## cargo
- La categoria merce determina requisiti operativi e documentali.
- ADR e profilo dangerous goods restano separati dai colli ordinari.

## company
- Il trasporto internazionale richiede licenze aziendali valide.
- Il trasporto rifiuti richiede categorie ambientali coerenti.

## driver
- Il driver può avere vecchie categorie compatibili e nuovi certificati con validità temporale.
- La CQC e l’ADR non sono semplici booleani: sono requisiti professionali/documentali.

## fleet
- Il modello realistico distingue unità veicolo, allestimento, massa, dimensione, assi, aggancio e certificati.
- Il refrigerato è un allestimento/certificazione, non un tipo veicolo separato.
- Le combinazioni calcolano tipo, assi totali, massa complessiva e scadenze certificate.

## fuel
- Consumi fuori soglia sono anomalie, non errori matematici.
- La differenza odometrica deve essere coerente tra rifornimenti.

## loadsecurity
- La dotazione minima dipende da tipo merce e peso.
- La checklist non sostituisce la missione, la abilita.

## maintenance
- Work order e downtime sono separati: uno è intervento, l’altro è indisponibilità del mezzo.
- Ticket autista permette segnalazioni dal campo.

## shared
- Valori nulli, vuoti o negativi vengono rifiutati quando non ammessi.
- Ogni value object espone factory statiche leggibili.

## shipment
- Una Shipment può essere creata solo da un TransportOrder ACCEPTED.
- Transizioni valide: CREATED → PLANNED → DISPATCHED → IN_TRANSIT → DELIVERED.
- CANCELLED è consentito solo se la spedizione non è terminale.

## telematics
- Fuel drop e speeding sono eventi rilevabili da regole pure.
- Snapshot e behavior event restano separati da mission/tracking.

## tire
- Una gomma fisica può cambiare veicolo e posizione.
- Il battistrada minimo genera alert/sostituzione.
