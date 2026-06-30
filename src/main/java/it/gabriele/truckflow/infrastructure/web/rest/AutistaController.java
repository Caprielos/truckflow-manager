package it.gabriele.truckflow.infrastructure.web.rest;

import it.gabriele.truckflow.application.autisti.AutistaService;
import it.gabriele.truckflow.domain.autisti.Autista;
import it.gabriele.truckflow.domain.autisti.Patente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autisti")
public class AutistaController {

  private final AutistaService autistaService;

  public AutistaController(AutistaService autistaService) {
    this.autistaService = autistaService;
  }

  @GetMapping
  public List<Autista> trovaTutti() {
    return autistaService.trovaTutti();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Autista> trovaPerId(@PathVariable UUID id) {
    return autistaService
        .trovaPerId(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Autista> crea(@Valid @RequestBody CreaAutistaRequest request) {
    Autista autista =
        autistaService.crea(
            request.nome(), request.cognome(), request.dataNascita(), request.patenti());

    URI location = URI.create("/api/autisti/" + autista.id());
    return ResponseEntity.created(location).body(autista);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> elimina(@PathVariable UUID id) {
    autistaService.elimina(id);
    return ResponseEntity.noContent().build();
  }

  public record CreaAutistaRequest(
      @NotBlank String nome,
      @NotBlank String cognome,
      @NotNull LocalDate dataNascita,
      Set<Patente> patenti) {}
}
