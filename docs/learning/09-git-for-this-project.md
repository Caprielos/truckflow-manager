# 09 - Git spiegato per questo progetto

## git status

Dice dove sei e se hai file modificati.

```bash
git status
```

## git branch

Mostra le branch locali.

```bash
git branch
```

## git branch -r

Mostra le branch remote.

```bash
git branch -r
```

## git checkout -b nome-branch

Crea una branch nuova e ci entra.

```bash
git checkout -b add-application-layer
```

## git add .

Aggiunge tutte le modifiche allo staging.

```bash
git add .
```

## git commit -m "messaggio"

Crea un salvataggio Git locale.

```bash
git commit -m "Add application layer"
```

## git push origin main

Manda `main` su GitHub.

```bash
git push origin main
```

## git merge nome-branch

Unisce una branch dentro quella corrente.

```bash
git merge add-application-layer
```

## git branch -d nome-branch

Cancella una branch locale già unita.

```bash
git branch -d add-application-layer
```

## git push origin --delete nome-branch

Cancella una branch su GitHub.

```bash
git push origin --delete add-application-layer
```
