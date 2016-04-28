package com.example.amanarora.watchit.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.amanarora.watchit.Data.DBOpenHelper;
import com.example.amanarora.watchit.Data.Movies;
import com.example.amanarora.watchit.Data.MoviesProvider;
import com.example.amanarora.watchit.R;
import com.example.amanarora.watchit.ui.DetailsActivity;
import com.example.amanarora.watchit.ui.MoviesFragment;

/**
 * Created by Aman's Laptop on 3/20/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private static final String TAG = MoviesFragment.class.getSimpleName();
    private Context context;
    public Movies[] mMovie;
    public Movies mDetail;
    public final static String HOURLY_FORECAST = "HOURLY_FORECAST";
    private String MovieFilter ;
    private String genrelist = "";

    public MovieAdapter(Context context) {
        context = context;

    }
    public void updateDataSet(Movies[] movie)
    {
        mMovie = movie;
        notifyDataSetChanged();
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent, false);
        MovieHolder holder = new MovieHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MovieHolder holder, final int position) {

        Uri uri = Uri.parse("http://image.tmdb.org/t/p/w780/" + mMovie[position].getPoster());
        Context context = holder.posterLabel.getContext();

        Glide
                .with(context)
                .load(uri)
                .centerCrop()
                .fitCenter()
                .into(holder.posterLabel);
        holder.nameMovie.setText(mMovie[position].getTitle());
      //  for(int x = 0 ; x < mMovie[position].getGenreId().length; x++) {
       //     if (x == mMovie[position].getGenreId().length - 1) {
         //       genrelist+=(mMovie[position].getGenreId()[x]);
           //     break;
           // }
            //Log.v("Genre", mMovie[position].getGenreId()[x] +"for" + mMovie[position].getTitle());
          //  genrelist+=(mMovie[position].getGenreId()[x]);
          //  genrelist += ", ";
        //}
        //Log.v("Genre", genrelist +"for" + mMovie[position].getTitle());
        genrelist = mMovie[position].getGenreId();
        holder.genreMovie.setText(genrelist);
        genrelist = "";
        mDetail = mMovie[position];

        if (mMovie[position].getIsFavorite() == 1 )
        {
            holder.checkBox.setChecked(true);
        }
        else
        {
            holder.checkBox.setChecked(false);

        }

        holder.posterLabel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i(TAG, mMovie[position].getTitle());

                Log.i(TAG, String.valueOf(mMovie[position].getId()));
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(HOURLY_FORECAST, mMovie[position]);
                view.getContext().startActivity(intent);
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, mMovie[position].getTitle());
                MovieFilter = DBOpenHelper.MOVIES_ID + "=" + mMovie[position].getId();
                if (holder.checkBox.isChecked() == true )
                {
                    insertNote(v.getContext(), mMovie[position].getId(), mMovie[position].getTitle());
                    mMovie[position].setIsFavorite(1);
                }
                else
                {
                    deleteNote(v.getContext(),MovieFilter);
                    mMovie[position].setIsFavorite(0);
                }

            }
        });
        //holder.movieLabel.setText(mMovie[position] + "");
    }

    @Override
    public int getItemCount() {

        if(mMovie != null){
        return mMovie.length;}

        else
            return 0;
        //return 0;
    }

    private void insertNote(Context context,Integer movieId, String movieName) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.MOVIES_ID, movieId.toString());
        values.put(DBOpenHelper.MOVIES_NAME, movieName);
        Uri movieUri = context.getContentResolver().insert(MoviesProvider.CONTENT_URI, values);
        Log.d("MoviesFragment", "Inserted Movie " + movieUri.getLastPathSegment());
    }

    private void deleteNote(Context context, String movieFilter)
    {
        ContentValues values = new ContentValues();
        context.getContentResolver().delete(MoviesProvider.CONTENT_URI, movieFilter, null);
        Log.d("MoviesFragment", "Removed Movie " + MovieFilter);
    }

    public class MovieHolder extends RecyclerView.ViewHolder {

        public ImageView posterLabel;
        public CheckBox checkBox;
        public TextView nameMovie;
        public TextView genreMovie;

        public MovieHolder(View itemView) {
            super(itemView);

            posterLabel = (ImageView) itemView.findViewById(R.id.posterLabel);
            checkBox = (CheckBox) itemView.findViewById(R.id.favoriteButton);
            nameMovie = (TextView) itemView.findViewById(R.id.nameTextView);
            genreMovie = (TextView) itemView.findViewById(R.id.genreTextView);
        }
    }

}
