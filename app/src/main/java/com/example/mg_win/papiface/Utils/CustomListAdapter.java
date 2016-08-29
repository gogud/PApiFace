package com.example.mg_win.papiface.Utils;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mg_win.papiface.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mg-Win on 10.08.2016.
 */
public class CustomListAdapter extends ArrayAdapter<String> {
    private ArrayList<String> resultContent;
    //private Integer[] imageid;
    private Activity context;

    private static String TAG = "CustomListAdapter";

    public CustomListAdapter(Activity context, ArrayList<String> resultContent) {
        super(context, R.layout.activity_ident_result2, resultContent);
        this.context = context;
        this.resultContent = resultContent;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        //View listViewItem = inflater.inflate(R.layout.activity_ident_result2, null, true);
        View listViewItem = inflater.inflate(R.layout.activity_ident_result2, parent, false);


        Log.d(TAG, "Position = " + position + "\tContainer = " + resultContent.get(position) );

        if (position % 2 == 0) {

            TextView textViewName = (TextView) listViewItem.findViewById(R.id.textView_score);
            textViewName.setText(resultContent.get(position));

            ImageView imageView = (ImageView) listViewItem.findViewById(R.id.imageView_score);
            //imageView.setImageBitmap(IdentResultActivity.splittedBitmaps.get(position + 1));

            Picasso.with(this.context).load(resultContent.get(position + 1)).placeholder(R.drawable.progress_animation).skipMemoryCache().into(imageView);

        }
        return  listViewItem;

    }
}
