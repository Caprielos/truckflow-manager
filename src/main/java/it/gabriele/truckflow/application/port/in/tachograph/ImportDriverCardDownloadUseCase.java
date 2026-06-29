package it.gabriele.truckflow.application.port.in.tachograph;

import it.gabriele.truckflow.domain.tachograph.DriverCardDownload;

public interface ImportDriverCardDownloadUseCase {
  DriverCardDownload handle(Command command);

  record Command(DriverCardDownload download) {}
}
