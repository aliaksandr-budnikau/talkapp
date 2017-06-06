package org.talkapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.talkapp.TalkappCoreApplication;
import org.talkapp.dto.UncheckedAnswer;
import org.talkapp.model.GrammarCheckResult;
import org.talkapp.service.languagetool.LanguageToolConfiguration;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * @author Budnikau Aliaksandr
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TalkappCoreApplication.class, LanguageToolConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UncheckedAnswerControllerTest {

    @LocalServerPort
    private int port;
    @Value("${local.management.port}")
    private int mgt;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void checkUncheckedAnswer() {
        UncheckedAnswer request = new UncheckedAnswer();
        request.setText("Hello worlad");
        ResponseEntity<GrammarCheckResult> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + "/answers/check", request, GrammarCheckResult.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}