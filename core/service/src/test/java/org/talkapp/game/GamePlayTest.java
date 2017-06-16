package org.talkapp.game;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.talkapp.TalkappCoreApplication;
import org.talkapp.controller.SentenceController;
import org.talkapp.controller.WordSetController;
import org.talkapp.model.GrammarCheckResult;
import org.talkapp.model.UncheckedAnswer;
import org.talkapp.model.WordSet;

import java.util.*;

import static org.talkapp.controller.UncheckedAnswerController.CHECK_METHOD;
import static org.talkapp.controller.UncheckedAnswerController.CONTROLLER_PATH;

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

    @Test
    public void test() {
        ResponseEntity<WordSet> wordSet = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + WordSetController.CONTROLLER_PATH + "/qwe0", WordSet.class);

        List<String> list = wordSet.getBody().getWords();
        Set<String> pairs = choosePairs(list);
        for (String pair : pairs) {
            Map sentence = getSentence(pair);

            UncheckedAnswer request = new UncheckedAnswer();
            request.setText((String) sentence.get("text"));
            ResponseEntity<GrammarCheckResult> entity = this.testRestTemplate.postForEntity(
                    "http://localhost:" + this.port + CONTROLLER_PATH + CHECK_METHOD, request, GrammarCheckResult.class);

            if (entity.getBody().getErrors().isEmpty()) {
                System.out.println("OK");
            } else {
                System.out.println("FAIL");
            }
        }
    }

    private Map getSentence(String pair) {
        ResponseEntity<Object> entity = this.testRestTemplate.getForEntity(
                "http://localhost:" + this.port + SentenceController.CONTROLLER_PATH + "?words=" + pair, Object.class);

        List<Map> sentences = (List<Map>) entity.getBody();
        if (sentences.size() < 2) {
            return sentences.get(0);
        }
        int i = new Random().nextInt(sentences.size() - 1);
        return sentences.get(i);
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