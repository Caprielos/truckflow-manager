package it.gabriele.truckflow.domain.economics;

import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Objects;

/** Conto economico di una missione: ricavi cliente meno costi reali/allocati. */
public final class MissionEconomics {

  private static final int MAX_CODE_LENGTH = 50;

  private final String missionNumber;
  private final String shipmentNumber;
  private final List<MissionRevenueLine> revenueLines;
  private final List<MissionCostLine> costLines;
  private final Notes notes;

  private MissionEconomics(
      String missionNumber,
      String shipmentNumber,
      List<MissionRevenueLine> revenueLines,
      List<MissionCostLine> costLines,
      Notes notes) {
    this.missionNumber = validateCode(missionNumber, "Il numero missione è obbligatorio.");
    this.shipmentNumber = validateCode(shipmentNumber, "Il numero spedizione è obbligatorio.");
    this.revenueLines = validateRevenueLines(revenueLines);
    this.costLines = validateCostLines(costLines);
    validateCurrencyCompatibility(this.revenueLines, this.costLines);
    if (notes == null) {
      throw new IllegalArgumentException("Le note economics missione sono obbligatorie.");
    }
    this.notes = notes;
  }

  public static MissionEconomics of(
      String missionNumber,
      String shipmentNumber,
      List<MissionRevenueLine> revenueLines,
      List<MissionCostLine> costLines,
      Notes notes) {
    return new MissionEconomics(missionNumber, shipmentNumber, revenueLines, costLines, notes);
  }

  public static MissionEconomics of(
      String missionNumber,
      String shipmentNumber,
      MissionRevenueLine firstRevenueLine,
      List<MissionCostLine> costLines) {
    if (firstRevenueLine == null) {
      throw new IllegalArgumentException("La prima riga ricavo è obbligatoria.");
    }
    List<MissionRevenueLine> revenueLines = new ArrayList<>();
    revenueLines.add(firstRevenueLine);
    return of(missionNumber, shipmentNumber, revenueLines, costLines, Notes.empty());
  }

  private static String validateCode(String code, String nullMessage) {
    if (code == null) {
      throw new IllegalArgumentException(nullMessage);
    }
    String normalized = code.trim().toUpperCase();
    if (normalized.isEmpty()) {
      throw new IllegalArgumentException(nullMessage);
    }
    if (normalized.length() > MAX_CODE_LENGTH) {
      throw new IllegalArgumentException(
          "Il codice non può superare " + MAX_CODE_LENGTH + " caratteri.");
    }
    if (!normalized.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il codice può contenere solo lettere, numeri, trattini e underscore.");
    }
    return normalized;
  }

  private static List<MissionRevenueLine> validateRevenueLines(List<MissionRevenueLine> lines) {
    if (lines == null) {
      throw new IllegalArgumentException("Le righe ricavo missione sono obbligatorie.");
    }
    if (lines.isEmpty()) {
      throw new IllegalArgumentException("La missione deve avere almeno un ricavo.");
    }
    if (lines.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Le righe ricavo missione non possono contenere null.");
    }
    long uniqueCodes = lines.stream().map(MissionRevenueLine::getLineCode).distinct().count();
    if (uniqueCodes != lines.size()) {
      throw new IllegalArgumentException(
          "Le righe ricavo missione non possono avere codici duplicati.");
    }
    return List.copyOf(lines);
  }

  private static List<MissionCostLine> validateCostLines(List<MissionCostLine> lines) {
    if (lines == null) {
      throw new IllegalArgumentException("Le righe costo missione sono obbligatorie.");
    }
    if (lines.stream().anyMatch(Objects::isNull)) {
      throw new IllegalArgumentException("Le righe costo missione non possono contenere null.");
    }
    long uniqueCodes = lines.stream().map(MissionCostLine::getLineCode).distinct().count();
    if (uniqueCodes != lines.size()) {
      throw new IllegalArgumentException(
          "Le righe costo missione non possono avere codici duplicati.");
    }
    return List.copyOf(lines);
  }

