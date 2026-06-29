# Setup e test

## Entrare nel progetto

```bash
cd "/Users/gabriele/Documents/Corso Chat/truckflow-manager"
```

## Test completi

```bash
mvn clean test
```

## Controllo stato Git

```bash
git status
```

## Controllo branch

```bash
git branch
git branch -r
```

## Pulizia file macOS

```bash
find . -name "._*" -delete
```

## Pulizia patch residue

```bash
rm -f *.patch
```

Attenzione: usa `rm -f *.patch` solo se sei sicuro di non dover più applicare patch.
