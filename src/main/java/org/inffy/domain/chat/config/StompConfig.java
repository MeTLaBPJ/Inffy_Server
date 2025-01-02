package org.inffy.domain.chat.config;

import lombok.RequiredArgsConstructor;
import org.inffy.global.exception.handler.StompExceptionHandler;
import org.inffy.domain.chat.handler.StompHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    private final StompHandler stompHandler; // Stomp 연결 과정에서 Jwt 검증 유틸
    private final StompExceptionHandler stompExceptionHandler;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry){
        registry.enableSimpleBroker("/sub"); // 채팅룸 구독 Prefix
        registry.setApplicationDestinationPrefixes("/pub"); // 채탱 전송 Prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        registry.setErrorHandler(stompExceptionHandler) // stompHandler 에서 발생한 에러 처리
                .addEndpoint("/ws") // 핸드셰이크 경로
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration){
        registration.interceptors(stompHandler); // STOMP 연결 시 Jwt 인증 유틸 등록
    }
}