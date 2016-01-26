package com.example.android.movieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.HashMap;

/**
 * Created by pc on 09/12/2015.
 */
public  class MoviesFragment extends Fragment {

    static GridView gridView;
    static ArrayList<String> posters;
    static  int width;
    static  boolean sortByPop;
    static String API_KEY="20934a243cb0d3630d3a2d644cc6f384";
    static preferenceChangeListener listener;
    static SharedPreferences prefs;
    static boolean sortByFavorites;
    static ArrayList<String> postersF;
    static ArrayList<String> titlesF;
    static ArrayList<String> datesF;
    static ArrayList<String> ratingsF;
    static ArrayList<String> youtubesF;
    static ArrayList<String> youtubes2F;
    static ArrayList<ArrayList<String>> commentsF;
    static ArrayList<String> overviewsF;
    static ArrayList<String> overviews;
    static ArrayList<String> titles;
    static ArrayList<String> dates;
    static ArrayList<String> ratings;
    static ArrayList<String> youtubes;
    static ArrayList<String> youtubes2;
    static ArrayList<String> ids;
    static ArrayList<Boolean> favorited;
    static ArrayList<ArrayList<String>> comments;
    public MoviesFragment() {
    }

   /* public interface Callback{
        public void onItemSelected(JSONObject posterUri);
    }*/
     public interface Callback{
        public void onItemSelected(HashMap<String,String> posterUri);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
                if (arguments != null) {
                         arguments.getParcelable(DetailActivityFragment.DETAIL_URI);
                    }
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        WindowManager wm =(WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if(MainActivity.TABLET)
        {
            width = size.x/6;
        }
        else width=size.x/3;

        if(getActivity()!=null)
        {
            ArrayList<String> array = new ArrayList<String>();
            ImageAdapter adapter = new ImageAdapter(getActivity(),array,width);
            gridView = (GridView)rootView.findViewById(R.id.grid_view);

            gridView.setColumnWidth(width);
            gridView.setAdapter(adapter);
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
// onitemselected no3 2eh we 27oto gwaha
                //7age 3nd set
                //23mel josnobject key , value


             /* Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if(cursor !=null){
                ((Callback)getActivity()).onItemSelected(posters.get(position));
                }*/
                HashMap<String,String> mymovie = new HashMap<String,String>();
                if(!sortByFavorites){




                favorited = bindFavouriteToMovies();

               // Intent intent = new Intent(getActivity(),DetailActivity.class).
                 mymovie.put("overview",overviews.get(position).replace(","," ").replace(":"," "));
                    mymovie.put("poster",posters.get(position));
                    mymovie.put("title",titles.get(position).replace(","," ").replace(":"," "));
                    mymovie.put("dates",dates.get(position));
                mymovie.put("rating", ratings.get(position));
                mymovie.put("youtube", youtubes.get(position));
                mymovie.put("youtube2", youtubes2.get(position));

                    String commentatString="";
                    for (int i=0;i<comments.get(position).size() ;i++){
                        if(i==0)
                        commentatString = comments.get(position).get(i).replace(","," ");
                        else
                            commentatString =  "#%@#"+ comments.get(position).get(i).replace(","," ");


                    }
                mymovie.put("comments",commentatString);
                    mymovie.put("favorite", favorited.get(position).toString());
                    ((Callback) getActivity()).onItemSelected(mymovie);
                //startActivity(intent);

            }
                else{



                //    Intent intent = new Intent(getActivity(),DetailActivity.class).
                       /*     putExtra("overview",overviewsF.get(position)).
                            putExtra("poster",postersF.get(position)).
                            putExtra("title",titlesF.get(position)).
                            putExtra("dates",datesF.get(position)).
                            putExtra("rating", ratingsF.get(position)).
                            putExtra("youtube", youtubesF.get(position)).
                            putExtra("youtube2", youtubes2F.get(position)).
                            putExtra("comments", commentsF.get(position)).
                            putExtra("favorite", favorited.get(position));*/
                    mymovie.put("overview",overviewsF.get(position).replace(","," ").replace(":"," "));
                    mymovie.put("poster",postersF.get(position));
                    mymovie.put("title",titlesF.get(position).replace(","," ").replace(":"," "));
                    mymovie.put("dates",datesF.get(position));
                    mymovie.put("rating", ratingsF.get(position));
                    mymovie.put("youtube", youtubesF.get(position));
                    mymovie.put("youtube2", youtubes2F.get(position));

                    String commentatString="";
                    for (int i=0;i<comments.get(position).size() ;i++){
                        if(i==0)
                            commentatString = comments.get(position).get(i).replace(","," ");
                        else
                            commentatString =  "#%@#"+ comments.get(position).get(i).replace(","," ");


                    }
                    mymovie.put("comments", commentatString);
                    mymovie.put("favorite", favorited.get(position).toString());
                    ((Callback) getActivity()).onItemSelected(mymovie);
                  // startActivity(intent);
                }

        }}

        );
        return rootView;
    }

    private class preferenceChangeListener implements SharedPreferences.OnSharedPreferenceChangeListener{

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            gridView.setAdapter(null);
            onStart();
        }
    }
    public ArrayList<Boolean>bindFavouriteToMovies(){

        ArrayList<Boolean> result = new ArrayList<>();
        for (int i=0; i<titles.size();i++){
            result.add(false);
        }
        for(String favoritedTitles: titlesF)
        {
            for(int x = 0; x<titles.size(); x++)
            {
                if(favoritedTitles.equals(titles.get(x)))
                {
                    result.set(x,true);
                }
            }
        }
        return result;
    }

    @Override
    public  void onStart(){

     super.onStart();
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        listener =  new preferenceChangeListener();
        prefs.registerOnSharedPreferenceChangeListener(listener);

        if(prefs.getString("sortby","popularity").equals("popularity")){

            getActivity().setTitle("Most Popular Movies");
            sortByPop = true;
            sortByFavorites=false;
        }
        else if(prefs.getString("sortby","popularity").equals("rating"))
        {
            getActivity().setTitle("Highest Rated Movies");
            sortByPop = false;
            sortByFavorites=false;
        }
        else if(prefs.getString("sortby","popularity").equals("favorites"))
        {
            getActivity().setTitle("Favorited Movies");
            sortByPop = false;
            sortByFavorites=true;
        }
        TextView textView = new TextView(getActivity());
        RelativeLayout layout =  (RelativeLayout)getActivity().findViewById(R.id.relativelayout);
        loadFavouritesData();
        if(sortByFavorites) {
            if (postersF.size() == 0) {
                textView.setText("You have no favorites movies.");
                if (layout.getChildCount() == 1)
                    layout.addView(textView);
                gridView.setVisibility(GridView.GONE);
            }else {

                gridView.setVisibility(GridView.VISIBLE);
                layout.removeView(textView);
            }


           /*  if(postersF!=null&&getActivity()!=null){

                layout.removeView(textView);

                gridView.setVisibility(GridView.VISIBLE);
            }*/

            if(postersF!=null&&getActivity()!=null)
            {
              //  gridView.setAdapter(null);


                ImageAdapter adapter = new ImageAdapter(getActivity(),postersF,width);
                //adapter.notifyDataSetChanged();
                gridView.setAdapter(adapter);
               // gridView.setVisibility(GridView.VISIBLE);
            }
        }
        else {
            gridView.setVisibility(GridView.VISIBLE);
            layout.removeView(textView);

        }
        if(isNetworkAvailable())
        {

            new ImageLoadTask().execute();
        }
        else{
            TextView textView1= new TextView(getActivity());
            RelativeLayout layout1 = (RelativeLayout)getActivity().findViewById(R.id.relativelayout);
            textView1.setText("you are not connected to internet");
            if (layout1.getChildCount()==1){


                layout1.addView(textView1);
            }
            gridView.setVisibility(GridView.GONE);

        }
        }

