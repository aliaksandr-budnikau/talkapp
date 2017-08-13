package org.talkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talkapp.model.AnswerCheckingResult;
import org.talkapp.model.UncheckedAnswer;
import org.talkapp.model.WordSetExperience;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class RefereeServiceImpl implements RefereeService {
    @Autowired
    private GrammarCheckService grammarCheckService;
    @Autowired
    private WordSetExperienceService wordSetExperienceService;
    @Autowired
    private EqualityScorer equalityScorer;

    @Override
    public AnswerCheckingResult checkAnswer(UncheckedAnswer answer) {
        AnswerCheckingResult result = grammarCheckService.check(answer.getActualAnswer());
        if (result.getErrors().isEmpty()) {
            if (equalityScorer.score(answer.getExpectedAnswer(), answer.getActualAnswer()) < 80) {
                return result;
            }
            String id = answer.getWordSetExperienceId();
            WordSetExperience wordSetExperience = wordSetExperienceService.findById(id);
            int experience = wordSetExperience.getTrainingExperience() + 1;
            wordSetExperience.setTrainingExperience(experience);
            wordSetExperienceService.save(wordSetExperience);
            result.setCurrentTrainingExperience(experience);
        }
        return result;
    }
}