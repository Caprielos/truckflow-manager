package it.gabriele.truckflow.domain.vehicles.body;

public sealed interface VehicleBodyProfile
    permits RefrigeratedBodyProfile,
        TankBodyProfile,
        CarCarrierBodyProfile,
        ContainerChassisBodyProfile,
        LowLoaderBodyProfile,
        CurtainsiderBodyProfile,
        TipperBodyProfile,
        SiloBodyProfile,
        LivestockBodyProfile {

  VehicleBodyType bodyType();

  String notes();
}
