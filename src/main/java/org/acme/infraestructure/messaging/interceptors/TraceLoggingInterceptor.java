package org.acme.infraestructure.messaging.interceptors;

import io.smallrye.reactive.messaging.IncomingInterceptor;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.MDC;

import java.util.UUID;

@ApplicationScoped
public class TraceLoggingInterceptor implements IncomingInterceptor {

    private static final String TRACE_ID_KEY = "traceId";


    @Override
    public Message<?> afterMessageReceive(Message<?> message) {
        String traceId = UUID.randomUUID().toString();
        MDC.put(TRACE_ID_KEY, traceId);
        return message;
    }

    @Override
    public void onMessageAck(Message<?> message) {
        MDC.remove(TRACE_ID_KEY);
    }

    @Override
    public void onMessageNack(Message<?> message, Throwable failure) {
        MDC.remove(TRACE_ID_KEY);
    }
}
