package com.example.amanarora.watchit.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Aman's Laptop on 4/18/2016.
 */
public class MoviesProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.watchit.moviesprovider";
    private static final String BASE_PATH = "movies";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    // Constant to identify the requested operation
    private static final int MOVIES = 1;
    private static final int MOVIES_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public final static String CONTENT_ITEM_TYPE = "Movie";

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, MOVIES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", MOVIES_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {

        DBOpenHelper dbopenhelper = new DBOpenHelper(getContext());
        database = dbopenhelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        if (uriMatcher.match(uri) == MOVIES_ID) {
            selection = DBOpenHelper.MOVIES_ID + "=" + uri.getLastPathSegment();
        }

        return database.query(DBOpenHelper.TABLE_MOVIES, DBOpenHelper.ALL_COLUMNS, selection,
                null, null, null, DBOpenHelper.MOVIE_CREATED + " DESC");




    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(DBOpenHelper.TABLE_MOVIES, null, values);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(DBOpenHelper.TABLE_MOVIES, selection, selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_MOVIES, values, selection, selectionArgs);
    }
}
