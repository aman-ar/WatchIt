package com.example.amanarora.watchit.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.amanarora.watchit.Data.DBOpenHelper;
import com.example.amanarora.watchit.Data.Movies;
import com.example.amanarora.watchit.Data.MoviesProvider;
import com.example.amanarora.watchit.R;
import com.example.amanarora.watchit.adapters.AndroidDatabaseManager;
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

public class MoviesFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> , MainActivity.YourFragmentInterface{

    public final static String MOVIE_POSTERS = "MOVIE_POSTERS";

    private static final String TAG = MoviesFragment.class.getSimpleName();

    private RecyclerView.LayoutManager mLayoutManager;
    public Movies[] mMovie;
    RecyclerView mRecyclerView;
    MovieAdapter adapter;
    String apiKey = "a1970cae6b3c53bc3a123570ad3b5401";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        String popMoviesUrl = "https://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;
        getMovies(popMoviesUrl);
        getLoaderManager().initLoader(0, null, this);

        // Log.v(TAG, "Inside onCreate" + mMovie.length);


        //Intent intent = getIntent();
        //Parcelable[] parcelables = intent.getParcelableArrayExtra(MoviesFragment.MOVIE_POSTERS);
        //mMovie = Arrays.copyOf(parcelables, parcelables.length, Movies[].class);

        adapter = new MovieAdapter(rootView.getContext());
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);

        setHasOptionsMenu(true);

        return rootView;
    }

    private void getMovies(String Url) {

        if (isNetworkAvailable()) {


            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(Url).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                    alertUserAboutError();

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });
                        String jsonData = response.body().string();
                        //Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {

                            mMovie = getMovieDetails(jsonData);

                            //mForecast = parseForecastDetails(jsonData);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //updateDisplay();

                                    adapter.updateDataSet(mMovie);
                                    Log.i(TAG, "Inside onResponse" + mMovie.length);

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

            Toast.makeText(getActivity(), R.string.network_unavailable_message, Toast.LENGTH_SHORT).show();
        }
    }

    public Movies[] getMovieDetails(String jsonData) throws JSONException {
        JSONObject movieDetails = new JSONObject(jsonData);
        JSONArray data = movieDetails.getJSONArray("results");

        Movies[] movies = new Movies[data.length()];
        for (int i = 0; i < movies.length; i++) {
            JSONObject jsonMovie = data.getJSONObject(i);
            Log.v(TAG, "Displaying movies[i] " + jsonMovie.getString("release_date"));
            Movies movie = new Movies();

            movie.setTitle(jsonMovie.getString("original_title"));
            movie.setPoster(jsonMovie.getString("poster_path"));
            movie.setreleaseDate(jsonMovie.getString("release_date"));
            movie.setSynopsis(jsonMovie.getString("overview"));
            movie.setRating(jsonMovie.getDouble("vote_average"));
            movie.setId(jsonMovie.getInt("id"));
            movie.setBackDrop(jsonMovie.getString("backdrop_path"));
            Log.v(TAG, String.valueOf(jsonMovie.getString("backdrop_path")));
            JSONArray genreData = jsonMovie.getJSONArray("genre_ids");

            int[] genreList = new int[genreData.length()];
            for (int j = 0; j < genreData.length(); j++) {
                genreList[j] = genreData.getInt(j);
                Log.v(TAG, String.valueOf(genreList[j]));
            }
            movie.setGenreId(genreList);
            movies[i] = movie;
            Log.v(TAG, "Displaying movies[i] " + movies[i].getId());
            //insertNote( movie.getTitle());
        }
        //Log.v(TAG, "Displaying movies[i] " + movies.length);
        //Log.v(TAG, "Displaying movies[i] " + movies);


        return movies;
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
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
        } else if (id == R.id.topRated) {
            String topRatedUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + apiKey;
            getMovies(topRatedUrl);
        } else if (id == R.id.Popular) {
            String popMoviesUrl = "https://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;
            getMovies(popMoviesUrl);
        } else if (id == R.id.ShowDB) {
            Intent dbmanager = new Intent(getActivity(), AndroidDatabaseManager.class);
            startActivity(dbmanager);
        }

        return super.onOptionsItemSelected(item);
    }

    private void insertNote(String movieId, String movieName) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.MOVIES_ID, movieId);
        //values.put(DBOpenHelper.MOVIES_NAME, movieName);
        Uri movieUri = getActivity().getContentResolver().insert(MoviesProvider.CONTENT_URI, values);
        Log.d("MoviesFragment", "Inserted Movie " + movieUri.getLastPathSegment());
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.CursorLoader(getActivity(), MoviesProvider.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }


    @Override
    public void fragmentBecameVisible() {
        getLoaderManager().restartLoader(0, null, this);
    }
}
