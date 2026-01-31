package org.acme.infraestructure.persistence;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.infraestructure.persistence.entities.WordEntity;

@ApplicationScoped
public class WordRepository implements PanacheRepository<WordEntity> {
}
