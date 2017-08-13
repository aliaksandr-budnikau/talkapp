package org.talkapp.game;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.talkapp.TalkappCoreApplication;
import org.talkapp.controller.LoginController;
import org.talkapp.controller.SentenceController;
import org.talkapp.controller.WordSetController;
import org.talkapp.controller.WordSetExperienceController;
import org.talkapp.model.*;

import java.util.*;

import static org.junit.Assert.fail;
import static org.talkapp.config.WebSecurityConfigurer.AUTHORIZATION_HEADER;
import static org.talkapp.controller.RefereeController.CHECK_METHOD;
import static org.talkapp.controller.RefereeController.CONTROLLER_PATH;
import static org.talkapp.repository.impl.WordSetRepositoryImpl.QWE_0;

/**
 * @author Budnikau Aliaksandr
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TalkappCoreApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties =
        {
                "core.srv.elasticsearch.data.files=data.json",
                "core.srv.elasticsearch.clusterName=talkapp-test-cluster",
                "core.srv.elasticsearch.host=localhost",
                "core.srv.elasticsearch.port=9300",
                "core.srv.elasticsearch.clearing.enabled=true"
        }
)
public class GamePlayTest {

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
    public void test() {
        ResponseEntity<WordSet> wordSet = this.testRestTemplate.exchange("http://localhost:" + this.port + WordSetController.CONTROLLER_PATH + "/" + QWE_0, HttpMethod.GET, new HttpEntity<>(headers), WordSet.class);

        ResponseEntity<WordSetExperience> exp = this.testRestTemplate.exchange("http://localhost:" + this.port + WordSetExperienceController.CONTROLLER_PATH + "/" + QWE_0, HttpMethod.GET, new HttpEntity<>(headers), WordSetExperience.class);

        List<String> list = wordSet.getBody().getWords();
        Set<String> pairs = choosePairs(list);
        int expectedExperience = 0;
        for (String pair : pairs) {
            Map<String, String> sentence = getSentence(pair);

            UncheckedAnswer request = new UncheckedAnswer();
            request.setActualAnswer(sentence.get("text"));
            request.setExpectedAnswer(sentence.get("text"));
            request.setWordSetExperienceId(exp.getBody().getId());

            ResponseEntity<AnswerCheckingResult> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + CONTROLLER_PATH + CHECK_METHOD, HttpMethod.POST, new HttpEntity<>(request, headers), AnswerCheckingResult.class);

            if (entity.getBody().getErrors().isEmpty()) {
                expectedExperience++;
                if (entity.getBody().getCurrentTrainingExperience() != expectedExperience) {
                    fail();
                    return;
                }
                System.out.println("ok");
                if (entity.getBody().getCurrentTrainingExperience() == exp.getBody().getMaxTrainingExperience()) {
                    System.out.println("OK");
                }
            } else {
                fail();
                return;
            }
        }
    }

    private Map<String, String> getSentence(String pair) {
        ResponseEntity<List> entity = this.testRestTemplate.exchange("http://localhost:" + this.port + SentenceController.CONTROLLER_PATH + "?words=" + pair, HttpMethod.GET, new HttpEntity<>(headers), List.class);

        List<Map<String, String>> sentences = (List<Map<String, String>>) entity.getBody();
        if (sentences.size() < 2) {
            return sentences.get(0);
        }
        int i = new Random().nextInt(sentences.size() - 1);
        return sentences.get(i);
    }

    @After
    public void destroy() {
        this.testRestTemplate.exchange("http://localhost:" + this.port + SentenceController.CONTROLLER_PATH, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
    }

    @NotNull
    private Set<String> choosePairs(List<String> list) {
        Set<String> pairs = new LinkedHashSet<>();
        Random random = new Random();
        for (int i = list.size() - 1; i > 1; i--) {
            String word = list.get(i);
            String pair = word + " ";
            pair += list.get(random.nextInt(i - 1));
            pairs.add(pair);
        }
        return pairs;
    }
}