package it.gabriele.truckflow.web.parking;

import it.gabriele.truckflow.domain.parking.ParkingSpot;

/**
 * DTO di response per mostrare un posto parcheggio disponibile tramite API REST.
 */
public record ParkingSpotResponse(
        String facilityCode,
        String spotNumber,
        String type,
        String status,
        double maxLengthMeters,
        double maxWidthMeters,
        boolean powerSupplyAvailable,
        String notes
) {

    public static ParkingSpotResponse fromDomain(ParkingSpot spot) {
        return new ParkingSpotResponse(
                spot.getFacilityCode(),
                spot.getSpotNumber(),
                spot.getType().name(),
                spot.getStatus().name(),
                spot.getMaxLengthMeters(),
                spot.getMaxWidthMeters(),
                spot.isPowerSupplyAvailable(),
                spot.getNotes().getText()
        );
    }
}