  private static void validateCurrencyCompatibility(
      List<MissionRevenueLine> revenueLines, List<MissionCostLine> costLines) {
    Money reference = revenueLines.get(0).getAmount();
    for (int i = 1; i < revenueLines.size(); i++) {
      reference.add(revenueLines.get(i).getAmount());
    }
    for (MissionCostLine costLine : costLines) {
      reference.add(costLine.getAmount());
    }
  }

  public String getMissionNumber() {
    return missionNumber;
  }

  public String getShipmentNumber() {
    return shipmentNumber;
  }

  public List<MissionRevenueLine> getRevenueLines() {
    return revenueLines;
  }

  public List<MissionCostLine> getCostLines() {
    return costLines;
  }

  public Notes getNotes() {
    return notes;
  }

  public Money calculateTotalRevenue() {
    Money total = revenueLines.get(0).getAmount();
    for (int i = 1; i < revenueLines.size(); i++) {
      total = total.add(revenueLines.get(i).getAmount());
    }
    return total;
  }

  public Money calculateTotalCosts() {
    Currency currency = calculateTotalRevenue().getCurrency();
    if (costLines.isEmpty()) {
      return Money.of(BigDecimal.ZERO, currency);
    }
    Money total = costLines.get(0).getAmount();
    for (int i = 1; i < costLines.size(); i++) {
      total = total.add(costLines.get(i).getAmount());
    }
    return total;
  }

  public Money calculateVariableCosts() {
    List<MissionCostLine> variableCosts =
        costLines.stream().filter(MissionCostLine::isVariableOperationalCost).toList();
    if (variableCosts.isEmpty()) {
      return Money.of(BigDecimal.ZERO, calculateTotalRevenue().getCurrency());
    }
    Money total = variableCosts.get(0).getAmount();
    for (int i = 1; i < variableCosts.size(); i++) {
      total = total.add(variableCosts.get(i).getAmount());
    }
    return total;
  }

  public ProfitabilityResult calculateProfitability() {
    return ProfitabilityResult.of(calculateTotalRevenue(), calculateTotalCosts());
  }

  public boolean isProfitable() {
    return calculateProfitability().isProfitable();
  }

  public boolean isLossMaking() {
    return calculateProfitability().isLossMaking();
  }

  public boolean hasCostType(MissionCostType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo costo da cercare è obbligatorio.");
    }
    return costLines.stream().anyMatch(line -> line.getType() == type);
  }

  public boolean hasRevenueType(MissionRevenueType type) {
    if (type == null) {
      throw new IllegalArgumentException("Il tipo ricavo da cercare è obbligatorio.");
    }
    return revenueLines.stream().anyMatch(line -> line.getType() == type);
  }

  public MissionEconomics addCost(MissionCostLine costLine) {
    if (costLine == null) {
      throw new IllegalArgumentException("La riga costo da aggiungere è obbligatoria.");
    }
    List<MissionCostLine> updatedCosts = new ArrayList<>(costLines);
    updatedCosts.add(costLine);
    return new MissionEconomics(missionNumber, shipmentNumber, revenueLines, updatedCosts, notes);
  }

  public MissionEconomics addRevenue(MissionRevenueLine revenueLine) {
    if (revenueLine == null) {
      throw new IllegalArgumentException("La riga ricavo da aggiungere è obbligatoria.");
    }
    List<MissionRevenueLine> updatedRevenues = new ArrayList<>(revenueLines);
    updatedRevenues.add(revenueLine);
    return new MissionEconomics(missionNumber, shipmentNumber, updatedRevenues, costLines, notes);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MissionEconomics that)) return false;
    return missionNumber.equals(that.missionNumber)
        && shipmentNumber.equals(that.shipmentNumber)
        && revenueLines.equals(that.revenueLines)
        && costLines.equals(that.costLines)
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(missionNumber, shipmentNumber, revenueLines, costLines, notes);
  }
}
