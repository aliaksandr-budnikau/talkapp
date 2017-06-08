package org.talkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talkapp.model.GrammarCheckResult;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class UncheckedAnswerServiceImpl implements UncheckedAnswerService {
    @Autowired
    private GrammarCheckService grammarCheckService;

    @Override
    public GrammarCheckResult check(String text) {
        return grammarCheckService.check(text);
    }
}