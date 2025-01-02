package org.inffy.domain.chat.handler;

import lombok.RequiredArgsConstructor;
import org.inffy.global.security.jwt.util.JwtTokenProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel messageChannel){

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message); // 메시지에서 해더 추출

        if(accessor.getCommand() == StompCommand.CONNECT) { // 해당 메시지가 Stomp 연결 요청이라면,
            String authorization = jwtTokenProvider.getJwtFromStompRequest(accessor);
            jwtTokenProvider.validateStompJwt(authorization);
        }

        return message;
    }
}
