package com.example.mg_win.papiface.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.mg_win.papiface.Activities.MainActivity;

/**
 * Created by mg-Win on 10.08.2016.
 */
public class GridImageAdapter extends BaseAdapter {

    private Context mContext;
    public GridImageAdapter(Context context) { mContext = context; }


    @Override
    public int getCount() {
        return MainActivity.splittepBitmaps.size();
    }

    @Override
    public Bitmap getItem(int position) {
        return MainActivity.splittepBitmaps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int width = (int) (displayMetrics.widthPixels / 3.2);
        int height = (int) (displayMetrics.heightPixels / 3.2);

        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(width, width));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(1, 1, 1, 1);

        imageView.setImageBitmap(MainActivity.splittepBitmaps.get(position));

        return imageView;
    }
}
