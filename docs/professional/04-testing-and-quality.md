# 4. Testing and Quality

## Test strategy

TruckFlow Manager uses tests to protect both behavior and architecture.

The current test suite covers:

- domain rules and invariants;
- application use case behavior;
- repository port contracts;
- in-memory repository behavior;
- copy-on-write mutation hardening;
- Spring wiring foundation;
- persistence mapping blueprint;
- file-backed repository behavior;
- infrastructure boundaries and freeze rules.

## Architectural tests

Several tests verify that premature layers are not introduced.

Examples of forbidden concerns at this stage include:

- REST controllers;
- JPA entities;
- Spring Data repositories;
- security filters;
- database packages;
- web packages.

## Infrastructure tests

The Infrastructure Layer includes tests for:

- shared file-backed storage;
- safe encoding and decoding;
- malformed records;
- use case integration through file-backed repositories;
- absence of framework leakage into core layers.

## Documentation tests

Some final freeze tests verify that expected documentation files exist. After the documentation restructure, those tests must point to `docs/old_style/` for historical step documents and to `docs/digital/` for browser documentation.

## Quality principle

The project favors small, controlled steps. Each architectural cycle is closed with review, hardening and freeze documentation before the next cycle begins.
