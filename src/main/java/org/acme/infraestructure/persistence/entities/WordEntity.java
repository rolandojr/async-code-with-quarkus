package org.acme.infraestructure.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private LocalDateTime createdAt;

    public WordEntity() {
    }

    public WordEntity(String word, LocalDateTime createdAt) {
        this.word = word;
        this.createdAt = createdAt;
    }

}
