package org.talkapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talkapp.dto.UncheckedAnswer;
import org.talkapp.model.GrammarCheckResult;
import org.talkapp.service.UncheckedAnswerService;

/**
 * @author Budnikau Aliaksandr
 */
@RestController
@RequestMapping(UncheckedAnswerController.CONTROLLER_PATH)
public class UncheckedAnswerController {
    public static final String CONTROLLER_PATH = "/answers";
    public static final String CHECK_METHOD = "/check";
    @Autowired
    private UncheckedAnswerService uncheckedAnswerService;

    @RequestMapping(path = CHECK_METHOD, method = RequestMethod.POST)
    public GrammarCheckResult checkAnswer(@RequestBody UncheckedAnswer uncheckedAnswer) {
        return uncheckedAnswerService.check(uncheckedAnswer.getText());
    }
}
