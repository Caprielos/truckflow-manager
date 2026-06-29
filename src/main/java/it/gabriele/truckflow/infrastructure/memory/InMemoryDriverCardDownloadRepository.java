package it.gabriele.truckflow.infrastructure.memory;

import it.gabriele.truckflow.application.port.out.DriverCardDownloadRepository;
import it.gabriele.truckflow.domain.tachograph.DriverCardDownload;

/** Repository in memoria per DriverCardDownload. */
public final class InMemoryDriverCardDownloadRepository
    extends InMemoryRepository<DriverCardDownload> implements DriverCardDownloadRepository {

  public InMemoryDriverCardDownloadRepository() {
    super(download -> download.downloadCode());
  }
}
