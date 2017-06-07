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
import org.talkapp.dto.UncheckedAnswer;
import org.talkapp.model.GrammarCheckResult;
import org.talkapp.model.GrammarError;
import org.talkapp.service.languagetool.LanguageToolConfiguration;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.talkapp.controller.UncheckedAnswerController.CHECK_METHOD;
import static org.talkapp.controller.UncheckedAnswerController.CONTROLLER_PATH;

/**
 * @author Budnikau Aliaksandr
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TalkappCoreApplication.class, LanguageToolConfiguration.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UncheckedAnswerControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void checkUncheckedAnswer() {
        testHelloWorld();
        testIAmAnEngineer();
        testWhoIsDutyToday();
        testWhoAreDutyToday();
    }

    private void testHelloWorld() {
        UncheckedAnswer request = new UncheckedAnswer();
        request.setText("Hello worlad");
        ResponseEntity<GrammarCheckResult> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + CONTROLLER_PATH + CHECK_METHOD, request, GrammarCheckResult.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<GrammarError> errors = entity.getBody().getErrors();
        then(errors).isNotEmpty();
        then(errors.size()).isEqualTo(1);
        then(errors.get(0).getBad()).isEqualTo("worlad");
        then(errors.get(0).getLength()).isEqualTo(6);
        then(errors.get(0).getOffset()).isEqualTo(6);
        then(errors.get(0).getSuggestions()).contains("world");
        then(errors.get(0).getMessage()).isNotEmpty();
    }

    private void testIAmAnEngineer() {
        UncheckedAnswer request = new UncheckedAnswer();
        request.setText("I is a enginear");
        ResponseEntity<GrammarCheckResult> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + CONTROLLER_PATH + CHECK_METHOD, request, GrammarCheckResult.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<GrammarError> errors = entity.getBody().getErrors();
        then(errors).isNotEmpty();
        then(errors.size()).isEqualTo(3);

        then(errors.get(0).getBad()).isEqualTo("is");
        then(errors.get(0).getLength()).isEqualTo(2);
        then(errors.get(0).getOffset()).isEqualTo(2);
        then(errors.get(0).getSuggestions()).contains("am");
        then(errors.get(0).getMessage()).isNotEmpty();

        then(errors.get(1).getBad()).isEqualTo("a");
        then(errors.get(1).getLength()).isEqualTo(1);
        then(errors.get(1).getOffset()).isEqualTo(5);
        then(errors.get(1).getSuggestions()).contains("an");
        then(errors.get(1).getMessage()).isNotEmpty();

        then(errors.get(2).getBad()).isEqualTo("enginear");
        then(errors.get(2).getLength()).isEqualTo(8);
        then(errors.get(2).getOffset()).isEqualTo(7);
        then(errors.get(2).getSuggestions()).contains("engineer");
        then(errors.get(2).getMessage()).isNotEmpty();
    }

    private void testWhoIsDutyToday() {
        UncheckedAnswer request = new UncheckedAnswer();
        request.setText("Who is duty today?");
        ResponseEntity<GrammarCheckResult> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + CONTROLLER_PATH + CHECK_METHOD, request, GrammarCheckResult.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<GrammarError> errors = entity.getBody().getErrors();
        then(errors).isEmpty();
    }

    private void testWhoAreDutyToday() {
        UncheckedAnswer request = new UncheckedAnswer();
        request.setText("Who are duty today?");
        ResponseEntity<GrammarCheckResult> entity = this.testRestTemplate.postForEntity(
                "http://localhost:" + this.port + CONTROLLER_PATH + CHECK_METHOD, request, GrammarCheckResult.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<GrammarError> errors = entity.getBody().getErrors();
        then(errors).isEmpty();
    }
}