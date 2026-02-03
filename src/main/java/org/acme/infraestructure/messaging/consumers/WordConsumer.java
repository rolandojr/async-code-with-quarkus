package org.acme.infraestructure.messaging.consumers;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.domain.services.WordService;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;

@Slf4j
@ApplicationScoped
public class WordConsumer {

    @Inject
    WordService wordService;

    @Incoming("words-in")
    public Uni<Void> consume(Message<String> message) {
        return wordService.process(message.getPayload())
                .onItem().transformToUni(res -> Uni.createFrom().completionStage(message.ack()))
                .onFailure().recoverWithUni(err -> Uni.createFrom().completionStage(message.nack(err)));
    }
}
