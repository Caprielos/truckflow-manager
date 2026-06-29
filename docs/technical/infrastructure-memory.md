# Infrastructure memory

`infrastructure/memory` contiene repository in memoria.

## A cosa servono

Servono per far funzionare use case e test di scenario senza database.

## Come funzionano

La classe base è:

```java
InMemoryRepository<T>
```

Internamente usa:

```java
ConcurrentHashMap<String, T>
```

Quindi salva oggetti in RAM usando una chiave stringa.

## Limite importante

Non è persistenza reale. Se l’applicazione si spegne, i dati spariscono.

## Perché è utile

Permette di testare subito flussi come:

- parcheggiare un convoglio;
- pianificare una missione;
- calcolare economics;
- registrare magazzino;
- calcolare payroll.

Quando arriverà il database, le interfacce repository resteranno simili, ma cambierà l’implementazione.
