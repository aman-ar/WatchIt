package com.example.amanarora.watchit.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.amanarora.watchit.Data.Movies;
import com.example.amanarora.watchit.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {


    public TextView title;
    public TextView rating;
    public TextView synopsis;
    public TextView releaseDate;
    public ImageView posterLabel;
    public TextView genreLabel;
    public String trailerId = "";
    public String backDropPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Movies mMovies = getIntent().getParcelableExtra("HOURLY_FORECAST");
        Log.i("JSON", mMovies.getTitle());
        String backDropPath = mMovies.getBackDrop();
        String trailerUrl = "http://api.themoviedb.org/3/movie/" + String.valueOf(mMovies.getId()) + "/videos?api_key=a1970cae6b3c53bc3a123570ad3b5401";
        String backDropUrl = "http://api.themoviedb.org/3/movie/" + String.valueOf(mMovies.getId()) + "/images?api_key=a1970cae6b3c53bc3a123570ad3b5401";
        getTrailerInfo(trailerUrl, mMovies);
        Log.i("BackDrops", backDropPath);
        Log.i("Trailer", trailerUrl);
        bindData(mMovies);
        // Log.i("abx", trailerId);

    }

    private void getTrailerInfo(String url, final Movies mMovie) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                alertUserAboutError();

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    String jsonData = response.body().string();
                    if (response.isSuccessful()) {
                        JSONObject movieInfo = new JSONObject(jsonData);
                        JSONArray data = movieInfo.getJSONArray("results");
                        JSONObject movieData = data.getJSONObject(0);
                        mMovie.setTrailerKey(movieData.getString("key"));
                        Log.i("video", movieData.getString("key"));
                        trailerId += String.valueOf(mMovie.getTrailerKey());

                        //mForecast = parseForecastDetails(jsonData);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //updateDisplay();

                                //adapter.updateDataSet(mMovie);
                                //Log.i(TAG, "Inside onResponse" + mMovie.length);
                                //return mMovie.getTrailerKey();

                            }
                        });


                    } else {
                        //alertUserAboutError();
                    }
                } catch (IOException e) {
                    //Log.e(TAG, "Exception Caught: ", e);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        });
    }


    public void bindData(Movies movie) {
        title = (TextView) findViewById(R.id.titleLabel);
        title.setText(movie.getTitle());
        rating = (TextView) findViewById(R.id.ratingLabel);
        rating.setText(movie.getRating() + "");
        synopsis = (TextView) findViewById(R.id.SynopsisLabel);
        synopsis.setText(movie.getSynopsis());
        synopsis
                .setMovementMethod(new ScrollingMovementMethod());
        releaseDate = (TextView) findViewById(R.id.releaseLabel);
        releaseDate.setText(movie.getreleaseDate());
        genreLabel = (TextView) findViewById(R.id.genreLabel);
        genreLabel.setText(movie.getGenreId());
        posterLabel = (ImageView) findViewById(R.id.posterImageView);
        Context context = posterLabel.getContext();
        Uri uri = Uri.parse("http://image.tmdb.org/t/p/w780" + movie.getBackDrop());
        Log.v("URI", uri.toString());
        Glide
                .with(context)
                .load(uri)
                .fitCenter()
                .into(posterLabel);

        Log.i("video", trailerId);
    }

    public void openTrailer(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailerId)));
    }

    private void alertUserAboutError() {
        alertDialogFragment dialog = new alertDialogFragment();
        dialog.show(getSupportFragmentManager(), "error_dialog");
    }

}
