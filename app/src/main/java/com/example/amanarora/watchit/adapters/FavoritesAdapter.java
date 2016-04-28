package com.example.amanarora.watchit.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.amanarora.watchit.R;

/**
 * Created by Aman's Laptop on 4/22/2016.
 */
public class FavoritesAdapter extends CursorAdapter{

    public FavoritesAdapter(Context context, Cursor cursor , int flags)
    {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.fav_item_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView movieName = (TextView) view.findViewById(R.id.favMovieName);

        String name = cursor.getString(cursor.getColumnIndexOrThrow("movieName"));
        movieName.setText(name);
    }
}
