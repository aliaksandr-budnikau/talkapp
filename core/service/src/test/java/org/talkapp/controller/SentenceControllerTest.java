package org.talkapp.controller;

import org.junit.After;
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
import org.talkapp.model.Sentence;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.talkapp.config.WebSecurityConfigurer.AUTHORIZATION_HEADER;
import static org.talkapp.controller.SentenceController.CONTROLLER_PATH;

/**
 * @author Budnikau Aliaksandr
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TalkappCoreApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties =
        {
                "core.srv.elasticsearch.data.files=test-data.json",
                "core.srv.elasticsearch.clusterName=talkapp-test-cluster",
                "core.srv.elasticsearch.host=localhost",
                "core.srv.elasticsearch.port=9300",
                "core.srv.elasticsearch.clearing.enabled=true"
        }
)
public class SentenceControllerTest {
    public static final String SENTENCE_1 = "Who is duty today?";
    public static final String SENTENCE_2 = "Who is duty tomorrow?";
    public static final String SENTENCE_3 = "Stay or run.";
    public static final String SENTENCE_4 = "The gray house was stayed.";
    public static final String SENTENCE_5 = "Tra ta ta.";
    public static final String RUSSIAN = "russian";
    public static final String SENTENCE_RU_1 = "Кто сегодня дежурный?";
    public static final String SENTENCE_RU_2 = "Кто завтра дежурный?";
    public static final String SENTENCE_RU_3 = "Оставайтесь или бегите.";
    public static final String SENTENCE_RU_4 = "Серый дом остался.";
    public static final String SENTENCE_RU_5 = "Тра та та.";
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

    @Test
    public void testStorageOfSentences() throws Exception {
        testFindAll();
        testFindByStay();
        testFindByStayed();
        testFindByStayedAndTwo();
        testFindByTodayAndDuty();
        testFindByTomorrowAndDuty();
        testFindByTomorrowAndDutyAndWho();
        testSave();
        testDelete();
    }

    private void testDelete() {
        this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);

        ResponseEntity<Object> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH, HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(0);
    }

    @After
    public void destroy() {
        this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
    }

    private void testSave() {
        Sentence sentence = new Sentence();
        sentence.setText(SENTENCE_5);
        sentence.getTranslations().put(RUSSIAN, SENTENCE_RU_5);

        this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH, HttpMethod.POST, new HttpEntity<>(sentence, headers), Void.class);

        ResponseEntity<Object> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH + "?words=ta tra", HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(1);
        then(body.get(0).get("text")).isEqualTo(SENTENCE_5);
        Map<String, String> translations = (Map<String, String>) body.get(0).get("translations");
        then(translations.get(RUSSIAN)).isEqualTo(SENTENCE_RU_5);
    }

    private void testFindAll() {
        ResponseEntity<Object> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH, HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(4);
        Collections.sort(body, (o1, o2) -> ((String) o1.get("text")).compareTo((String) o2.get("text")));
        then(body.get(0).get("text")).isEqualTo(SENTENCE_3);
        Map<String, String> translations1 = (Map<String, String>) body.get(0).get("translations");
        then(translations1.get(RUSSIAN)).isEqualTo(SENTENCE_RU_3);

        then(body.get(1).get("text")).isEqualTo(SENTENCE_4);
        Map<String, String> translations2 = (Map<String, String>) body.get(1).get("translations");
        then(translations2.get(RUSSIAN)).isEqualTo(SENTENCE_RU_4);

        then(body.get(2).get("text")).isEqualTo(SENTENCE_1);
        Map<String, String> translations3 = (Map<String, String>) body.get(2).get("translations");
        then(translations3.get(RUSSIAN)).isEqualTo(SENTENCE_RU_1);

        then(body.get(3).get("text")).isEqualTo(SENTENCE_2);
        Map<String, String> translations4 = (Map<String, String>) body.get(3).get("translations");
        then(translations4.get(RUSSIAN)).isEqualTo(SENTENCE_RU_2);
    }

    private void testFindByStay() {
        ResponseEntity<Object> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH + "?words=Stay", HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(2);
        Collections.sort(body, (o1, o2) -> ((String) o1.get("text")).compareTo((String) o2.get("text")));
        then(body.get(0).get("text")).isEqualTo(SENTENCE_3);
        Map<String, String> translations1 = (Map<String, String>) body.get(0).get("translations");
        then(translations1.get(RUSSIAN)).isEqualTo(SENTENCE_RU_3);

        then(body.get(1).get("text")).isEqualTo(SENTENCE_4);
        Map<String, String> translations2 = (Map<String, String>) body.get(1).get("translations");
        then(translations2.get(RUSSIAN)).isEqualTo(SENTENCE_RU_4);
    }

    private void testFindByStayed() {
        ResponseEntity<Object> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH + "?words=Stayed", HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(2);
        Collections.sort(body, (o1, o2) -> ((String) o1.get("text")).compareTo((String) o2.get("text")));
        then(body.get(0).get("text")).isEqualTo(SENTENCE_3);
        Map<String, String> translations1 = (Map<String, String>) body.get(0).get("translations");
        then(translations1.get(RUSSIAN)).isEqualTo(SENTENCE_RU_3);

        then(body.get(1).get("text")).isEqualTo(SENTENCE_4);
        Map<String, String> translations2 = (Map<String, String>) body.get(1).get("translations");
        then(translations2.get(RUSSIAN)).isEqualTo(SENTENCE_RU_4);
    }

    private void testFindByTomorrowAndDuty() {
        ResponseEntity<Object> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH + "?words=tomorrow duty", HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(1);
        then(body.get(0).get("text")).isEqualTo(SENTENCE_2);
        Map<String, String> translations = (Map<String, String>) body.get(0).get("translations");
        then(translations.get(RUSSIAN)).isEqualTo(SENTENCE_RU_2);
    }

    private void testFindByTomorrowAndDutyAndWho() {
        ResponseEntity<Object> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH + "?words=tomorrow are duty", HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(0);
    }

    private void testFindByStayedAndTwo() {
        ResponseEntity<Object> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + CONTROLLER_PATH + "?words=Stayed Two",
                HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(0);
    }

    private void testFindByTodayAndDuty() {
        ResponseEntity<Object> entity = this.testRestTemplate.exchange(
                "http://localhost:" + this.port + CONTROLLER_PATH + "?words=today duty",
                HttpMethod.GET, new HttpEntity<>(headers), Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(1);
        then(body.get(0).get("text")).isEqualTo(SENTENCE_1);
        Map<String, String> translations = (Map<String, String>) body.get(0).get("translations");
        then(translations.get(RUSSIAN)).isEqualTo(SENTENCE_RU_1);
    }
}