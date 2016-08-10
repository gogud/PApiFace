package com.example.mg_win.papiface.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mg_win.papiface.FaceAPI.FaceRecognition;
import com.example.mg_win.papiface.FaceAPI.FaceRecognitionResponse;
import com.example.mg_win.papiface.R;
import com.example.mg_win.papiface.Utils.GridImageAdapter;
import com.example.mg_win.papiface.Utils.SetGetImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    private static String TAG = "MainActivity";

    public static List<Bitmap> splittepBitmaps;
    GridImageAdapter gridImageAdapter = null;
    GridView gridView = null;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mContext = this.getApplicationContext();

        splittepBitmaps = getIntent().getParcelableArrayListExtra("images");
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new GridImageAdapter(this));

        gridView.setClickable(true);
        gridImageAdapter = new GridImageAdapter(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Clicked Image Position: " + position);

                Bitmap tmpImg = gridImageAdapter.getItem(position);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                tmpImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent intent = new Intent(mContext, SelectedImageActivity.class);
                intent.putExtra("ImageArray", byteArray);
                startActivity(intent);

            }
        });

        Toast.makeText(this,"Lütfen Resim Seçiniz",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
