package it.gabriele.truckflow.infrastructure.adapter;

/** Marker contract for technical adapters owned by the infrastructure layer. */
public interface InfrastructureAdapter {

  /**
   * Returns the stable technical name of the adapter.
   *
   * @return human-readable adapter name used in diagnostics and documentation
   */
  String adapterName();
}
