package it.gabriele.truckflow.domain.abilitazioni;

public enum AbilitazioneCatalogo {
  PATENTE_C(
      "PATENTE_C", "Patente C", CategoriaAbilitazione.PATENTI_GUIDA, "Autocarri superiori a 3,5 t"),

  PATENTE_C1(
      "PATENTE_C1",
      "Patente C1",
      CategoriaAbilitazione.PATENTI_GUIDA,
      "Autocarri da 3,5 t a 7,5 t"),

  PATENTE_CE(
      "PATENTE_CE",
      "Patente CE",
      CategoriaAbilitazione.PATENTI_GUIDA,
      "Autoarticolati e autotreni"),

  PATENTE_C1E(
      "PATENTE_C1E",
      "Patente C1E",
      CategoriaAbilitazione.PATENTI_GUIDA,
      "C1 con rimorchio pesante"),

  PATENTE_BE(
      "PATENTE_BE",
      "Patente BE",
      CategoriaAbilitazione.PATENTI_GUIDA,
      "Veicoli categoria B con rimorchio"),

  PATENTE_D("PATENTE_D", "Patente D", CategoriaAbilitazione.PATENTI_GUIDA, "Autobus"),

  PATENTE_D1("PATENTE_D1", "Patente D1", CategoriaAbilitazione.PATENTI_GUIDA, "Minibus"),

  PATENTE_DE(
      "PATENTE_DE", "Patente DE", CategoriaAbilitazione.PATENTI_GUIDA, "Autobus con rimorchio"),

  PATENTE_D1E(
      "PATENTE_D1E", "Patente D1E", CategoriaAbilitazione.PATENTI_GUIDA, "Minibus con rimorchio"),

  CQC_MERCI(
      "CQC_MERCI",
      "CQC Merci",
      CategoriaAbilitazione.CQC,
      "Qualificazione professionale per trasporto merci per conto terzi"),

  CQC_PERSONE(
      "CQC_PERSONE",
      "CQC Persone",
      CategoriaAbilitazione.CQC,
      "Qualificazione professionale per trasporto persone per conto terzi"),

  ADR_BASE(
      "ADR_BASE",
      "ADR Base",
      CategoriaAbilitazione.ADR,
      "Abilitazione base per trasporto merci pericolose"),

  ADR_CISTERNA(
      "ADR_CISTERNA",
      "ADR Cisterna",
      CategoriaAbilitazione.ADR,
      "Abilitazione per trasporto merci pericolose in cisterna"),

  ADR_CLASSE_1("ADR_CLASSE_1", "ADR Classe 1", CategoriaAbilitazione.ADR, "Esplosivi"),

  ADR_CLASSE_2("ADR_CLASSE_2", "ADR Classe 2", CategoriaAbilitazione.ADR, "Gas"),

  ADR_CLASSE_3("ADR_CLASSE_3", "ADR Classe 3", CategoriaAbilitazione.ADR, "Liquidi infiammabili"),

  ADR_CLASSE_4("ADR_CLASSE_4", "ADR Classe 4", CategoriaAbilitazione.ADR, "Solidi infiammabili"),

  ADR_CLASSE_5("ADR_CLASSE_5", "ADR Classe 5", CategoriaAbilitazione.ADR, "Comburenti e perossidi"),

  ADR_CLASSE_6("ADR_CLASSE_6", "ADR Classe 6", CategoriaAbilitazione.ADR, "Tossici e infettivi"),

  ADR_CLASSE_7("ADR_CLASSE_7", "ADR Classe 7", CategoriaAbilitazione.ADR, "Radioattivi"),

  ADR_CLASSE_8("ADR_CLASSE_8", "ADR Classe 8", CategoriaAbilitazione.ADR, "Corrosivi"),

  ADR_CLASSE_9(
      "ADR_CLASSE_9",
      "ADR Classe 9",
      CategoriaAbilitazione.ADR,
      "Materie e oggetti pericolosi vari"),

  ATP(
      "ATP",
      "ATP",
      CategoriaAbilitazione.ALIMENTI_FARMACI,
      "Abilitazione/certificazione per trasporto con mezzi refrigerati"),

  HACCP(
      "HACCP",
      "HACCP",
      CategoriaAbilitazione.ALIMENTI_FARMACI,
      "Formazione per manipolazione e gestione alimenti"),

  TRASPORTO_FARMACI(
      "TRASPORTO_FARMACI",
      "Trasporto Farmaci",
      CategoriaAbilitazione.ALIMENTI_FARMACI,
      "Abilitazione per trasporto farmaci e prodotti sanitari"),

  ANIMALI_VIVI(
      "ANIMALI_VIVI",
      "Animali vivi",
      CategoriaAbilitazione.ANIMALI,
      "Abilitazione per trasporto di animali vivi"),

