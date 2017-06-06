package org.talkapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class UncheckedAnswerService {
    @Autowired
    private GrammarCheckService grammarCheckService;
}