package org.talkapp.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.talkapp.TalkappCoreApplication;
import org.talkapp.model.LoginCredentials;
import org.talkapp.model.UnrecognizedVoice;
import org.talkapp.model.VoiceRecognitionResult;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.talkapp.config.WebSecurityConfigurer.AUTHORIZATION_HEADER;
import static org.talkapp.controller.VoiceServiceController.CONTROLLER_PATH;
import static org.talkapp.controller.VoiceServiceController.RECOGNIZE_METHOD;

/**
 * @author Budnikau Aliaksandr
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TalkappCoreApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties =
        {
                "core.srv.elasticsearch.data.files=",
                "core.srv.elasticsearch.clusterName=talkapp-test-cluster",
                "core.srv.elasticsearch.host=localhost",
                "core.srv.elasticsearch.port=9300",
        }
)
public class VoiceServiceControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;
    private HttpHeaders headers;

    @Before
    public void init() {
        LoginCredentials loginCredentials = new LoginCredentials();
        loginCredentials.setEmail("sasha-ne@tut.by");
        loginCredentials.setPassword("password0");

        ResponseEntity<Boolean> login = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + LoginController.CONTROLLER_PATH, loginCredentials, Boolean.class);

        List<String> sign = login.getHeaders().get(AUTHORIZATION_HEADER);

        headers = new HttpHeaders();
        headers.put(AUTHORIZATION_HEADER, sign);
    }

    @Test(timeout=10000)
    public void checkAnswer() throws Exception {
        ClassLoader classLoader = this.getClass().getClassLoader();
        URI uri = classLoader.getResource("audio.raw").toURI();
        byte[] data = Files.readAllBytes(Paths.get(uri));
        UnrecognizedVoice request = new UnrecognizedVoice();
        request.setVoice(data);

        ResponseEntity<VoiceRecognitionResult> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH + RECOGNIZE_METHOD, HttpMethod.POST, new HttpEntity<>(request, headers), VoiceRecognitionResult.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().getVariant().size()).isEqualTo(1);
        then(entity.getBody().getVariant().get(0)).isEqualTo("how old is the Brooklyn Bridge");
    }
}