  BENESSERE_ANIMALE(
      "BENESSERE_ANIMALE",
      "Benessere animale",
      CategoriaAbilitazione.ANIMALI,
      "Formazione/certificazione sul rispetto delle norme di benessere animale"),

  ANIMALI_DA_MACELLO(
      "ANIMALI_DA_MACELLO",
      "Animali da macello",
      CategoriaAbilitazione.ANIMALI,
      "Abilitazione per trasporto di animali destinati alla macellazione"),

  ANIMALI_DA_COMPAGNIA(
      "ANIMALI_DA_COMPAGNIA",
      "Animali da compagnia",
      CategoriaAbilitazione.ANIMALI,
      "Abilitazione per trasporto di animali domestici o da compagnia"),

  RIFIUTI_CAT1(
      "RIFIUTI_CAT1",
      "Rifiuti Categoria 1",
      CategoriaAbilitazione.RIFIUTI,
      "Autorizzazione per trasporto rifiuti categoria 1"),

  RIFIUTI_CAT2(
      "RIFIUTI_CAT2",
      "Rifiuti Categoria 2",
      CategoriaAbilitazione.RIFIUTI,
      "Autorizzazione per trasporto rifiuti categoria 2"),

  RIFIUTI_CAT3(
      "RIFIUTI_CAT3",
      "Rifiuti Categoria 3",
      CategoriaAbilitazione.RIFIUTI,
      "Autorizzazione per trasporto rifiuti categoria 3"),

  RIFIUTI_CAT4(
      "RIFIUTI_CAT4",
      "Rifiuti Categoria 4",
      CategoriaAbilitazione.RIFIUTI,
      "Autorizzazione per trasporto rifiuti categoria 4"),

  RIFIUTI_CAT5(
      "RIFIUTI_CAT5",
      "Rifiuti Categoria 5",
      CategoriaAbilitazione.RIFIUTI,
      "Autorizzazione per trasporto rifiuti categoria 5"),

  RIFIUTI_CAT6(
      "RIFIUTI_CAT6",
      "Rifiuti Categoria 6",
      CategoriaAbilitazione.RIFIUTI,
      "Autorizzazione per trasporto rifiuti categoria 6"),

  RIFIUTI_CAT8(
      "RIFIUTI_CAT8",
      "Rifiuti Categoria 8",
      CategoriaAbilitazione.RIFIUTI,
      "Autorizzazione per trasporto rifiuti categoria 8"),

  RIFIUTI_CAT9(
      "RIFIUTI_CAT9",
      "Rifiuti Categoria 9",
      CategoriaAbilitazione.RIFIUTI,
      "Autorizzazione per trasporto rifiuti categoria 9"),

  RIFIUTI_CAT10(
      "RIFIUTI_CAT10",
      "Rifiuti Categoria 10",
      CategoriaAbilitazione.RIFIUTI,
      "Autorizzazione per trasporto rifiuti categoria 10"),

  MULETTO(
      "MULETTO",
      "Muletto",
      CategoriaAbilitazione.MACCHINE_OPERATRICI,
      "Abilitazione per uso del carrello elevatore"),

  PLE(
      "PLE",
      "PLE",
      CategoriaAbilitazione.MACCHINE_OPERATRICI,
      "Abilitazione per uso di piattaforme di lavoro elevabili"),

  GRU_AUTOCARRO(
      "GRU_AUTOCARRO",
      "Gru Autocarro",
      CategoriaAbilitazione.MACCHINE_OPERATRICI,
      "Abilitazione per uso di gru su autocarro"),

  GRU_TORRE(
      "GRU_TORRE",
      "Gru Torre",
      CategoriaAbilitazione.MACCHINE_OPERATRICI,
      "Abilitazione per uso di gru a torre"),

  ESCAVATORE(
      "ESCAVATORE",
      "Escavatore",
      CategoriaAbilitazione.MACCHINE_OPERATRICI,
      "Abilitazione per uso di escavatore"),

  PALA(
      "PALA",
      "Pala",
      CategoriaAbilitazione.MACCHINE_OPERATRICI,
      "Abilitazione per uso di pala caricatrice"),

  TERNA(
      "TERNA", "Terna", CategoriaAbilitazione.MACCHINE_OPERATRICI, "Abilitazione per uso di terna"),

  SOLLEVATORE_TELESCOPICO(
      "SOLLEVATORE_TELESCOPICO",
      "Sollevatore Telescopico",
      CategoriaAbilitazione.MACCHINE_OPERATRICI,
      "Abilitazione per uso di sollevatore telescopico"),

  SICUREZZA_CANTIERI(
      "SICUREZZA_CANTIERI",
      "Sicurezza Cantieri",
      CategoriaAbilitazione.SICUREZZA,
      "Formazione richiesta per operare in sicurezza nei cantieri"),

  DPI_III_CATEGORIA(
      "DPI_III_CATEGORIA",
      "DPI III Categoria",
      CategoriaAbilitazione.SICUREZZA,
      "Formazione per utilizzo di dispositivi di protezione individuale di terza categoria"),

