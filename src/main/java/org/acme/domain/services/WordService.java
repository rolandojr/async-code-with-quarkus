package org.acme.domain.services;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.infraestructure.persistence.WordRepository;
import org.acme.infraestructure.persistence.entities.WordEntity;

import java.time.LocalDateTime;

@Slf4j
@ApplicationScoped
public class WordService {

    @Inject
    WordRepository wordRepository;

    @WithTransaction
    public Uni<String> process(String word) {
        log.info("Processing word: {}", word);
        if (word == null || word.isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Empty word"));
        }
        return Uni.createFrom().item(word.toUpperCase())
                .onItem().call(upper -> wordRepository.persist(new WordEntity(upper, LocalDateTime.now())))
                .onTermination().invoke(() -> log.info("Finished processing message with word: {}", word));
    }
}
