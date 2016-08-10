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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mg_win.papiface.FaceAPI.FaceRecognition;
import com.example.mg_win.papiface.FaceAPI.FaceRecognitionResponse;
import com.example.mg_win.papiface.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SelectedImageActivity extends AppCompatActivity  implements FaceRecognitionResponse{

    public final static String TAG = "SelectedImageActivity";

    byte[] imageArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_image);

        ImageView imageView = (ImageView) findViewById(R.id.selectedImageView);

        Bundle extras = getIntent().getExtras();
        imageArray = extras.getByteArray("ImageArray");

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
        imageView.setImageBitmap(bitmap);

    }

    // Identify Button Click!
    public void buttonIdentifyClicked(View view) {
        Log.d(TAG, "Identify Button Clicked");
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Toast.makeText(this, "Sorgu Başlatıldı, Lütfen Bekleyiniz...", Toast.LENGTH_SHORT).show();

        FaceRecognition faceRecognition = new FaceRecognition();
        faceRecognition.delegate = this;

        faceRecognition.execute("identify", imageArray);
    }

    // Enroll Button Click!
    public void buttonEnrollClicked(View view) {
        Log.d(TAG, "Enroll Button Clicked");
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Toast.makeText(this, "Resim Kaydediliyor, Lütfen Bekleyiniz...", Toast.LENGTH_SHORT).show();

        FaceRecognition faceRecognition = new FaceRecognition();
        faceRecognition.delegate = this;

        faceRecognition.execute("enroll", imageArray);
    }

    @Override
    public void processFaceRecognition(FaceRecognition.FaceRecognitionResult[] results) {

        if (results[0].methodName.equalsIgnoreCase("identify")) {
            ArrayList<String> arrayList = new ArrayList<String>();

            for (int i = 0; i < results.length; i++) {
                arrayList.add(results[i].confidence);
                arrayList.add(results[i].thumbnail);
            }

            Intent intent = new Intent(this, ShowIdentActivity.class);
            intent.putExtra("IdentResults", arrayList);
            intent.putExtra("BaseImage", imageArray);
            startActivity(intent);
            finish();
        } else if (results[0].methodName.equalsIgnoreCase("enroll")) {
            onBackPressed();
        }
    }

    @Override
    public void nullDataReturned() {
        Log.d(TAG, "Identify: Null Data Returned");
        Toast.makeText(this, "Yüz Bulunamadı, Lütfen Tekrar Deneyiniz...", Toast.LENGTH_SHORT);

        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Resim Kaydedildi...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
