package it.gabriele.truckflow.domain.order;

import it.gabriele.truckflow.domain.cargo.CargoCategory;
import it.gabriele.truckflow.domain.cargo.CargoLoad;
import it.gabriele.truckflow.domain.customer.CustomerAccount;
import it.gabriele.truckflow.domain.facility.Facility;
import it.gabriele.truckflow.domain.shared.Money;
import it.gabriele.truckflow.domain.shared.Notes;
import it.gabriele.truckflow.domain.shared.TimeWindow;
import java.util.Objects;

/** Rappresenta un ordine di trasporto richiesto da un cliente. */
public final class TransportOrder {

  private static final int MAX_ORDER_NUMBER_LENGTH = 50;

  private final String orderNumber;
  private final CustomerAccount customerAccount;
  private final CargoLoad cargoLoad;
  private final Facility pickupFacility;
  private final Facility deliveryFacility;
  private final TimeWindow pickupWindow;
  private final TimeWindow deliveryWindow;
  private final TransportServiceType serviceType;
  private final Money quotedPrice;
  private final TransportOrderStatus status;
  private final Notes notes;

  private TransportOrder(
      String orderNumber,
      CustomerAccount customerAccount,
      CargoLoad cargoLoad,
      Facility pickupFacility,
      Facility deliveryFacility,
      TimeWindow pickupWindow,
      TimeWindow deliveryWindow,
      TransportServiceType serviceType,
      Money quotedPrice,
      TransportOrderStatus status,
      Notes notes) {
    this.orderNumber = validateOrderNumber(orderNumber);

    if (customerAccount == null) {
      throw new IllegalArgumentException("Il cliente dell'ordine è obbligatorio.");
    }

    if (cargoLoad == null) {
      throw new IllegalArgumentException("Il carico dell'ordine è obbligatorio.");
    }

    if (pickupFacility == null) {
      throw new IllegalArgumentException("La struttura di ritiro è obbligatoria.");
    }

    if (deliveryFacility == null) {
      throw new IllegalArgumentException("La struttura di consegna è obbligatoria.");
    }

    if (pickupFacility.equals(deliveryFacility)) {
      throw new IllegalArgumentException(
          "La struttura di ritiro e consegna non possono essere uguali.");
    }

    if (pickupWindow == null) {
      throw new IllegalArgumentException("La finestra oraria di ritiro è obbligatoria.");
    }

    if (deliveryWindow == null) {
      throw new IllegalArgumentException("La finestra oraria di consegna è obbligatoria.");
    }

    if (serviceType == null) {
      throw new IllegalArgumentException("Il tipo di servizio è obbligatorio.");
    }

    if (quotedPrice == null) {
      throw new IllegalArgumentException("Il prezzo quotato è obbligatorio.");
    }

    if (status == null) {
      throw new IllegalArgumentException("Lo stato dell'ordine è obbligatorio.");
    }

    if (notes == null) {
      throw new IllegalArgumentException("Le note dell'ordine sono obbligatorie.");
    }

    this.customerAccount = customerAccount;
    this.cargoLoad = cargoLoad;
    this.pickupFacility = pickupFacility;
    this.deliveryFacility = deliveryFacility;
    this.pickupWindow = pickupWindow;
    this.deliveryWindow = deliveryWindow;
    this.serviceType = serviceType;
    this.quotedPrice = quotedPrice;
    this.status = status;
    this.notes = notes;
  }

  public static TransportOrder draft(
      String orderNumber,
      CustomerAccount customerAccount,
      CargoLoad cargoLoad,
      Facility pickupFacility,
      Facility deliveryFacility,
      TimeWindow pickupWindow,
      TimeWindow deliveryWindow,
      TransportServiceType serviceType,
      Money quotedPrice,
      Notes notes) {
    return new TransportOrder(
        orderNumber,
        customerAccount,
        cargoLoad,
        pickupFacility,
        deliveryFacility,
        pickupWindow,
        deliveryWindow,
        serviceType,
        quotedPrice,
        TransportOrderStatus.DRAFT,
        notes);
  }

  public static TransportOrder submitted(
      String orderNumber,
      CustomerAccount customerAccount,
      CargoLoad cargoLoad,
      Facility pickupFacility,
      Facility deliveryFacility,
      TimeWindow pickupWindow,
      TimeWindow deliveryWindow,
      TransportServiceType serviceType,
      Money quotedPrice,
      Notes notes) {
    return draft(
            orderNumber,
            customerAccount,
            cargoLoad,
            pickupFacility,
            deliveryFacility,
            pickupWindow,
            deliveryWindow,
            serviceType,
            quotedPrice,
            notes)
        .submit();
  }

