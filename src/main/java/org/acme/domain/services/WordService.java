package org.acme.domain.services;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.domain.models.avro.WordRecord;
import org.acme.infraestructure.persistence.WordRepository;
import org.acme.infraestructure.persistence.entities.WordEntity;

import java.time.LocalDateTime;

@Slf4j
@ApplicationScoped
public class WordService {

    @Inject
    WordRepository wordRepository;

    @WithTransaction
    public Uni<String> process(WordRecord record) {
        log.info("Processing record: {}", record);
        if (record == null || record.getWord().isEmpty()) {
            return Uni.createFrom().failure(new IllegalArgumentException("Empty word"));
        }
        String word = record.getWord();
        return Uni.createFrom().item(word.toUpperCase())
                .onItem().call(upper -> wordRepository.persist(new WordEntity(upper, LocalDateTime.now())))
                .onTermination().invoke(() -> log.info("Finished processing message with word: {}", word));
    }
}
