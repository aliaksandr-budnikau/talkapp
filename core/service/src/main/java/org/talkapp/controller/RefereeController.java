package org.talkapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talkapp.model.UncheckedAnswer;
import org.talkapp.model.AnswerCheckingResult;
import org.talkapp.service.RefereeService;

/**
 * @author Budnikau Aliaksandr
 */
@RestController
@RequestMapping(RefereeController.CONTROLLER_PATH)
public class RefereeController {
    public static final String CONTROLLER_PATH = "/referee";
    public static final String CHECK_METHOD = "/checkAnswer";
    @Autowired
    private RefereeService refereeService;

    @RequestMapping(path = CHECK_METHOD, method = RequestMethod.POST)
    public AnswerCheckingResult checkAnswer(@RequestBody UncheckedAnswer uncheckedAnswer) {
        return refereeService.checkAnswer(uncheckedAnswer);
    }
}
