package com.myapp.www.mymovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    static GridView gridview;
    static ArrayList<String> posters;
    static boolean sortByPop = true;
    static boolean sortByFav=true;
    private static final String API_KEY = "";
    static PreferenceChangeListener listener;
    static SharedPreferences prefs;

    static ArrayList<String> overviews;
    static ArrayList<String> trailer1;
    static ArrayList<String> trailer2;
    static ArrayList<ArrayList<String>> reviews;
    static ArrayList<String> title;
    static ArrayList<String> date;
    static ArrayList<String> ratings;
    static ArrayList<String> id;
    static ArrayList<Boolean> favourited;

    public static DetailedActivityFragment df;
    static ArrayList<String> postersF;
    static ArrayList<String> titlesF;
    static ArrayList<String> ratingsF;
    static ArrayList<String> overviewsF;
    static ArrayList<String> datesF;
    static ArrayList<ArrayList<String>> reviewsF;
    static ArrayList<String> youtubesF;
    static ArrayList<String> youtubes2F;

    public MainActivityFragment() {


    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<String> array = new ArrayList<String>();
        if(array!=null&&getActivity()!=null) {
            ImageAdapter adapter = new ImageAdapter(getActivity(), array);
            gridview = (GridView) rootView.findViewById(R.id.gridView);
            gridview.setAdapter(adapter);
        }
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!MainActivity.mTwoPane) {

                    if (!sortByFav) {
                        favourited = bindFavoritesToMovies();

                        Intent intent = new Intent(getActivity(), DetailedActivity.class)

                                .putExtra("overviews", overviews.get(position))
                                .putExtra("title", title.get(position))
                                .putExtra("date", date.get(position))
                                .putExtra("ratings", ratings.get(position))
                                .putExtra("poster", posters.get(position))
                                .putExtra("trailer1", trailer1.get(position))
                                .putExtra("trailer2", trailer2.get(position))
                                .putExtra("reviews", reviews.get(position))
                                .putExtra("favourites", favourited.get(position));

                        startActivity(intent);

                    } else {
                        Intent intent = new Intent(getActivity(), DetailedActivity.class)
                                .putExtra("overviews", overviewsF.get(position))
                                .putExtra("title", titlesF.get(position))
                                .putExtra("date", datesF.get(position))
                                .putExtra("ratings", ratingsF.get(position))
                                .putExtra("poster", postersF.get(position))
                                .putExtra("trailer1", youtubesF.get(position))
                                .putExtra("trailer2", youtubes2F.get(position))
                                .putExtra("favourites", favourited.get(position));

                        startActivity(intent);
                    }

                }
                else{
                        if (!sortByFav) {

                            df = new DetailedActivityFragment();
                            Bundle b = new Bundle();

                            favourited = bindFavoritesToMovies();


                            b.putStringArrayList("reviews", reviews.get(position));
                            b.putString("title", title.get(position));
                            b.putString("overviews", overviews.get(position));
                            b.putString("date", date.get(position));
                            b.putString("ratings", ratings.get(position));
                             b.putBoolean("favourites", favourited.get(position));
                            b.putString("poster", posters.get(position));
                            b.putString("trailer1", trailer1.get(position));
                            b.putString("trailer2", trailer2.get(position));

                            df.setArguments(b);

                            Log.e(MainActivityFragment.class.getSimpleName(), "IN Loop:\t" + b);

                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container2, df)
                                    .commit();


                        } else {

                            df = new DetailedActivityFragment();
                            Bundle b = new Bundle();

                            b.putString("overviews", overviewsF.get(position));
                            b.putString("title", titlesF.get(position));
                            b.putString("date", datesF.get(position));
                            b.putString("ratings", ratingsF.get(position));
                            b.putString("poster", postersF.get(position));
                            b.putString("trailer1", youtubesF.get(position));
                            b.putString("trailer2", youtubes2F.get(position));
                            b.putBoolean("favourites", favourited.get(position));


                            df.setArguments(b);
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container2, df)
                                    .commit();


                        }
                        }}

                    });
        return rootView;
}
    public ArrayList<Boolean> bindFavoritesToMovies()
    {
        ArrayList<Boolean> result = new ArrayList<>();
        for(int i =0; i<title.size();i++)
        {
            result.add(false);
        }
        for(String favoritedTitles: titlesF)
        {
            for(int x = 0; x<title.size(); x++)
            {
                if(favoritedTitles.equals(title.get(x)))
                {
                    result.set(x,true);
                }
            }
        }
        return result;
    }
    private class PreferenceChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            try {
                gridview.setAdapter(null);
                onStart();

            }catch(Exception e){}
        }
    }

    @Override
    public void onStart() {

        super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listener = new PreferenceChangeListener();
        prefs.registerOnSharedPreferenceChangeListener(listener);

        if (prefs.getString("sortby", "popularity").equals("popularity")) {
            getActivity().setTitle("Most Popular Movies");
            sortByPop = true;
            sortByFav=false;
        } else if(prefs.getString("sortby", "popularity").equals("rating")) {
            getActivity().setTitle("Highest Rated Movies");
            sortByPop = false;
            sortByFav=false;
        }
        else if(prefs.getString("sortby","popularity").equals("favourites"))
        {
            getActivity().setTitle("Favourited Movies");
            sortByPop = false;
            sortByFav=true;
        }
        loadFavouritesData();
        if(sortByFav)
        {
            if(postersF.size()==0)
        {
            gridview.setVisibility(GridView.GONE);
        }
        else {
            gridview.setVisibility(GridView.VISIBLE);
        }
            if(postersF!=null&&getActivity()!=null)
            {
                ImageAdapter adapter = new ImageAdapter(getActivity(),postersF);
                gridview.setAdapter(adapter);
            }
        }
        else {
            gridview.setVisibility(GridView.VISIBLE);

                new ImageLoadTask().execute();

        }
    }
    public void loadFavouritesData()
    {
        String URL = "content://com.myapp.provider.Movies/movies";
        Uri movies = Uri.parse(URL);
        Cursor c = getActivity().getContentResolver().query(movies,null,null,null,"title");

        postersF = new ArrayList<String>();
        titlesF = new ArrayList<String>();
        datesF = new ArrayList<String>();
        overviewsF = new ArrayList<String>();
        favourited = new ArrayList<Boolean>();
        reviewsF = new ArrayList<ArrayList<String>>();
        youtubesF = new ArrayList<String>();
        youtubes2F = new ArrayList<String>();
        ratingsF = new ArrayList<String>();
        if(c==null) return;
        while(c.moveToNext())
        {

            postersF.add(c.getString(c.getColumnIndex(MovieDatabase.NAME)));
            titlesF.add(c.getString(c.getColumnIndex(MovieDatabase.TITLE)));
            overviewsF.add(c.getString(c.getColumnIndex(MovieDatabase.OVERVIEW)));
            youtubesF.add(c.getString(c.getColumnIndex(MovieDatabase.YOUTUBE1)));
            youtubes2F.add(c.getString(c.getColumnIndex(MovieDatabase.YOUTUBE2)));
            datesF.add(c.getString(c.getColumnIndex(MovieDatabase.DATE)));
            ratingsF.add(c.getString(c.getColumnIndex(MovieDatabase.RATING)));
            favourited.add(true);
       }
    }
    public class ImageLoadTask extends AsyncTask<Void, Void, ArrayList<String>> {



        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            while (true) {
                try {
                    posters = new ArrayList(Arrays.asList(getPathFromAPI(sortByPop)));

                    return posters;
                } catch (Exception e) {
                    continue;
                }
            }

        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (result != null && getActivity() != null) {
                ImageAdapter adapter = new ImageAdapter(getActivity(), result);

                gridview.setAdapter(adapter);
            }
        }

        public String[] getPathFromAPI(boolean sortbypop) {
            while (true) {

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                try {
                    String urlString = null;
                    String JSONResult;
                    if (sortbypop) {
                        urlString =
                                "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + API_KEY;
                    } else {
                        urlString =
                                "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&vote_count.gte=500&api_key=" + API_KEY;
                    }
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }
                    if (buffer.length() == 0) {
                        return null;
                    }
                    JSONResult = buffer.toString();
                    try {
                        overviews = new ArrayList<String>(Arrays.asList(getJSONStrings(JSONResult, "overview")));
                        title = new ArrayList<String>(Arrays.asList(getJSONStrings(JSONResult, "original_title")));
                        date = new ArrayList<String>(Arrays.asList(getJSONStrings(JSONResult, "release_date")));
                        ratings = new ArrayList<String>(Arrays.asList(getJSONStrings(JSONResult, "vote_average")));
                        id = new ArrayList<String>(Arrays.asList(getJSONStrings(JSONResult, "id")));
                        while(true)
                        {
                            trailer1 = new ArrayList<String>(Arrays.asList(getYoutubeids(id,0)));
                            trailer2 = new ArrayList<String>(Arrays.asList(getYoutubeids(id,1)));
                            int nullCount = 0;
                            for(int i = 0; i<trailer1.size();i++)
                            {
                                if(trailer1.get(i)==null)
                                {
                                    nullCount++;
                                    trailer1.set(i,"no video found");
                                }
                            }
                            for(int i = 0; i<trailer2.size();i++)
                            {
                                if(trailer2.get(i)==null)
                                {
                                    nullCount++;
                                    trailer2.set(i,"no video found");
                                }
                            }
                            if(nullCount>2)continue;
                            break;
                        }
                        reviews = getReviewsFromIds(id);
                        return getPathFromJSON(JSONResult);
                    } catch (JSONException e) {
                        return null;
                    }

                } catch (Exception e) {
                    continue;

                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {

                            continue;
                        }


                    }

                }
            }
        }

        public String[] getJSONStrings(String JSONStringParams, String param) throws JSONException {
            JSONObject JSONString = new JSONObject(JSONStringParams);

            JSONArray movieArray = JSONString.getJSONArray("results");
            String[] result = new String[movieArray.length()];

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i);

                if (param.equals("vote_average")) {
                    Double number = movie.getDouble("vote_average");
                    String ratings = Double.toString(number) + "/10";
                    result[i] = ratings;
                } else {
                    String data = movie.getString(param);
                    result[i] = data;
                }
            }
            return result;
        }

        public String[] getPathFromJSON(String JSONStringParams) throws JSONException {

            JSONObject JSONString = new JSONObject(JSONStringParams);

            JSONArray movieArray = JSONString.getJSONArray("results");
            String[] result = new String[movieArray.length()];

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i);
                String moviePath = movie.getString("poster_path");
                result[i] = moviePath;
            }
            return result;
        }


        public String[] getYoutubeids(ArrayList<String> ids, int position) {

            String[] results = new String[ids.size()];
            for (int i = 0; i < ids.size(); i++) {


                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                try {
                    String urlString = null;
                    String JSONResult;
                    urlString =
                            "http://api.themoviedb.org/3/movie/" + ids.get(i) + "/videos?api_key=" + API_KEY;

                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }
                    if (buffer.length() == 0) {
                        return null;
                    }
                    JSONResult = buffer.toString();
                    try {

                        results[i] = getYoutubeLink(JSONResult, position);
                    } catch (JSONException E) {
                        results[i] = "no videos found";
                    }

                } catch (Exception e) {
                    continue;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {

                            continue;
                        }


                    }
                }
            }

            return results;
        }


    }

    private String getYoutubeLink(String JSONStringParams, int position) throws JSONException {

        JSONObject JSONString = new JSONObject(JSONStringParams);
        JSONArray youtubeArray = JSONString.getJSONArray("results");
        JSONObject youtube;
        String result = "NO videos Found";
        if (position == 0) {
            youtube = youtubeArray.getJSONObject(0);
            result = youtube.getString("key");

        } else if (position == 1) {
            if (youtubeArray.length() > 1) {
                youtube = youtubeArray.getJSONObject(1);
            } else {
                youtube = youtubeArray.getJSONObject(0);
            }

            result = youtube.getString("key");
        }
        return result;
    }

    public ArrayList<ArrayList<String>> getReviewsFromIds(ArrayList<String> ids) {
            outerloop:
               while(true){
                    ArrayList<ArrayList<String>> results = new ArrayList<>();
                    for(int i=0;i<ids.size();i++){
                    HttpURLConnection urlConnection = null;
                    BufferedReader reader=null;
                    String JSONResult;
            try{
                String urlString =null;
                urlString=
                        "http://api.themoviedb.org/3/movie/" + ids.get(i) + "/reviews?api_key=" + API_KEY;
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                JSONResult = buffer.toString();
                try    {
                        results.add(getCommentsFromJSON(JSONResult));

                } catch (JSONException E) {
                        return null;
                }

            } catch (Exception e) {
                continue outerloop;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {

                        continue;
                    }


                }
            }
            }

            return results;
        }
    }

    public ArrayList<String> getCommentsFromJSON(String jsonResult) throws JSONException{

            JSONObject JSONString=new JSONObject(jsonResult);
            JSONArray reviewsArray= JSONString.getJSONArray("results");
             ArrayList<String> results=new ArrayList<>();

            if(reviewsArray.length()==0){
                results.add("No Reviews yet");
                return results;
            }
        for(int i=0;i<reviewsArray.length();i++){

            JSONObject result = reviewsArray.getJSONObject(i);
            results.add(result.getString("content"));
        }


        return results;    }

}
