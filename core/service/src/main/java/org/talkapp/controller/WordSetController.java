package org.talkapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talkapp.model.WordSet;
import org.talkapp.service.WordSetService;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
@RestController
@RequestMapping(WordSetController.CONTROLLER_PATH)
public class WordSetController {
    public static final String CONTROLLER_PATH = "/wordset";

    @Autowired
    private WordSetService wordSetService;

    @RequestMapping(method = RequestMethod.GET)
    public List<WordSet> findAll() {
        return wordSetService.findAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public WordSet findById(@PathVariable String id) {
        return wordSetService.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody WordSet wordSet) {
        wordSetService.save(wordSet);
    }
}