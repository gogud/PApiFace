package com.example.mg_win.papiface.FaceAPI;

/**
 * Created by mg-Win on 9.08.2016.
 */
public interface FaceRecognitionResponse {
    void processFaceRecognition(FaceRecognition.FaceRecognitionResult[] results);
    void nullDataReturned();
}
