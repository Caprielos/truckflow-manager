package it.gabriele.truckflow.infrastructure.persistence.jpa;

import java.time.LocalDate;
import java.util.UUID;

public class AutistaEntity {

  private UUID id;
  private String nome;
  private String cognome;
  private LocalDate dataNascita;

  public AutistaEntity() {}

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCognome() {
    return cognome;
  }

  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  public LocalDate getDataNascita() {
    return dataNascita;
  }

  public void setDataNascita(LocalDate dataNascita) {
    this.dataNascita = dataNascita;
  }
}
