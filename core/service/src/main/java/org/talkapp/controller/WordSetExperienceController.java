package org.talkapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talkapp.model.Account;
import org.talkapp.model.WordSetExperience;
import org.talkapp.service.AccountService;
import org.talkapp.service.WordSetExperienceService;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
@RestController
@RequestMapping(WordSetExperienceController.CONTROLLER_PATH)
public class WordSetExperienceController {
    public static final String CONTROLLER_PATH = "/wordsetExperience";

    @Autowired
    private WordSetExperienceService wordSetExperienceService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public List<WordSetExperience> findAll() {
        Account current = accountService.getCurrent();
        return wordSetExperienceService.findAllByAccountId(current.getId());
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public WordSetExperience findById(@PathVariable String id) {
        return wordSetExperienceService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody WordSetExperience wordSetExperience) {
        wordSetExperienceService.save(wordSetExperience);
    }
}