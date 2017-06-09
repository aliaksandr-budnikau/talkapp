package org.talkapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.talkapp.model.UnrecognizedVoice;
import org.talkapp.model.VoiceRecognitionResult;
import org.talkapp.service.VoiceService;

@RestController
@RequestMapping(VoiceServiceController.CONTROLLER_PATH)
public class VoiceServiceController {
    public static final String CONTROLLER_PATH = "/voices";
    public static final String RECOGNIZE_METHOD = "/recognize";

    @Autowired
    private VoiceService voiceService;

    @RequestMapping(path = RECOGNIZE_METHOD, method = RequestMethod.POST)
    public VoiceRecognitionResult checkAnswer(@RequestBody UnrecognizedVoice unrecognizedVoice) {
        return voiceService.syncRecognizeFile(unrecognizedVoice);
    }
}