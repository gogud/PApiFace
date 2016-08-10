package com.example.mg_win.papiface.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.mg_win.papiface.R;
import com.example.mg_win.papiface.Utils.CustomListAdapter;

import java.io.File;
import java.util.ArrayList;

public class ShowIdentActivity extends AppCompatActivity {

    public static ArrayList<String> resultArrayList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ident);

        // Bar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Bundle extras = getIntent().getExtras();
        byte[] baseImage = extras.getByteArray("BaseImage");
        resultArrayList = extras.getStringArrayList("IdentResults");

        Bitmap bmp = BitmapFactory.decodeByteArray(baseImage, 0, baseImage.length);
        ImageView imageBase = (ImageView) findViewById(R.id.imageViewSource);

        imageBase.setImageBitmap(bmp);


        CustomListAdapter customListAdapter = new CustomListAdapter(this, resultArrayList);
        listView = (ListView) findViewById(R.id.listview_score);
        listView.setAdapter(customListAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
