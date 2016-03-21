package com.example.amanarora.watchit.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.amanarora.watchit.Data.Movies;
import com.example.amanarora.watchit.R;
import com.example.amanarora.watchit.adapters.MovieAdapter;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public final static String MOVIE_POSTERS = "MOVIE_POSTERS";

    private static final String TAG = MainActivity.class.getSimpleName();
    //private int[] mMovie = {1, 2, 3, 4, 5, 6, 7, 8, 8, 10};
    //private Movie[] movies;

    private RecyclerView.LayoutManager mLayoutManager;
    public Movies[] mMovie;
    RecyclerView mRecyclerView;
    MovieAdapter adapter;
    String apiKey = "a1970cae6b3c53bc3a123570ad3b5401";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String popMoviesUrl = "https://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;
        getMovies(popMoviesUrl);
       // Log.v(TAG, "Inside onCreate" + mMovie.length);


        //Intent intent = getIntent();
       //Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.MOVIE_POSTERS);
        //mMovie = Arrays.copyOf(parcelables, parcelables.length, Movies[].class);

        adapter = new MovieAdapter(MainActivity.this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(MainActivity.this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);

    }

    private void getMovies(String Url)
    {

        if (isNetworkAvailable()) {


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(Url).build();

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
                        //Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {

                            mMovie = getMovieDetails(jsonData);

                            //mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //updateDisplay();

                                    adapter.updateDataSet(mMovie);
                                    Log.i(TAG,"Inside onResponse" + mMovie.length);

                                }
                            });


                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception Caught: ", e);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            });
        } else {

            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_SHORT).show();
        }
    }

    public Movies[] getMovieDetails(String jsonData) throws JSONException
    {
        JSONObject movieDetails = new JSONObject(jsonData);
        JSONArray data = movieDetails.getJSONArray("results");

        Movies[] movies = new Movies[data.length()];
        for(int i=0; i< movies.length; i++)
        {
            JSONObject jsonMovie = data.getJSONObject(i);
            Log.v(TAG, "Displaying movies[i] " + jsonMovie.getString("release_date"));
            Movies movie = new Movies();

            movie.setTitle(jsonMovie.getString("original_title"));
            movie.setPoster(jsonMovie.getString("poster_path"));
            movie.setreleaseDate(jsonMovie.getString("release_date"));
            movie.setSynopsis(jsonMovie.getString("overview"));
            movie.setRating(jsonMovie.getDouble("vote_average"));

            movies[i] = movie;
            Log.v(TAG, "Displaying movies[i] " + movies[i].getreleaseDate());
        }
        //Log.v(TAG, "Displaying movies[i] " + movies.length);
        //Log.v(TAG, "Displaying movies[i] " + movies);


        return movies;
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkinfo != null && networkinfo.isConnected()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        alertDialogFragment dialog = new alertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
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
            return true;
        }
        else if(id == R.id.topRated)
        {
            String topRatedUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + apiKey;
            getMovies(topRatedUrl);
        }
        else if(id == R.id.Popular)
        {
            String popMoviesUrl = "https://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;
            getMovies(popMoviesUrl);
        }

        return super.onOptionsItemSelected(item);
    }
}