  private static String validateOrderNumber(String orderNumber) {
    if (orderNumber == null) {
      throw new IllegalArgumentException("Il numero ordine è obbligatorio.");
    }

    String normalizedOrderNumber = orderNumber.trim().toUpperCase();

    if (normalizedOrderNumber.isEmpty()) {
      throw new IllegalArgumentException("Il numero ordine non può essere vuoto.");
    }

    if (normalizedOrderNumber.length() > MAX_ORDER_NUMBER_LENGTH) {
      throw new IllegalArgumentException(
          "Il numero ordine non può superare " + MAX_ORDER_NUMBER_LENGTH + " caratteri.");
    }

    if (!normalizedOrderNumber.matches("[A-Z0-9_-]+")) {
      throw new IllegalArgumentException(
          "Il numero ordine può contenere solo lettere, numeri, trattini e underscore.");
    }

    return normalizedOrderNumber;
  }

  public String getOrderNumber() {
    return orderNumber;
  }

  public CustomerAccount getCustomerAccount() {
    return customerAccount;
  }

  public CargoLoad getCargoLoad() {
    return cargoLoad;
  }

  public Facility getPickupFacility() {
    return pickupFacility;
  }

  public Facility getDeliveryFacility() {
    return deliveryFacility;
  }

  public TimeWindow getPickupWindow() {
    return pickupWindow;
  }

  public TimeWindow getDeliveryWindow() {
    return deliveryWindow;
  }

  public TransportServiceType getServiceType() {
    return serviceType;
  }

  public Money getQuotedPrice() {
    return quotedPrice;
  }

  public TransportOrderStatus getStatus() {
    return status;
  }

  public Notes getNotes() {
    return notes;
  }

  public boolean isInternational() {
    return !pickupFacility
        .getLocation()
        .getAddress()
        .getCountryCode()
        .equals(deliveryFacility.getLocation().getAddress().getCountryCode());
  }

  public boolean requiresTemperatureControlledTransport() {
    return cargoLoad.requiresTemperatureControl();
  }

  public boolean containsHazardousMaterial() {
    return cargoLoad.hasCategory(CargoCategory.HAZARDOUS_MATERIAL);
  }

  public boolean isServiceCompatibleWithCargo() {
    if (requiresTemperatureControlledTransport() && !serviceType.supportsTemperatureControl()) {
      return false;
    }

    if (containsHazardousMaterial() && !serviceType.supportsHazardousMaterial()) {
      return false;
    }

    return true;
  }

  public boolean canBeSubmitted() {
    return status == TransportOrderStatus.DRAFT
        && customerAccount.canRequestTransportOrder()
        && isServiceCompatibleWithCargo();
  }

  public boolean canBeAccepted() {
    return status == TransportOrderStatus.SUBMITTED
        && customerAccount.canRequestTransportOrder()
        && isServiceCompatibleWithCargo();
  }

  public boolean canBeCancelled() {
    return !status.isTerminal();
  }

  public TransportOrder submit() {
    if (!canBeSubmitted()) {
      throw new IllegalStateException("L'ordine non può essere inviato.");
    }

    return withStatus(TransportOrderStatus.SUBMITTED);
  }

  public TransportOrder accept() {
    if (!canBeAccepted()) {
      throw new IllegalStateException("L'ordine non può essere accettato.");
    }

    return withStatus(TransportOrderStatus.ACCEPTED);
  }

  public TransportOrder reject() {
    if (status != TransportOrderStatus.SUBMITTED) {
      throw new IllegalStateException("Solo un ordine inviato può essere rifiutato.");
    }

    return withStatus(TransportOrderStatus.REJECTED);
  }

  public TransportOrder cancel() {
    if (!canBeCancelled()) {
      throw new IllegalStateException("L'ordine non può essere cancellato.");
    }

    return withStatus(TransportOrderStatus.CANCELLED);
  }

  private TransportOrder withStatus(TransportOrderStatus newStatus) {
    return new TransportOrder(
        orderNumber,
        customerAccount,
        cargoLoad,
        pickupFacility,
        deliveryFacility,
        pickupWindow,
        deliveryWindow,
        serviceType,
        quotedPrice,
        newStatus,
        notes);
  }

  public String formatSingleLine() {
    return orderNumber
        + " - "
        + customerAccount.getCustomerCode()
        + " - "
        + serviceType
        + " - "
        + status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TransportOrder that)) return false;
    return orderNumber.equals(that.orderNumber)
        && customerAccount.equals(that.customerAccount)
        && cargoLoad.equals(that.cargoLoad)
        && pickupFacility.equals(that.pickupFacility)
        && deliveryFacility.equals(that.deliveryFacility)
        && pickupWindow.equals(that.pickupWindow)
        && deliveryWindow.equals(that.deliveryWindow)
        && serviceType == that.serviceType
        && quotedPrice.equals(that.quotedPrice)
        && status == that.status
        && notes.equals(that.notes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        orderNumber,
        customerAccount,
        cargoLoad,
        pickupFacility,
        deliveryFacility,
        pickupWindow,
        deliveryWindow,
        serviceType,
        quotedPrice,
        status,
        notes);
  }

  @Override
  public String toString() {
    return formatSingleLine();
  }
}
