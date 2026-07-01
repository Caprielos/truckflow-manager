package it.gabriele.truckflow.infrastructure.repository;

import it.gabriele.truckflow.infrastructure.adapter.InfrastructureAdapter;

/** Marker contract for repository adapters implemented by the infrastructure layer. */
public interface InfrastructureRepositoryAdapter extends InfrastructureAdapter {

  /**
   * Returns the application outbound port implemented by this repository adapter.
   *
   * @return simple or fully qualified port name
   */
  String implementedPortName();
}
