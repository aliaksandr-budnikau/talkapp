package org.talkapp.service;

import org.talkapp.model.UnrecognizedVoice;
import org.talkapp.model.VoiceRecognitionResult;

/**
 * @author Budnikau Aliaksandr
 */
public interface VoiceService {
    VoiceRecognitionResult syncRecognizeFile(UnrecognizedVoice voice);
}