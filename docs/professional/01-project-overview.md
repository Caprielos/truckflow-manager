# 1. Project Overview

## Purpose

TruckFlow Manager is a Java 21 logistics and transport management project designed around a clean, layered architecture.

The project models core concepts of road transport and fleet-oriented logistics: users, qualifications, operational roles, vehicles, cargo, locations, trip templates, shipments, documents and compliance requirements.

## Current maturity

The project has completed three major architectural cycles:

- Domain foundation and review;
- Application Layer implementation and freeze;
- Infrastructure Layer foundation, prototype repositories and freeze.

The system is not yet an HTTP API product. It is currently a strong domain/application/infrastructure foundation prepared for a future API Layer.

## Main goals

TruckFlow Manager aims to provide:

- a pure domain model independent from technical frameworks;
- application use cases independent from delivery mechanisms;
- infrastructure adapters implementing application outbound ports;
- technical tests that protect architectural boundaries;
- documentation that explains both historical evolution and current architecture.

## Explicit exclusions at this stage

At the end of Punto 7, the project intentionally does not include:

- REST controllers;
- web DTOs;
- HTTP security;
- JPA entities;
- Spring Data repositories;
- relational database schema;
- frontend;
- external service integrations.

These concerns belong to future roadmap points, starting with **Punto 8 — API Layer**.
