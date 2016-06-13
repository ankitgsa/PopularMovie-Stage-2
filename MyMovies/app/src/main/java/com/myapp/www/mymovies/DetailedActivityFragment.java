package com.myapp.www.mymovies;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ankit on 03-06-2016.
 */ public class DetailedActivityFragment extends Fragment {

    public static String overviews;
    public static String trailer1;
    public static String trailer2;
    public static String comment;
    public static boolean favourite;
    public static String titles;
    public static String date;
    public static String ratings;
    public static String poster;
    public static ArrayList<String> reviews;
   public DetailedActivityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detailed, container, false);
        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra("overviews")) {
             overviews = intent.getStringExtra("overviews");
           TextView tv=((TextView) rootView.findViewById(R.id.overview));
                    tv.setText(overviews);
        }
        if (intent != null && intent.hasExtra("title")) {

             titles = intent.getStringExtra("title");
            TextView tv = ((TextView) rootView.findViewById(R.id.title));
                    tv.setText(titles);

        }
        if (intent != null && intent.hasExtra("date")) {
             date = intent.getStringExtra("date");
           TextView tv= ((TextView) rootView.findViewById(R.id.date));
                    tv.setText(date);

        }
        if (intent != null && intent.hasExtra("ratings")) {
             ratings = intent.getStringExtra("ratings");
            TextView tv=((TextView) rootView.findViewById(R.id.rating));
                    tv.setText(ratings);

        }

        if (intent != null && intent.hasExtra("poster")) {

            ImageView imageView = (ImageView) rootView.findViewById(R.id.poster);
             poster = intent.getStringExtra("poster");
            Picasso.with(getActivity())
                    .load("http://image.tmdb.org/t/p/w92/" + poster)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .fit()
                    .into(imageView);

        }
        if (intent != null && intent.hasExtra("trailer1")) {

            trailer1 = intent.getStringExtra("trailer1");
        }
        if (intent != null && intent.hasExtra("trailer2")) {
            trailer2 = intent.getStringExtra("trailer2");
        }


       if (intent != null && intent.hasExtra("reviews")) {

              reviews = intent.getStringArrayListExtra("reviews");
            for (int i = 0; i < reviews.size(); i++) {
                LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linear);
                View divider = new View(getActivity());
                TextView tv = new TextView(getActivity());
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(p);
                int paddingPixel = 10;
                float density = getActivity().getResources().getDisplayMetrics().density;
                int paddingDP = (int) (paddingPixel * density);
                tv.setPadding(0, paddingDP, 0, paddingDP);
                RelativeLayout.LayoutParams x = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                x.height = 1;
                divider.setLayoutParams(x);
                divider.setBackgroundColor(Color.BLACK);

                tv.setText(reviews.get(i));
                layout.addView(divider);
                layout.addView(tv);

                if (comment == null) {
                    comment = reviews.get(i);
                } else {
                    comment += "divider123" + reviews.get(i);
                }
            }

        }

        Button b = (Button) rootView.findViewById(R.id.favourite);
        if (intent != null && intent.hasExtra("favourites")) {
            favourite = intent.getBooleanExtra("favourites", false);
            if (!favourite) {
                b.setText("FAVOURITE");
                b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            } else {
                b.setText("UN-FAVOURITE");
                b.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            }
        }
        if (getArguments()!=null)
        {

            Bundle bundle = getArguments();
            titles=bundle.getString("title");
            trailer1 = bundle.getString("trailer1");
            trailer2 = bundle.getString("trailer2");
            overviews = bundle.getString("overviews");
            date = bundle.getString("date");
            reviews = bundle.getStringArrayList("reviews");
            poster = bundle.getString("poster");

            favourite = bundle.getBoolean("favourites");

            ratings = bundle.getString("ratings");

            TextView textviews = (TextView) rootView.findViewById(R.id.overview);
            textviews.setText(overviews);
            ImageView imageView = (ImageView) rootView.findViewById(R.id.poster);
            Picasso.with(getActivity())
                    .load("http://image.tmdb.org/t/p/w92/" + poster)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.error)
                    .fit()
                    .into(imageView);

            TextView textviewDate = (TextView) rootView.findViewById(R.id.date);
            textviewDate.setText(date);
            TextView textviewTitles = (TextView) rootView.findViewById(R.id.title);
            textviewTitles.setText(titles);
            TextView textviewRatings = (TextView) rootView.findViewById(R.id.rating);

            textviewRatings.setText(ratings);
        if(reviews!=null){
            for (int i = 0; i < reviews.size(); i++) {

                LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linear);
                View divider = new View(getActivity());
                TextView textview = new TextView(getActivity());
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                textview.setLayoutParams(p);
                int paddingPixel = 10;
                float density = getActivity().getResources().getDisplayMetrics().density;
                int paddingDp = (int) (paddingPixel * density);
                textview.setPadding(0, paddingDp, 0, paddingDp);
                RelativeLayout.LayoutParams x = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                x.height = 1;
                divider.setLayoutParams(x);
                divider.setBackgroundColor(Color.BLACK);
                textview.setText(reviews.get(i));
                layout.addView(divider);
                layout.addView(textview);
                if (comment == null) {
                    comment = reviews.get(i);
                } else {
                    comment += "divider123" + reviews.get(i);
                }
            }}
            if (!favourite) {


                b.setText("FAVOURITE");
                b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            } else {
                b.setText("UN-FAVOURITE");
                b.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

            }

        }



        return rootView;

    }
}
