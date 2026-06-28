package it.gabriele.truckflow.domain.document;

/**
 * Tipo di documento collegato al trasporto.
 */
public enum TransportDocumentType {

    CMR_WAYBILL(true, false, false, false, false),
    PROOF_OF_DELIVERY(true, false, false, true, false),
    DELIVERY_NOTE(true, false, false, false, false),
    ADR_TRANSPORT_DOCUMENT(true, false, true, false, false),
    TEMPERATURE_LOG(true, false, false, false, false),
    INVOICE_COPY(false, true, false, false, false),
    INSURANCE_CERTIFICATE(false, false, false, false, true),
    VEHICLE_REGISTRATION(false, false, false, false, true),
    DRIVER_LICENSE_COPY(false, false, false, false, true),
    WASTE_IDENTIFICATION_FORM(true, false, false, false, false),
    SAFETY_DATA_SHEET(true, false, true, false, false),
    ADR_WRITTEN_INSTRUCTIONS(true, false, true, false, false),
    HACCP_SANITATION_DOCUMENT(true, false, false, false, true),
    VETERINARY_DOCUMENT(true, false, false, false, true),
    OVERSIZED_TRANSPORT_AUTHORIZATION(true, false, false, false, true);

    private final boolean shipmentRelated;
    private final boolean invoiceRelated;
    private final boolean requiredForAdr;
    private final boolean proofOfDelivery;
    private final boolean expirable;

    TransportDocumentType(
            boolean shipmentRelated,
            boolean invoiceRelated,
            boolean requiredForAdr,
            boolean proofOfDelivery,
            boolean expirable
    ) {
        this.shipmentRelated = shipmentRelated;
        this.invoiceRelated = invoiceRelated;
        this.requiredForAdr = requiredForAdr;
        this.proofOfDelivery = proofOfDelivery;
        this.expirable = expirable;
    }

    public boolean isShipmentRelated() {
        return shipmentRelated;
    }

    public boolean isInvoiceRelated() {
        return invoiceRelated;
    }

    public boolean isRequiredForAdr() {
        return requiredForAdr;
    }

    public boolean isProofOfDelivery() {
        return proofOfDelivery;
    }

    public boolean isExpirable() {
        return expirable;
    }
}
