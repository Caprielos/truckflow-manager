# 10. Perché non abbiamo ancora database, JPA e security

Database, JPA e security sono importanti, ma sono anche parti delicate.

Se arrivano troppo presto, possono guidare male l'architettura.

## Database

Un database serve a salvare dati in modo reale e strutturato.

Però prima di scegliere tabelle, relazioni e query, bisogna capire bene il dominio e i flussi applicativi.

Per questo nel Punto 7 abbiamo usato repository file-backed prototipali, non un database completo.

## JPA

JPA è una tecnologia Java per collegare oggetti e database relazionali.

È utile, ma può influenzare molto il modo in cui scrivi le classi.

Noi non vogliamo che il dominio sia progettato per piacere a JPA. Il dominio deve rappresentare il business. JPA arriverà solo se e quando servirà.

## Security

La security riguarda login, ruoli, permessi, token, protezione degli endpoint e accessi.

È fondamentale, ma prima servono API e confini esterni.

Per ora abbiamo modellato utenti e ruoli a livello di dominio, ma non abbiamo ancora introdotto security HTTP o JWT.

## Perché questa scelta è prudente

Questa scelta evita di costruire un progetto troppo grande tutto insieme.

Ogni tecnologia deve entrare nel momento giusto, quando il layer precedente è stabile.
