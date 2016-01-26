package com.example.android.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by pc on 14/12/2015.
 */
public  class DetailActivityFragment extends Fragment {
    static final String DETAIL_URI = "URI";
    private String mUri;
    public static DetailActivityFragment newInstance(String index){

        DetailActivityFragment f= new DetailActivityFragment();
        Bundle args = new Bundle();
        args.putString("URI",index);
        f.setArguments(args);
        return f;
    }
    // 7a3mel hena
    public  String getShowIndex(){
        return getArguments().getString("URI");
    }
    public static String youtube;
    public static String youtube2;
    public static String overview;
    public static String rating;
    public static String date;
    public static String review;
    public static String title;
    public static String poster;
    public static boolean favorite;
    public static ArrayList<String> comments;
    public static Button b;
    private ShareActionProvider mShareActionProvider;
    public HashMap<String , String> intent;
    public String stringxCommentat;


    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
       // Bundle arguments = getArguments();
        intent = new HashMap<String, String>();
        if(getArguments() == null){return null;}
        if (getArguments() != null) {
            mUri = getArguments().getString(DetailActivityFragment.DETAIL_URI);
            //intent = new HashMap<String, String>();
            String[] pairs = mUri.split(",");
            for (int i = 0; i < pairs.length; i++) {
                String pair = pairs[i];
                String[] keyValue = pair.split("=");

                intent.put(keyValue[0].replaceAll("\\W","").replaceAll("\\s",""), String.valueOf(keyValue[1]));
            }


            // Intent intent = getActivity().getIntent();
            getActivity().setTitle("movie Details");

            review = null;

            if (intent != null && intent.get("overview") != null) {
                overview = intent.get("overview");
                TextView tv = (TextView) rootView.findViewById(R.id.overview);
                tv.setText(overview);
            }
            if (intent != null && intent.get("title") != null) {
                title = intent.get("title");
                TextView tv = (TextView) rootView.findViewById(R.id.title);
                tv.setText(title);
            }
            if (intent != null && intent.get("rating") != null) {
                rating = intent.get("rating");
                TextView tv = (TextView) rootView.findViewById(R.id.rating);
                tv.setText(rating);
            }
            if (intent != null && intent.get("dates") != null) {
                date = intent.get("dates");
                TextView tv = (TextView) rootView.findViewById(R.id.date);
                tv.setText(date);
            }
            if (intent != null && intent.get("poster") != null) {
                poster = intent.get("poster");
                ImageView iv = (ImageView) rootView.findViewById(R.id.poster);

                Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185/" + poster).resize(
                        MoviesFragment.width, (int) (MoviesFragment.width * 1.5)).into(iv);

            }
            if (intent != null && intent.get("youtube") != null) {
                youtube = intent.get("youtube");
            }
            if (intent != null && intent.get("youtube2") != null) {
                youtube2 = intent.get("youtube2");
            }

            if (intent != null && intent.get("comments") != null) {
                stringxCommentat = intent.get("comments");

                String[] pairs2 = stringxCommentat.split("#%@#");
                comments = new ArrayList( Arrays.asList((pairs2)));
                for (int i = 0; i < comments.size(); i++) {
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

                    tv.setText(comments.get(i));
                    layout.addView(divider);
                    layout.addView(tv);

                    if (review == null) {
                        review = comments.get(i);
                    } else {
                        review += "divider123" + comments.get(i);
                    }
                }
            }

            Button youtubebutton =  (Button) rootView.findViewById(R.id.trailer1);
            youtubebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trailer1(v);
                }
            });
            Button youtubebutton2 =  (Button) rootView.findViewById(R.id.trailer2);
            youtubebutton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trailer2(v);
                }

            });


            b = (Button) rootView.findViewById(R.id.favorite);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    favorite(v);
                }
            });
            if (intent != null && intent.get("favorite") != null) {
                favorite = Boolean.valueOf(intent.get("favorite"));
                if (!favorite) {
                    b.setText("FAVORITE");
                    b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

                } else {
                    b.setText("UNFAVORITE");
                    b.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);
                }
            }

        }

        return rootView;
    }
    public void trailer1(View v)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com" +
                "/watch?v=" + DetailActivityFragment.youtube));
        startActivity(browserIntent);
    }
    public void trailer2(View v)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com" +
                "/watch?v=" + DetailActivityFragment.youtube2));
        startActivity(browserIntent);
    }
    public void onOptionsItemSelected(Menu menu , MenuInflater inflater){
        inflater.inflate(R.menu.menu_detail,menu);
        MenuItem item = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if(mShareActionProvider !=null)
        {
            mShareActionProvider.setShareIntent(createShareIntent());

        }}
    public void favorite(View v)
    {
        //Button b = (Button)getActivity().findViewById(R.id.favorite);
        if(b.getText().equals("FAVORITE"))
        {
            //code to store movie data in database
            b.setText("UNFAVORITE");
            b.getBackground().setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY);

            ContentValues values = new ContentValues();
            values.put(MovieProvider.NAME,DetailActivityFragment.poster);
            values.put(MovieProvider.OVERVIEW,DetailActivityFragment.overview);
            values.put(MovieProvider.RATING,DetailActivityFragment.rating);
            values.put(MovieProvider.DATE,DetailActivityFragment.date);
            values.put(MovieProvider.REVIEW,DetailActivityFragment.review);
            values.put(MovieProvider.YOUTUBE1,DetailActivityFragment.youtube);
            values.put(MovieProvider.YOUTUBE2,DetailActivityFragment.youtube2);
            values.put(MovieProvider.TITLE,DetailActivityFragment.title);
            //line to store in data base
            getActivity().getContentResolver().insert(MovieProvider.CONTENT_URI,values);

        }
        else
        {
            b.setText("FAVORITE");
            b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            getActivity().getContentResolver().delete(Uri.parse("content://com.example.provider.Movies/movies"),
                    "title=?", new String[]{DetailActivityFragment.title});
        }
    }


    private Intent createShareIntent()
    {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this trailer for " + title + ": " +
                "https://www.youtube.com/watch?v=" + youtube);
        return shareIntent;
    }

}
