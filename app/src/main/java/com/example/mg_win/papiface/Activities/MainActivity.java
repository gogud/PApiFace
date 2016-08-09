package com.example.mg_win.papiface.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.mg_win.papiface.FaceAPI.FaceRecognition;
import com.example.mg_win.papiface.FaceAPI.FaceRecognitionResponse;
import com.example.mg_win.papiface.R;
import com.example.mg_win.papiface.Utils.SetGetImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements FaceRecognitionResponse {

    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Send Image to FaceRecognition
        File file = getImageFromCache();
        byte[] imageArray = convertFileToByteArray(file);
        if (imageArray != null) {

            FaceRecognition faceRecognition = new FaceRecognition();
            faceRecognition.delegate = this;
            faceRecognition.execute("identify", imageArray);

        }

        /*
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(myBitmap);
        */
    }

    public File getImageFromCache() {
        // Get Image - Last Saved
        SetGetImage setGetImage = new SetGetImage();
        File file = setGetImage.getOutputMediaFile(this);

        return file;
    }

    public byte[] convertFileToByteArray(File file) {
        FileInputStream fileInputStream = null;

        if (file.length() > 0) {
            byte[] bFile = new byte[(int) file.length()];

            try {
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(bFile);
                fileInputStream.close();



            } catch (FileNotFoundException e) {
                Log.d(TAG, "Cannot create fileInputStream: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Cannot read bFile: " + e.getMessage());
            }

            return bFile;
        } else {
            Log.d(TAG, "- ConvertFileToByteArray: File is Empty!");
            return null;
        }
    }

    //API Finish Works! Process here...
    @Override
    public void processFaceRecognition(FaceRecognition.FaceRecognitionResult[] results) {

        switch (results[0].methodName) {
            case "detect":
                Log.d(TAG, ": Case - detect");
                break;
            case "enroll":
                Log.d(TAG, ": Case - enroll");
                break;
            case "identify":
                Log.d(TAG, ": Case - identify");
                break;
            default:
                break;
        }
    }
}
