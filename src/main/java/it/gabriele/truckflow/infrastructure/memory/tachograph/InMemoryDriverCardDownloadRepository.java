package it.gabriele.truckflow.infrastructure.memory.tachograph;

import it.gabriele.truckflow.application.port.out.tachograph.DriverCardDownloadRepository;
import it.gabriele.truckflow.domain.tachograph.DriverCardDownload;
import it.gabriele.truckflow.infrastructure.memory.InMemoryRepository;

/** Repository in memoria per DriverCardDownload. */
public final class InMemoryDriverCardDownloadRepository
    extends InMemoryRepository<DriverCardDownload> implements DriverCardDownloadRepository {

  public InMemoryDriverCardDownloadRepository() {
    super(download -> download.downloadCode());
  }
}
