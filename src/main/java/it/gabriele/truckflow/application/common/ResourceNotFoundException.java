package it.gabriele.truckflow.application.common;

/**
 * Eccezione applicativa per risorse richieste da un caso d'uso ma non presenti nel repository.
 */
public final class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String id) {
        super(resourceName + " non trovato: " + id);
    }
}
