# 8. Cosa sono i port e gli adapter

Port e adapter sono parole tecniche, ma l'idea è semplice.

Un **port** è un contratto.

Un **adapter** è una classe concreta che rispetta quel contratto.

## Esempio semplice

Immagina una presa elettrica.

La presa è il port: definisce come ci si collega.

Il caricatore è l'adapter: usa quella presa per fare qualcosa di concreto.

## Port in

Un port in rappresenta un'azione che entra nel sistema.

Esempio: `RegisterLocationUseCase`.

È il modo con cui l'esterno, in futuro anche una API, potrà chiedere al sistema di fare qualcosa.

## Port out

Un port out rappresenta una dipendenza verso l'esterno.

Esempio: `LocationRepository`.

Il use case dice: “ho bisogno di salvare una location”, ma non decide se salvarla in memoria, su file o in database.

## Adapter

Un adapter è l'implementazione tecnica.

Esempio:

- `InMemoryLocationRepository` è un adapter in-memory;
- `FileLocationRepository` è un adapter file-backed.

## Perché questa separazione è utile

Perché possiamo cambiare tecnologia senza cambiare il cuore del sistema.

Se domani passiamo da file a database, il use case deve continuare a parlare con la stessa port.
