package org.talkapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.talkapp.model.Sentence;
import org.talkapp.service.SentenceService;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
@RestController
@RequestMapping(SentenceController.CONTROLLER_PATH)
public class SentenceController {
    public static final String CONTROLLER_PATH = "/sentences";
    public static final String REQUEST_PARAM_WORDS = "words";

    @Autowired
    private SentenceService sentenceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Sentence> findAll() {
        return sentenceService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, params = REQUEST_PARAM_WORDS)
    public List<Sentence> findByWords(@RequestParam(REQUEST_PARAM_WORDS) String words) {
        return sentenceService.findByWords(words);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody Sentence sentence) {
        sentenceService.save(sentence);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteAll() {
        sentenceService.deleteAll();
    }
}