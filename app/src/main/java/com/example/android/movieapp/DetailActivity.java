package com.example.android.movieapp;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailActivityFragment.DETAIL_URI, getIntent().getData());
            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment )
                    .commit();
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
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void favorite(View v)
    {
        Button b = (Button)findViewById(R.id.favorite);
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
            getContentResolver().insert(MovieProvider.CONTENT_URI,values);

        }
        else
        {
            b.setText("FAVORITE");
            b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            getContentResolver().delete(Uri.parse("content://com.example.provider.Movies/movies"),
                    "title=?", new String[]{DetailActivityFragment.title});
        }
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

}
