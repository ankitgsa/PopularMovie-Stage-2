package com.myapp.www.mymovies;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Ankit on 05-04-2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> array;


    public ImageAdapter(Context c, ArrayList<String> paths) {
        mContext = c;
        paths.removeAll(Collections.singleton(null));
        array = paths;

        Log.e(MainActivityFragment.class.getSimpleName(),"PosterF array:\t"+ array);

    }

    @Override
    public int getCount() {

        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null)
        {
            imageView = new ImageView(mContext);
        }
        else{
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext)

                .load("http://image.tmdb.org/t/p/w185/" + array.get(position))
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .fit()
                .into(imageView);

        return imageView;

    }}



