# TruckFlow Digital Domain Documentation

This folder contains the HTML + CSS version of the official TruckFlow domain documentation.

## Purpose

The page is a faithful digital mirror of the Markdown documentation files.

The left sidebar contains the documentation index. Selecting an item shows the corresponding document on the right.

## Tooltip modes

The tooltip behavior is controlled only by the class on the `<body>` tag in `index.html`.

```html
<body class="tooltip-enabled">
```

Shows Italian explanations through hover tooltips.

```html
<body class="tooltip-disabled">
```

Shows Italian explanations directly inline.

The HTML markup of technical terms does not need to change. Only the global CSS class changes the behavior.

## Source documents included

This digital documentation mirrors:

- `TRUCKFLOW_PROJECT_DOCUMENTATION.md`
- `docs/README.md`
- `docs/01-project-overview.md`
- `docs/02-domain-users.md`
- `docs/03-domain-qualifications.md`
- `docs/04-domain-operational.md`
- `docs/05-domain-vehicles.md`
- `docs/06-architecture-decisions.md`
- `docs/07-domain-cargo.md`
- `docs/08-domain-locations.md`
- `docs/09-domain-triptemplates.md`
- `docs/10-domain-shipments.md`
- `docs/11-domain-documents.md`
- `docs/12-domain-compliance.md`
- `docs/13-domain-rules.md`
- `docs/14-domain-review-patches.md`
- `docs/15-domain-test-suite-review.md`
- `docs/16-application-layer-blueprint.md`
- `command_basic.md`


## Application layer blueprint

The digital documentation now also mirrors the Punto 6A application layer blueprint. This step explains the intended structure for command, result, port in, port out, use cases, repository ports, in-memory repositories and application tests before introducing REST APIs or databases.

## Aggiornamento Punto 6B

La documentazione digitale include ora anche il riferimento al Punto 6B — Application Foundation, che introduce i primi package e contratti dell'application layer senza modificare la documentazione Markdown esistente.

Documento di riferimento: `docs/17-application-foundation.md`.
