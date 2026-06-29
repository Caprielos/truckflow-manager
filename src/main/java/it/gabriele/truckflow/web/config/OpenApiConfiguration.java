package it.gabriele.truckflow.web.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

/** Configurazione generale della documentazione OpenAPI di TruckFlow Manager. */
@Configuration
@OpenAPIDefinition(
    info =
        @Info(
            title = "TruckFlow Manager API",
            version = "0.1.0",
            description =
                "API REST didattiche e operative per gestione trasporti, flotta, parcheggi, "
                    + "missioni e processi aziendali TruckFlow.",
            contact = @Contact(name = "Gabriele Di Egidio"),
            license = @License(name = "MIT")),
    tags = {
      @Tag(
          name = "Parking",
          description = "API per consultare posti parcheggio e assegnare risorse in piazzale")
    })
public class OpenApiConfiguration {}
