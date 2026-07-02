package it.gabriele.truckflow.infrastructure.config.spring;

import it.gabriele.truckflow.application.port.in.documents.ActivateDocumentUseCase;
import it.gabriele.truckflow.application.port.in.documents.ArchiveDocumentUseCase;
import it.gabriele.truckflow.application.port.in.documents.FindDocumentUseCase;
import it.gabriele.truckflow.application.port.in.documents.RegisterDocumentUseCase;
import it.gabriele.truckflow.application.port.out.documents.DocumentRepository;
import it.gabriele.truckflow.application.usecase.documents.ActivateDocumentService;
import it.gabriele.truckflow.application.usecase.documents.ArchiveDocumentService;
import it.gabriele.truckflow.application.usecase.documents.FindDocumentService;
import it.gabriele.truckflow.application.usecase.documents.RegisterDocumentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Spring bean wiring for document application use cases. */
@Configuration
public class DocumentUseCaseSpringConfiguration {

  @Bean
  public RegisterDocumentUseCase registerDocumentUseCase(DocumentRepository documentRepository) {
    return new RegisterDocumentService(documentRepository);
  }

  @Bean
  public FindDocumentUseCase findDocumentUseCase(DocumentRepository documentRepository) {
    return new FindDocumentService(documentRepository);
  }

  @Bean
  public ActivateDocumentUseCase activateDocumentUseCase(DocumentRepository documentRepository) {
    return new ActivateDocumentService(documentRepository);
  }

  @Bean
  public ArchiveDocumentUseCase archiveDocumentUseCase(DocumentRepository documentRepository) {
    return new ArchiveDocumentService(documentRepository);
  }
}
