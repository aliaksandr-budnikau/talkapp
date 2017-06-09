package org.talkapp.service;

import com.google.cloud.speech.spi.v1beta1.SpeechClient;
import com.google.cloud.speech.v1beta1.RecognitionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Budnikau Aliaksandr
 */
@Configuration
public class VoiceServiceConfiguration {
    @Bean
    public RecognitionConfig recognitionConfig() {
        return RecognitionConfig.newBuilder()
                .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                .setLanguageCode("en-US")
                .setSampleRate(16000)
                .build();
    }

    @Bean(destroyMethod = "close")
    public SpeechClient speechClient() throws IOException {
        return SpeechClient.create();
    }
}