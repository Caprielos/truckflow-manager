# 6. Cosa sono i use case

Un use case è un'azione che il sistema sa fare.

Non è una schermata e non è un controller. È un flusso applicativo.

## Esempio semplice

“Registrare una location” è un use case.

Per farlo, il sistema deve:

1. ricevere i dati della location;
2. controllare che non siano vuoti;
3. controllare che il codice non sia già usato;
4. creare l'oggetto di dominio;
5. salvarlo con un repository;
6. restituire un risultato.

## Perché i use case sono importanti

I use case tengono ordinata la logica applicativa.

Senza use case, rischieremmo di mettere la logica nei controller, nei repository o direttamente nel main. Questo renderebbe il progetto confuso.

## Come sono organizzati nel progetto

Ogni use case ha di solito:

- un **command**, cioè l'input;
- una **port in**, cioè il contratto dell'azione;
- un **service**, cioè l'implementazione dell'azione;
- un **result**, cioè l'output.

## Cosa non deve fare un use case

Un use case non deve:

- conoscere REST;
- conoscere JSON;
- conoscere JPA;
- decidere dettagli del database;
- contenere regole profonde che appartengono al dominio.

Deve coordinare, non sostituire il dominio.