//load favourite data methods
    public void loadFavouritesData(){
        String Url = "content://com.example.provider.Movies/movies";
        Uri movies = Uri.parse(Url);
        Cursor c = getActivity().getContentResolver().query(movies,null,null,null,"title");
        postersF = new ArrayList<String>();
        titlesF = new ArrayList<String>();
        datesF = new ArrayList<String>();
        overviewsF = new ArrayList<String>();
        favorited = new ArrayList<Boolean>();
        commentsF = new ArrayList<ArrayList<String>>();
        youtubesF = new ArrayList<String>();
        youtubes2F = new ArrayList<String>();
        ratingsF = new ArrayList<String>();
        if(c==null){return;}
        while (c.moveToNext()){
            postersF.add(c.getString(c.getColumnIndex(MovieProvider.NAME)));
            commentsF.add(convertStringToArrayList(c.getString(c.getColumnIndex(MovieProvider.REVIEW))));
            titlesF.add(c.getString(c.getColumnIndex(MovieProvider.TITLE)));
            overviewsF.add(c.getString(c.getColumnIndex(MovieProvider.OVERVIEW)));
            youtubesF.add(c.getString(c.getColumnIndex(MovieProvider.YOUTUBE1)));
            youtubes2F.add(c.getString(c.getColumnIndex(MovieProvider.YOUTUBE2)));
            datesF.add(c.getString(c.getColumnIndex(MovieProvider.DATE)));
            ratingsF.add(c.getString(c.getColumnIndex(MovieProvider.RATING)));
            favorited.add(true);


        }
    }
    public  ArrayList<String> convertStringToArrayList(String s){

        ArrayList<String> result = new ArrayList<>(Arrays.asList(s.split("divider123")));
        return result;

    }
   public boolean isNetworkAvailable(){

       ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
       return activeNetworkInfo !=null &&activeNetworkInfo.isConnected();

   }

