package com.example.mg_win.papiface.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.mg_win.papiface.R;

public class SelectedImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_image);

        ImageView imageView = (ImageView) findViewById(R.id.selectedImageView);

        Bundle extras = getIntent().getExtras();
        byte[] imageArray = extras.getByteArray("ImageArray");

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
        imageView.setImageBitmap(bitmap);

    }
}
