package com.myapp.www.mymovies;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailedActivityFragment())
                    .commit();
        }
    }


    public void favourite(View v){

        Button b=(Button)findViewById(R.id.favourite);
        if(b.getText().equals("FAVOURITE")){


            b.setText("UN-FAVOURITE");
            b.getBackground().setColorFilter(Color.RED,PorterDuff.Mode.MULTIPLY);
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
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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

}