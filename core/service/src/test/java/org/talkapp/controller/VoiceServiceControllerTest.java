package org.talkapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.talkapp.TalkappCoreApplication;
import org.talkapp.model.UnrecognizedVoice;
import org.talkapp.model.VoiceRecognitionResult;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.BDDAssertions.then;
import static org.talkapp.controller.VoiceServiceController.CONTROLLER_PATH;
import static org.talkapp.controller.VoiceServiceController.RECOGNIZE_METHOD;

/**
 * @author Budnikau Aliaksandr
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TalkappCoreApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VoiceServiceControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void checkAnswer() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URI uri = classLoader.getResource("audio.raw").toURI();
        byte[] data = Files.readAllBytes(Paths.get(uri));
        UnrecognizedVoice request = new UnrecognizedVoice();
        request.setVoice(data);
        ResponseEntity<VoiceRecognitionResult> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + CONTROLLER_PATH + RECOGNIZE_METHOD, request, VoiceRecognitionResult.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().getVariant().size()).isEqualTo(1);
        then(entity.getBody().getVariant().get(0)).isEqualTo("how old is the Brooklyn Bridge");
    }
}