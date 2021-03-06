package org.talkapp.service;

import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talkapp.model.AnswerCheckingResult;
import org.talkapp.model.GrammarError;

import java.io.IOException;
import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class GrammarCheckServiceImpl implements GrammarCheckService {
    @Autowired
    private JLanguageTool languageTool;
    @Autowired
    private Logger logger;

    @Override
    public AnswerCheckingResult check(String text) {
        logger.debug("Checking : " + text);
        try {
            List<RuleMatch> ruleMatches = languageTool.check(text);
            AnswerCheckingResult result = new AnswerCheckingResult();
            for (RuleMatch ruleMatch : ruleMatches) {
                GrammarError error = new GrammarError();
                result.getErrors().add(error);
                error.setMessage(ruleMatch.getShortMessage());
                String bad = text.substring(ruleMatch.getFromPos(), ruleMatch.getToPos());
                error.setBad(bad);
                error.setOffset(ruleMatch.getFromPos());
                error.setLength(ruleMatch.getToPos() - ruleMatch.getFromPos());
                error.setSuggestions(ruleMatch.getSuggestedReplacements());
            }
            logger.debug("Checking result: " + result);
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}