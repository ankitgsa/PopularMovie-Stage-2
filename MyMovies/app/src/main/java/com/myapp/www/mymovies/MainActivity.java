package com.myapp.www.mymovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static boolean mTwoPane;

    public static boolean screenRotate = false;
    MainActivityFragment mfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       if (findViewById(R.id.container2) != null) {

                mTwoPane= true;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container2, new DetailedActivityFragment())
                    .commit();


        }} else {
            mTwoPane = false;
        }

    } @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);


        if(!MainActivityFragment.sortByFav && savedInstanceState!=null&&screenRotate==true)
        {



            ArrayList<String> posters = savedInstanceState.getStringArrayList("movies");

            if(posters!=null&&this!=null) {

                ImageAdapter adapter = new ImageAdapter(this, posters);
                MainActivityFragment.gridview.setAdapter(adapter);

            }

            screenRotate=false;


        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        screenRotate = true;
        outState.putStringArrayList("movies",  MainActivityFragment.posters);



    }
    @Override
    public void onStart()
    {super.onStart();
        if(mTwoPane) {
            FrameLayout container1 = (FrameLayout) findViewById(R.id.container1);
            FrameLayout container2 = (FrameLayout) findViewById(R.id.container2);



            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);


            ViewGroup.LayoutParams lp = container1.getLayoutParams();
            lp.width = size.x / 2;
            container1.setLayoutParams(lp);


            RelativeLayout.LayoutParams lpp = new RelativeLayout.LayoutParams(size.x / 2, RelativeLayout.LayoutParams.MATCH_PARENT);
            lpp.addRule(RelativeLayout.RIGHT_OF, container1.getId());
            container2.setLayoutParams(lpp);
            mfragment = new MainActivityFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container1, mfragment)
                    .commit();

            if(mfragment.df!=null) {

                getSupportFragmentManager().beginTransaction()
                        .remove(MainActivityFragment.df)
                        .commit();
            }


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
    public void trailer1(View v)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com" +
                "/watch?v=" + DetailedActivityFragment.trailer1));

        startActivity(browserIntent);
    }
    public void trailer2(View v)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com" +
                "/watch?v=" + DetailedActivityFragment.trailer2));
        startActivity(browserIntent);

    }

    public void favourite(View v)
    {



        Button b=(Button)findViewById(R.id.favourite);
        if(b.getText().equals("FAVOURITE")){


            b.setText("UN-FAVOURITE");
            b.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
            ContentValues values = new ContentValues();
            values.put(MovieDatabase.NAME,DetailedActivityFragment.poster);
            values.put(MovieDatabase.OVERVIEW,DetailedActivityFragment.overviews);
            values.put(MovieDatabase.RATING,DetailedActivityFragment.ratings);
            values.put(MovieDatabase.DATE,DetailedActivityFragment.date);
             values.put(MovieDatabase.YOUTUBE1,DetailedActivityFragment.trailer1);
            values.put(MovieDatabase.YOUTUBE2,DetailedActivityFragment.trailer2);
            values.put(MovieDatabase.TITLE,DetailedActivityFragment.titles);

            getContentResolver().insert(MovieDatabase.CONTENT_URI, values);
        }
        else if(b.getText().equals("UN-FAVOURITE")){

            b.setText("FAVOURITE");
            b.getBackground().setColorFilter(Color.GRAY,PorterDuff.Mode.MULTIPLY);
            getContentResolver().delete(Uri.parse("content://com.myapp.provider.Movies/movies"),
                    "title=?",new String[]{DetailedActivityFragment.titles});

        }

    mfragment.onStart();
    }

}
