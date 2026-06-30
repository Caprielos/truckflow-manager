package it.gabriele.truckflow.domain.abilitazioni;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CatalogoAbilitazioniTest {

  @Test
  void contienePatentiGuidaInseriteNelCatalogo() {
    var patenti = CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.PATENTI_GUIDA);

    assertEquals(9, patenti.size());
    assertTrue(patenti.contains(AbilitazioneCatalogo.PATENTE_C));
    assertTrue(patenti.contains(AbilitazioneCatalogo.PATENTE_CE));
    assertTrue(patenti.contains(AbilitazioneCatalogo.PATENTE_D1E));
  }

  @Test
  void contieneCqcInseriteNelCatalogo() {
    var cqc = CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.CQC);

    assertEquals(2, cqc.size());
    assertTrue(cqc.contains(AbilitazioneCatalogo.CQC_MERCI));
    assertTrue(cqc.contains(AbilitazioneCatalogo.CQC_PERSONE));
  }

  @Test
  void contieneAdrInseriteNelCatalogo() {
    var adr = CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.ADR);

    assertEquals(11, adr.size());
    assertTrue(adr.contains(AbilitazioneCatalogo.ADR_BASE));
    assertTrue(adr.contains(AbilitazioneCatalogo.ADR_CISTERNA));
    assertTrue(adr.contains(AbilitazioneCatalogo.ADR_CLASSE_9));
  }

  @Test
  void contieneAlimentiFarmaciInseritiNelCatalogo() {
    var alimentiFarmaci = CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.ALIMENTI_FARMACI);

    assertEquals(3, alimentiFarmaci.size());
    assertTrue(alimentiFarmaci.contains(AbilitazioneCatalogo.ATP));
    assertTrue(alimentiFarmaci.contains(AbilitazioneCatalogo.HACCP));
    assertTrue(alimentiFarmaci.contains(AbilitazioneCatalogo.TRASPORTO_FARMACI));
  }

  @Test
  void contieneAnimaliInseritiNelCatalogo() {
    var animali = CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.ANIMALI);

    assertEquals(4, animali.size());
    assertTrue(animali.contains(AbilitazioneCatalogo.ANIMALI_VIVI));
    assertTrue(animali.contains(AbilitazioneCatalogo.BENESSERE_ANIMALE));
    assertTrue(animali.contains(AbilitazioneCatalogo.ANIMALI_DA_MACELLO));
    assertTrue(animali.contains(AbilitazioneCatalogo.ANIMALI_DA_COMPAGNIA));
  }

  @Test
  void contieneRifiutiInseritiNelCatalogo() {
    var rifiuti = CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.RIFIUTI);

    assertEquals(9, rifiuti.size());
    assertTrue(rifiuti.contains(AbilitazioneCatalogo.RIFIUTI_CAT1));
    assertTrue(rifiuti.contains(AbilitazioneCatalogo.RIFIUTI_CAT5));
    assertTrue(rifiuti.contains(AbilitazioneCatalogo.RIFIUTI_CAT10));
  }

  @Test
  void contieneMacchineOperatriciInseriteNelCatalogo() {
    var macchine = CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.MACCHINE_OPERATRICI);

    assertEquals(8, macchine.size());
    assertTrue(macchine.contains(AbilitazioneCatalogo.MULETTO));
    assertTrue(macchine.contains(AbilitazioneCatalogo.PLE));
    assertTrue(macchine.contains(AbilitazioneCatalogo.GRU_AUTOCARRO));
    assertTrue(macchine.contains(AbilitazioneCatalogo.SOLLEVATORE_TELESCOPICO));
  }

  @Test
  void contieneSicurezzaInseritaNelCatalogo() {
    var sicurezza = CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.SICUREZZA);

    assertEquals(6, sicurezza.size());
    assertTrue(sicurezza.contains(AbilitazioneCatalogo.SICUREZZA_CANTIERI));
    assertTrue(sicurezza.contains(AbilitazioneCatalogo.DPI_III_CATEGORIA));
    assertTrue(sicurezza.contains(AbilitazioneCatalogo.SEGNALETICA_STRADALE));
    assertTrue(sicurezza.contains(AbilitazioneCatalogo.MOVIMENTAZIONE_CARICHI));
    assertTrue(sicurezza.contains(AbilitazioneCatalogo.ANTINCENDIO));
    assertTrue(sicurezza.contains(AbilitazioneCatalogo.PRIMO_SOCCORSO));
  }

  @Test
  void contienePortiAeroportiInseritiNelCatalogo() {
    var portiAeroporti = CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.PORTI_AEROPORTI);

    assertEquals(6, portiAeroporti.size());
    assertTrue(portiAeroporti.contains(AbilitazioneCatalogo.TRASPORTO_CONTAINER));
    assertTrue(portiAeroporti.contains(AbilitazioneCatalogo.IMO));
    assertTrue(portiAeroporti.contains(AbilitazioneCatalogo.ACCESSO_AREE_PORTUALI));
    assertTrue(portiAeroporti.contains(AbilitazioneCatalogo.ADR_PORTUALE));
    assertTrue(portiAeroporti.contains(AbilitazioneCatalogo.AEROPORTUALE_MERCI));
    assertTrue(portiAeroporti.contains(AbilitazioneCatalogo.SICUREZZA_AEROPORTUALE));
  }

  @Test
  void contieneFormazioniAziendaliInseriteNelCatalogo() {
    var formazioniAziendali =
        CatalogoAbilitazioni.perCategoria(CategoriaAbilitazione.AZIENDALI_LOGISTICA);

    assertEquals(6, formazioniAziendali.size());
    assertTrue(formazioniAziendali.contains(AbilitazioneCatalogo.LOGISTICA));
    assertTrue(formazioniAziendali.contains(AbilitazioneCatalogo.GESTIONE_MAGAZZINO));
    assertTrue(formazioniAziendali.contains(AbilitazioneCatalogo.IMBRACATURA_CARICHI));
    assertTrue(formazioniAziendali.contains(AbilitazioneCatalogo.USO_RAMPE_BAIE));
    assertTrue(formazioniAziendali.contains(AbilitazioneCatalogo.TRASPORTO_MERCI_SENSIBILI));
    assertTrue(
        formazioniAziendali.contains(AbilitazioneCatalogo.TRASPORTO_APPARECCHIATURE_MEDICHE));
  }

  @Test
  void trovaAbilitazionePerCodice() {
    var abilitazione = CatalogoAbilitazioni.trovaPerCodice("PATENTE_C");

    assertTrue(abilitazione.isPresent());
    assertEquals("Patente C", abilitazione.orElseThrow().nome());
  }
}
