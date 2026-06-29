# Comandi Git usati nel progetto

## Controllare stato

```bash
git status
```

Mostra branch attuale, file modificati, file non tracciati.

## Vedere branch locale

```bash
git branch
```

## Vedere branch remote

```bash
git branch -r
```

## Creare branch

```bash
git checkout -b nome-branch
```

## Entrare in una branch

```bash
git checkout nome-branch
```

## Tornare su main

```bash
git checkout main
```

## Aggiornare main da GitHub

```bash
git pull origin main
```

## Applicare una patch

```bash
git apply --check file.patch
git apply file.patch
```

Il primo comando controlla. Il secondo applica davvero.

## Aggiungere file al commit

```bash
git add .
```

oppure più mirato:

```bash
git add README.md docs
```

## Commit

```bash
git commit -m "Messaggio del commit"
```

## Push su main

```bash
git push origin main
```

## Push branch

```bash
git push -u origin nome-branch
```

## Merge branch in main

```bash
git checkout main
git pull origin main
git merge nome-branch
mvn clean test
git push origin main
```

## Cancellare branch locale

```bash
git branch -d nome-branch
```

## Cancellare branch remota

```bash
git push origin --delete nome-branch
```

## Pulizia branch remote obsolete

```bash
git fetch --prune
```

## Cancellare file patch rimasti

```bash
rm -f nome-file.patch
```

## Zip progetto senza file inutili

```bash
zip -r truckflow-manager-current.zip truckflow-manager   -x "truckflow-manager/.git/*"   -x "truckflow-manager/target/*"   -x "truckflow-manager/.idea/*"   -x "truckflow-manager/.DS_Store"
```
