package com.example.mg_win.papiface.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.mg_win.papiface.R;
import com.example.mg_win.papiface.Utils.ImagePicker;
import com.example.mg_win.papiface.Utils.SetGetImage;

import java.io.File;

public class StartActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_ID = 234; // the number doesn't matter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Search Button click event
        Button searchButton = (Button) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                Intent chooseImageIntent = ImagePicker.getPickImageIntent(getApplicationContext());
                                                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
                                            }
                                        }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE_ID:

                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                /*
                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(bitmap);
                */

                SetGetImage setGetImage = new SetGetImage();
                boolean isStored = setGetImage.storeImage(this, bitmap);

                if (isStored) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
/*
                File file = setGetImage.getOutputMediaFile(this);

                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());


                ImageView imageView = (ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(myBitmap);
*/
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }



}
