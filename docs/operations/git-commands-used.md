# Comandi Git usati nel progetto

Questa è la lista dei comandi Git ricorrenti che sono stati usati durante il lavoro.

## Controlli

```bash
git status
git branch
git branch -r
git branch --show-current
```

## Aggiornare main

```bash
git checkout main
git pull origin main
```

## Creare branch di lavoro

```bash
git checkout -b integrate-realistic-domain-model
git checkout -b add-economics-profitability-model
git checkout -b complete-domain-before-application
git checkout -b add-application-layer
git checkout -b add-application-memory-scenarios
```

## Applicare patch

```bash
cp ~/Downloads/nome-file.patch .
git apply --check nome-file.patch
git apply nome-file.patch
```

## Test

```bash
mvn clean test
```

## Commit

```bash
git add .
git commit -m "Messaggio commit"
```

## Push branch

```bash
git push -u origin nome-branch
```

## Merge in main

```bash
git checkout main
git pull origin main
git merge nome-branch
mvn clean test
git push origin main
```

## Cancellazione branch

```bash
git branch -d nome-branch
git push origin --delete nome-branch
git fetch --prune
```

## Controllo finale desiderato

```text
* main
origin/main
working tree clean
```
