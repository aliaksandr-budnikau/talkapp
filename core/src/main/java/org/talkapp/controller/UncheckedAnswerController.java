package org.talkapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.talkapp.dto.UncheckedAnswer;
import org.talkapp.model.GrammarCheckResult;
import org.talkapp.service.GrammarCheckService;

/**
 * @author Budnikau Aliaksandr
 */
@RestController
@RequestMapping("/answers")
public class UncheckedAnswerController {
    @Autowired
    private GrammarCheckService grammarCheckService;

    @ResponseBody
    @RequestMapping(path = "/check", method = RequestMethod.POST)
    public GrammarCheckResult checkAnswer(@RequestBody UncheckedAnswer uncheckedAnswer) {
        return grammarCheckService.check(uncheckedAnswer.getText());
    }
}
