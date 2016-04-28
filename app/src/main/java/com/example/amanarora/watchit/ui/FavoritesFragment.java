package com.example.amanarora.watchit.ui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.amanarora.watchit.Data.DBOpenHelper;
import com.example.amanarora.watchit.Data.MoviesProvider;
import com.example.amanarora.watchit.R;
import com.example.amanarora.watchit.adapters.FavoritesAdapter;

/**
 * Created by Aman's Laptop on 4/21/2016.
 */
public class FavoritesFragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> , MainActivity.YourFragmentInterface{
    public FavoritesAdapter favAdapter;
    public Cursor readAll;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);

        DBOpenHelper dbOpenHelper = new DBOpenHelper(getActivity());
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();


        ListView list = (ListView) rootView.findViewById(R.id.favListView);
        favAdapter = new FavoritesAdapter(getActivity(), null, 0);
        list.setAdapter(favAdapter);
        getLoaderManager().initLoader(0, null, this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Favorite", "onResume()");
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), MoviesProvider.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        favAdapter.swapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        favAdapter.swapCursor(null);

    }

    @Override
    public void fragmentBecameVisible() {
        getLoaderManager().restartLoader(0, null, this);
    }
}
