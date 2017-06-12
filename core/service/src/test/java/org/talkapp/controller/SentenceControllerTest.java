package org.talkapp.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.talkapp.TalkappCoreApplication;
import org.talkapp.model.Sentence;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.talkapp.controller.SentenceController.CONTROLLER_PATH;

/**
 * @author Budnikau Aliaksandr
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TalkappCoreApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class SentenceControllerTest {
    public static final String SENTENCE_1 = "Who is duty today?";
    public static final String SENTENCE_2 = "Who is duty tomorrow?";
    public static final String SENTENCE_RU_1 = "Кто сегодня дежурный?";
    public static final String RUSSIAN = "russian";
    public static final String SENTENCE_RU_2 = "Кто завтра дежурный?";
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void init() {
        Sentence sentence1 = new Sentence();
        sentence1.setText(SENTENCE_1);
        sentence1.getTranslations().put(RUSSIAN, SENTENCE_RU_1);
        this.testRestTemplate.postForEntity("http://localhost:" + this.port + CONTROLLER_PATH, sentence1, Void.class);

        Sentence sentence2 = new Sentence();
        sentence2.setText(SENTENCE_2);
        sentence2.getTranslations().put("russian", SENTENCE_RU_2);
        this.testRestTemplate.postForEntity("http://localhost:" + this.port + CONTROLLER_PATH, sentence2, Void.class);
    }

    @Test
    public void testStorageOfSentences() throws Exception {
        testFindAll();
        testFindByTodayAndDuty();
        testFindByTomorrowAndDuty();
        testFindByTomorrowAndDutyAndWho();
        testSaveAndDelete();
    }

    private void testSaveAndDelete() {
        destroy();
        init();
        testFindAll();
    }

    @After
    public void destroy() {
        this.testRestTemplate.delete("http://localhost:" + this.port + CONTROLLER_PATH);
    }

    private void testFindAll() {
        ResponseEntity<Object> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + CONTROLLER_PATH, Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(2);
        Collections.sort(body, (o1, o2) -> o1.get("text").equals(SENTENCE_1) ? -1 : 1);
        then(body.get(0).get("text")).isEqualTo(SENTENCE_1);
        Map<String, String> translations1 = (Map<String, String>) body.get(0).get("translations");
        then(translations1.get(RUSSIAN)).isEqualTo(SENTENCE_RU_1);

        then(body.get(1).get("text")).isEqualTo(SENTENCE_2);
        Map<String, String> translations2 = (Map<String, String>) body.get(1).get("translations");
        then(translations2.get(RUSSIAN)).isEqualTo(SENTENCE_RU_2);
    }

    private void testFindByTomorrowAndDuty() {
        ResponseEntity<Object> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + CONTROLLER_PATH + "?words=tomorrow duty", Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(1);
        then(body.get(0).get("text")).isEqualTo(SENTENCE_2);
        Map<String, String> translations = (Map<String, String>) body.get(0).get("translations");
        then(translations.get(RUSSIAN)).isEqualTo(SENTENCE_RU_2);
    }

    private void testFindByTomorrowAndDutyAndWho() {
        ResponseEntity<Object> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + CONTROLLER_PATH + "?words=tomorrow are duty", Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(0);
    }

    private void testFindByTodayAndDuty() {
        ResponseEntity<Object> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + CONTROLLER_PATH + "?words=today duty", Object.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Map> body = (List<Map>) entity.getBody();
        then(body.size()).isEqualTo(1);
        then(body.get(0).get("text")).isEqualTo(SENTENCE_1);
        Map<String, String> translations = (Map<String, String>) body.get(0).get("translations");
        then(translations.get(RUSSIAN)).isEqualTo(SENTENCE_RU_1);
    }
}