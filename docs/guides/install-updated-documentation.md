# Installare questa documentazione nel progetto

Dopo aver scaricato lo zip della documentazione:

```bash
cd ~/Downloads
unzip truckflow-documentation-final-application-memory.zip
```

Poi:

```bash
cd "/Users/gabriele/Documents/Corso Chat/truckflow-manager"
rm -rf docs
cp -R ~/Downloads/truckflow-documentation-final-application-memory/docs .
cp ~/Downloads/truckflow-documentation-final-application-memory/README.md README.md
```

Commit:

```bash
git status
git add README.md docs
git commit -m "Rewrite documentation after application memory layer"
git push origin main
```
