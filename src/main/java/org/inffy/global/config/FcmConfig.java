package org.inffy.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.inffy.global.exception.entity.RestApiException;
import org.inffy.global.exception.error.CustomErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Slf4j
@Configuration
public class FcmConfig {

    @Value("${firebase.config.path}")
    private String configPath;

    @Bean
    public FirebaseApp firebaseApp(){
        try {
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(
                            new ClassPathResource(configPath).getInputStream()
                    )).build();

            return FirebaseApp.initializeApp(firebaseOptions);
        }catch (IOException e){
            throw new RestApiException(CustomErrorCode.FCM_INITIALIZE_FAILED);
        }
    }

    @Bean
    FirebaseMessaging firebaseMessaging() {
        return FirebaseMessaging.getInstance(FirebaseApp.getInstance());
    }
}