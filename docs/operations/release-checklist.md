# Checklist prima di considerare una versione chiusa

1. Sei su `main`.
2. `git status` è pulito.
3. `git branch` mostra solo `main`.
4. `git branch -r` mostra solo `origin/main`.
5. `mvn clean test` è verde.
6. Non ci sono file `.patch` rimasti nel progetto.
7. Non ci sono file `._*` di macOS.
8. README e docs sono aggiornati.
