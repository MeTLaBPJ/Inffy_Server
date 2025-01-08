package org.inffy.global.exception.handler;

import org.inffy.global.exception.entity.StompJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
public class StompExceptionHandler extends StompSubProtocolErrorHandler {

    private static final byte[] EMPTY_PAYLOAD = new byte[0];

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {
        Throwable exception = convertThrowableException(ex);

        if (exception instanceof StompJwtException) {
            return handleJwtException(clientMessage, exception);
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Throwable convertThrowableException(Throwable ex) {
        return (ex instanceof MessageDeliveryException) ? ex.getCause() : ex;
    }

    private Message<byte[]> handleJwtException(Message<byte[]> clientMessage, Throwable ex) {
        return prepareErrorMessage(clientMessage, ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    private Message<byte[]> prepareErrorMessage(Message<byte[]> clientMessage, String message, HttpStatus httpStatus) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setMessage(httpStatus.toString());
        accessor.setLeaveMutable(true);

        setReceiptIdForClient(clientMessage, accessor);

        byte[] payload = message != null ? message.getBytes(StandardCharsets.UTF_8) : EMPTY_PAYLOAD;
        return MessageBuilder.createMessage(payload, accessor.getMessageHeaders());
    }

    private void setReceiptIdForClient(Message<byte[]> clientMessage, StompHeaderAccessor accessor) {
        if (Objects.isNull(clientMessage)) {
            return;
        }

        StompHeaderAccessor clientHeaderAccessor = MessageHeaderAccessor.getAccessor(
                clientMessage, StompHeaderAccessor.class);

        if (Objects.nonNull(clientHeaderAccessor)) {
            String receiptId = clientHeaderAccessor.getReceipt();
            if (Objects.nonNull(receiptId)) {
                accessor.setReceiptId(receiptId);
            }
        }
    }

    @Override
    protected Message<byte[]> handleInternal(StompHeaderAccessor errorHeaderAccessor,
                                             byte[] errorPayload, Throwable cause, StompHeaderAccessor clientHeaderAccessor) {
        return MessageBuilder.createMessage(errorPayload, errorHeaderAccessor.getMessageHeaders());
    }
}