public  class  ImageLoadTask extends AsyncTask<Void,Void,ArrayList<String>>{


    @Override
    protected ArrayList<String> doInBackground(Void... params) {
// string of the poster path every poster has path on the internet
        while (true){

            try{
                posters = new ArrayList(Arrays.asList(getPathAPI(sortByPop)));
                return posters;
            }catch (Exception e){continue;}
        }
    }
    @Override
    protected void onPostExecute(ArrayList<String>results){
        if(results!=null && getActivity()!=null){
            ImageAdapter adapter = new ImageAdapter(getActivity(),results,width);
            gridView.setAdapter(adapter);
        }
    }
    public  String [] getPathAPI(boolean sortbypop) {
        while (true) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String JSONResult;
            try {
                String urlString = null;
                if (sortbypop) {
                    urlString = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=" + API_KEY;
                } else {
                    urlString = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&vote_count.gte=500&api_key=" + API_KEY;
                }
                //make http request
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //read input stream into string
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
                    overviews = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult,"overview")));
                    titles = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult,"original_title")));
                    ratings = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult,"vote_average")));
                    dates = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult,"release_date")));
                    ids = new ArrayList<String>(Arrays.asList(getStringsFromJSON(JSONResult,"id")));
                    while(true)
                    {
                        youtubes = new ArrayList<String>(Arrays.asList(getYoutubesFromIds(ids,0)));
                        youtubes2 = new ArrayList<String>(Arrays.asList(getYoutubesFromIds(ids,1)));
                        int nullCount = 0;
                        for(int i = 0; i<youtubes.size();i++)
                        {
                            if(youtubes.get(i)==null)
                            {
                                nullCount++;
                                youtubes.set(i,"no video found");
                            }
                        }
                        for(int i = 0; i<youtubes2.size();i++)
                        {
                            if(youtubes2.get(i)==null)
                            {
                                nullCount++;
                                youtubes2.set(i,"no video found");
                            }
                        }
                        if(nullCount>2)continue;
                        break;
                    }
                    comments = getReviewsFromIds(ids);
                    return getPathsFromJson(JSONResult);
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
                    }

                }
            }
        }
    }
    public String[] getYoutubesFromIds(ArrayList<String> ids, int position)
    {
        String[] results = new String[ids.size()];
        for(int i =0; i<ids.size(); i++)
        {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String JSONResult;

            try {
                String urlString = null;
                urlString = "http://api.themoviedb.org/3/movie/" + ids.get(i) + "/videos?api_key=" + API_KEY;


                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input stream into a String
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
                    results[i] = getYoutubeFromJSON(JSONResult, position);
                } catch (JSONException E) {
                    results[i] = "no video found";
                }
            } catch (Exception e) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }
        }
        return results;

    }
    public ArrayList<ArrayList<String>> getReviewsFromIds(ArrayList<String> ids)
    {
        outerloop:
        while(true)
        {
            ArrayList<ArrayList<String>> results = new ArrayList<>();
            for(int i =0; i<ids.size(); i++)
            {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String JSONResult;

                try {
                    String urlString = null;
                    urlString = "http://api.themoviedb.org/3/movie/" + ids.get(i) + "/reviews?api_key=" + API_KEY;
                    URL url = new URL(urlString);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    //Read the input stream into a String
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
                        }
                    }
                }
            }
            return results;

        }
    }

    public ArrayList<String> getCommentsFromJSON(String JSONStringParam)throws JSONException{
        JSONObject JSONString = new JSONObject(JSONStringParam);
        JSONArray reviewsArray = JSONString.getJSONArray("results");
        ArrayList<String> results = new ArrayList<>();
        if(reviewsArray.length()==0)
        {
            results.add("No reviews found for this movie.");
            return results;
        }
        for(int i = 0; i<reviewsArray.length(); i++)
        {
            JSONObject result = reviewsArray.getJSONObject(i);
            results.add(result.getString("content"));
        }
        return results;

    }
    public String getYoutubeFromJSON(String JSONStringParam, int position) throws JSONException
    {
        JSONObject JSONString = new JSONObject(JSONStringParam);
        JSONArray youtubesArray = JSONString.getJSONArray("results");
        JSONObject youtube;
        String result = "no videos found";
        if(position ==0)
        {
            youtube = youtubesArray.getJSONObject(0);
            result = youtube.getString("key");
        }
        else if(position==1)
        {
            if(youtubesArray.length()>1)
            {
                youtube = youtubesArray.getJSONObject(1);
            }
            else{
                youtube = youtubesArray.getJSONObject(0);
            }
            result = youtube.getString("key");
        }
        return result;
    }

    public String[] getStringsFromJSON(String JSONStringParam, String param)throws JSONException{
        JSONObject JSONString = new JSONObject(JSONStringParam);
        JSONArray moviesArray = JSONString.getJSONArray("results");
        String [] result = new String[moviesArray.length()];
        for(int i = 0; i<moviesArray.length();i++)
        {
            JSONObject movie = moviesArray.getJSONObject(i);
            if(param.equals("vote_average"))
            {
                Double number = movie.getDouble("vote_average");
                String rating =Double.toString(number)+"/10";
                result[i]=rating;
            }else {

                String data = movie.getString(param);
                result[i] = data;
            }

        }
        return  result;
    }

    public  String[] getPathsFromJson(String JSONStringParam) throws JSONException{
        JSONObject JSONString = new JSONObject(JSONStringParam);
        JSONArray moviesArray = JSONString.getJSONArray("results");
        String [] result = new String[moviesArray.length()];
        for(int i = 0; i<moviesArray.length();i++)
        {
            JSONObject movie = moviesArray.getJSONObject(i);
            String moviePath = movie.getString("poster_path");
            result[i] = moviePath;
        }
        return result;
    }
}}