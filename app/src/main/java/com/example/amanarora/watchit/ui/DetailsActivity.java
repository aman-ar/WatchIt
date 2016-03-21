package com.example.amanarora.watchit.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amanarora.watchit.Data.Movies;
import com.example.amanarora.watchit.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity{


    public TextView title;
    public TextView rating;
    public TextView synopsis;
    public TextView releaseDate;
    public ImageView posterLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Movies mMovies = getIntent().getParcelableExtra("HOURLY FORECAST");
        //Log.i("abx", mMovies.getTitle());
        bindData(mMovies);

    }

    public void bindData(Movies movie)
    {
        title = (TextView) findViewById(R.id.titleLabel);
        title.setText(movie.getTitle());
        rating = (TextView) findViewById(R.id.ratingLabel);
        rating.setText(movie.getRating() + "");
        synopsis = (TextView) findViewById(R.id.SynopsisLabel);
        synopsis.setText(movie.getSynopsis());
        releaseDate = (TextView) findViewById(R.id.releaseLabel);
        releaseDate.setText(movie.getreleaseDate());
        posterLabel = (ImageView) findViewById(R.id.posterImageView);
        Uri uri = Uri.parse("http://image.tmdb.org/t/p/w500/" + movie.getPoster());
        Context context = getBaseContext();
        Picasso.with(context).load(uri).resize(400,200)
                .into(posterLabel);
    }

}
