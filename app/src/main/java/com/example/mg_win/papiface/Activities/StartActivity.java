package com.example.mg_win.papiface.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mg_win.papiface.FaceAPI.FaceRecognition;
import com.example.mg_win.papiface.FaceAPI.FaceRecognitionResponse;
import com.example.mg_win.papiface.R;
import com.example.mg_win.papiface.Utils.GridImageAdapter;
import com.example.mg_win.papiface.Utils.ImagePicker;
import com.example.mg_win.papiface.Utils.SetGetImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity implements FaceRecognitionResponse {

    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter
    private static String TAG = "StartActivity";

    public static ArrayList<Bitmap> bitmapArrayList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // Search Button click event
        Button searchButton = (Button) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                openGetPhotoOptions();
                                            }
                                        }
        );
    }

    public void openGetPhotoOptions() {
        Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE_ID:

                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);

                SetGetImage setGetImage = new SetGetImage();
                boolean isStored = setGetImage.storeImage(this, bitmap);

                if (isStored) {
                    /*
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    */

                    File file = getImageFromCache();
                    byte[] imageArray = convertFileToByteArray(file);
                    if (imageArray != null) {

                        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        Toast.makeText(this,"Resim İşleniyor, Lütfen Bekleyiniz...",Toast.LENGTH_LONG).show();

                        FaceRecognition faceRecognition = new FaceRecognition();
                        faceRecognition.delegate = this;
                        faceRecognition.execute("detect", imageArray);

                    }

                }

                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
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
                /**
                 * FACE DETECT FUNCTION
                 */

                detectAPI(results);

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

    @Override
    public void nullDataReturned() {
        Toast.makeText(this,"Yüz Bulunamadı, Lütfen Tekrar Deneyiniz...",Toast.LENGTH_SHORT).show();

        openGetPhotoOptions();
    }

    public void detectAPI(FaceRecognition.FaceRecognitionResult[] results) {

        bitmapArrayList = new ArrayList<Bitmap>();

        for (int i = 0; i < results.length; i++) {
            bitmapArrayList.add(convertToBitmap(results[i].x1, results[i].x2,
                    results[i].y1, results[i].y2));
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("images", bitmapArrayList);
        startActivity(intent);
    }

    public Bitmap convertToBitmap(int x1, int x2, int y1, int y2) {

        int height = y2 - y1;
        int width = x2 - x1;

        Bitmap bmp = BitmapFactory.decodeByteArray(convertFileToByteArray(getImageFromCache()), 0, convertFileToByteArray(getImageFromCache()).length);
        Bitmap tmpImage = Bitmap.createBitmap(bmp, x1, y1, width, height);

        return tmpImage;
    }



}
