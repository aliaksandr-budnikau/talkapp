package org.talkapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talkapp.model.GrammarCheckResult;
import org.talkapp.service.GrammarCheckService;
import org.talkapp.service.UncheckedAnswerService;

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