# 7. Cosa sono i repository

Un repository è un oggetto che permette al programma di salvare e recuperare dati.

Puoi immaginarlo come uno scaffale ordinato: quando hai bisogno di una location, di un cargo o di un documento, chiedi al repository.

## Repository port

Nel progetto, l'application layer conosce solo i contratti dei repository.

Questi contratti si chiamano repository port.

Esempio: un use case sa che può usare `LocationRepository`, ma non sa se dietro c'è memoria, file o database.

## Repository in-memory

Un repository in-memory salva i dati in memoria.

È come un quaderno temporaneo: funziona mentre il programma è acceso, ma non è pensato per salvare dati definitivamente.

È molto utile per test e sviluppo.

## Repository file-backed

Un repository file-backed salva i dati su file.

È come un quaderno che viene scritto su disco. Non è ancora un database vero, ma permette ai dati di sopravvivere oltre l'esecuzione del programma.

Nel Punto 7 abbiamo usato file-backed repository per validare il pattern tecnico.

## Repository futuri

In futuro potremo introdurre repository basati su database, ma solo quando sarà il momento giusto.

La cosa importante è che i use case non dovranno cambiare troppo, perché loro parlano con i repository port, non con l'implementazione tecnica.
