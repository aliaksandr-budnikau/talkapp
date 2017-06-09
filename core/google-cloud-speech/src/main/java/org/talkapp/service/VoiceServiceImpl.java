package org.talkapp.service;

import com.google.cloud.speech.spi.v1beta1.SpeechClient;
import com.google.cloud.speech.v1beta1.*;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talkapp.model.UnrecognizedVoice;
import org.talkapp.model.VoiceRecognitionResult;

import java.util.List;

/**
 * @author Budnikau Aliaksandr
 */
@Service
public class VoiceServiceImpl implements VoiceService {
    @Autowired
    private RecognitionConfig recognitionConfig;
    @Autowired
    private Logger logger;
    @Autowired
    private SpeechClient speechClient;

    @Override
    public VoiceRecognitionResult syncRecognizeFile(UnrecognizedVoice unrecognizedVoice) {
        logger.debug("Unrecognized voice: " + unrecognizedVoice);
        try {
            ByteString audioBytes = ByteString.copyFrom(unrecognizedVoice.getVoice());

            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(audioBytes)
                    .build();

            // Use blocking call to get audio transcript
            SyncRecognizeResponse response = speechClient.syncRecognize(recognitionConfig, audio);
            List<SpeechRecognitionResult> results = response.getResultsList();
            VoiceRecognitionResult voiceRecognitionResult = new VoiceRecognitionResult();
            for (SpeechRecognitionResult result : results) {
                List<SpeechRecognitionAlternative> alternatives = result.getAlternativesList();
                for (SpeechRecognitionAlternative alternative : alternatives) {
                    voiceRecognitionResult.getVariant().add(alternative.getTranscript());
                }
            }
            logger.debug("Recognition result: " + voiceRecognitionResult);
            return voiceRecognitionResult;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}