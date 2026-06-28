# Come sostituire la cartella docs

Questo zip contiene una cartella `docs/` nuova.

Per usarla nel progetto:

```bash
cd "/Users/gabriele/Documents/Corso Chat/truckflow-manager"
rm -rf docs
```

Poi copia dentro la nuova cartella `docs` estratta dallo zip.

Dopo controlla:

```bash
git status
```

Poi, se va bene:

```bash
git add docs
git commit -m "Rewrite project documentation"
git push
```
