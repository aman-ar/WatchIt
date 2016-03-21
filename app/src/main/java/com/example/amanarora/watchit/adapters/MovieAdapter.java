package com.example.amanarora.watchit.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.amanarora.watchit.Data.Movies;
import com.example.amanarora.watchit.R;
import com.example.amanarora.watchit.ui.DetailsActivity;
import com.example.amanarora.watchit.ui.MainActivity;

/**
 * Created by Aman's Laptop on 3/20/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;
    public Movies[] mMovie;
    public Movies mDetail;
    public final static String HOURLY_FORECAST = "HOURLY FORECAST";

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
    public void onBindViewHolder(MovieHolder holder, final int position) {

        //Uri uri = Uri.parse("http://image.tmdb.org/t/p/w185//inVq3FRqcYIRl2la8iZikYYxFNR.jpg");
        Uri uri = Uri.parse("http://image.tmdb.org/t/p/w500/" + mMovie[position].getPoster());
        Context context = holder.posterLabel.getContext();
        /*Picasso.with(context).load(uri)
                .into(holder.posterLabel);
        */
        Glide
                .with(context)
                .load(uri)
                .into(holder.posterLabel);
        mDetail = mMovie[position];

        holder.posterLabel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.i(TAG, mMovie[position].getTitle());
                //Log.i(TAG, mDetail.getTitle());
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra(HOURLY_FORECAST, mMovie[position]);
               // Log.i(TAG, mDetail.getTitle());
                Log.i(TAG, mMovie[position].getTitle());
                view.getContext().startActivity(intent);
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

    public class MovieHolder extends RecyclerView.ViewHolder {

        public ImageView posterLabel;

        public MovieHolder(View itemView) {
            super(itemView);

            posterLabel = (ImageView) itemView.findViewById(R.id.posterLabel);

        }
    }
}
