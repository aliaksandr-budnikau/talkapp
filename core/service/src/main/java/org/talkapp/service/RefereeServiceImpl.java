package org.talkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talkapp.model.AnswerCheckingResult;
import org.talkapp.model.UncheckedAnswer;
import org.talkapp.model.WordSet;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class RefereeServiceImpl implements RefereeService {
    @Autowired
    private GrammarCheckService grammarCheckService;
    @Autowired
    private WordSetService wordSetService;

    @Override
    public AnswerCheckingResult checkAnswer(UncheckedAnswer answer) {
        AnswerCheckingResult result = grammarCheckService.check(answer.getText());
        if (result.getErrors().isEmpty()) {
            String id = answer.getWordSetId();
            WordSet wordSet = wordSetService.findById(id);
            int experience = wordSet.getTrainingExperience() + 1;
            wordSet.setTrainingExperience(experience);
            wordSetService.save(wordSet);
            result.setCurrentTrainingExperience(experience);
        }
        return result;
    }
}