  SEGNALETICA_STRADALE(
      "SEGNALETICA_STRADALE",
      "Segnaletica Stradale",
      CategoriaAbilitazione.SICUREZZA,
      "Formazione per attività con segnaletica stradale e cantieri su strada"),

  MOVIMENTAZIONE_CARICHI(
      "MOVIMENTAZIONE_CARICHI",
      "Movimentazione Carichi",
      CategoriaAbilitazione.SICUREZZA,
      "Formazione per movimentazione manuale e operativa dei carichi"),

  ANTINCENDIO(
      "ANTINCENDIO",
      "Antincendio",
      CategoriaAbilitazione.SICUREZZA,
      "Formazione antincendio per la gestione delle emergenze"),

  PRIMO_SOCCORSO(
      "PRIMO_SOCCORSO",
      "Primo Soccorso",
      CategoriaAbilitazione.SICUREZZA,
      "Formazione per addetti al primo soccorso aziendale"),

  TRASPORTO_CONTAINER(
      "TRASPORTO_CONTAINER",
      "Trasporto Container",
      CategoriaAbilitazione.PORTI_AEROPORTI,
      "Abilitazione per attività di trasporto container in ambito portuale e terminal"),

  IMO(
      "IMO",
      "IMO",
      CategoriaAbilitazione.PORTI_AEROPORTI,
      "Abilitazione per gestione merci pericolose in ambito marittimo e portuale"),

  ACCESSO_AREE_PORTUALI(
      "ACCESSO_AREE_PORTUALI",
      "Accesso Aree Portuali",
      CategoriaAbilitazione.PORTI_AEROPORTI,
      "Permesso o abilitazione per accesso e operatività in aree portuali"),

  ADR_PORTUALE(
      "ADR_PORTUALE",
      "ADR Portuale",
      CategoriaAbilitazione.PORTI_AEROPORTI,
      "Abilitazione per gestione ADR in contesto portuale"),

  AEROPORTUALE_MERCI(
      "AEROPORTUALE_MERCI",
      "Aeroportuale Merci",
      CategoriaAbilitazione.PORTI_AEROPORTI,
      "Abilitazione per operare nel trasporto merci in ambito aeroportuale"),

  SICUREZZA_AEROPORTUALE(
      "SICUREZZA_AEROPORTUALE",
      "Sicurezza Aeroportuale",
      CategoriaAbilitazione.PORTI_AEROPORTI,
      "Formazione o abilitazione per operare secondo le regole di sicurezza aeroportuale"),

  LOGISTICA(
      "LOGISTICA",
      "Logistica",
      CategoriaAbilitazione.AZIENDALI_LOGISTICA,
      "Formazione interna per attività logistiche aziendali"),

  GESTIONE_MAGAZZINO(
      "GESTIONE_MAGAZZINO",
      "Gestione Magazzino",
      CategoriaAbilitazione.AZIENDALI_LOGISTICA,
      "Formazione interna per gestione operativa del magazzino"),

  IMBRACATURA_CARICHI(
      "IMBRACATURA_CARICHI",
      "Imbracatura Carichi",
      CategoriaAbilitazione.AZIENDALI_LOGISTICA,
      "Formazione interna per imbracatura e messa in sicurezza dei carichi"),

  USO_RAMPE_BAIE(
      "USO_RAMPE_BAIE",
      "Uso Rampe e Baie",
      CategoriaAbilitazione.AZIENDALI_LOGISTICA,
      "Formazione interna per utilizzo di rampe, baie di carico e aree di carico/scarico"),

  TRASPORTO_MERCI_SENSIBILI(
      "TRASPORTO_MERCI_SENSIBILI",
      "Trasporto Merci Sensibili",
      CategoriaAbilitazione.AZIENDALI_LOGISTICA,
      "Formazione interna per gestione e trasporto di merci sensibili"),

  TRASPORTO_APPARECCHIATURE_MEDICHE(
      "TRASPORTO_APPARECCHIATURE_MEDICHE",
      "Trasporto Apparecchiature Mediche",
      CategoriaAbilitazione.AZIENDALI_LOGISTICA,
      "Formazione interna per trasporto di apparecchiature mediche");

  private final String codice;
  private final String nome;
  private final CategoriaAbilitazione categoria;
  private final String descrizioneBreve;

  AbilitazioneCatalogo(
      String codice, String nome, CategoriaAbilitazione categoria, String descrizioneBreve) {
    this.codice = codice;
    this.nome = nome;
    this.categoria = categoria;
    this.descrizioneBreve = descrizioneBreve;
  }

  public String codice() {
    return codice;
  }

  public String nome() {
    return nome;
  }

  public CategoriaAbilitazione categoria() {
    return categoria;
  }

  public String descrizioneBreve() {
    return descrizioneBreve;
  }
}
