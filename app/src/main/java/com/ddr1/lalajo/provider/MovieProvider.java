package com.ddr1.lalajo.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.ddr1.lalajo.db.MovieHelper;
import com.ddr1.lalajo.view.favorite.FavoriteMovieFragment;

import java.util.Objects;

import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.AUTHORITY;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.CONTENT_URI;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.TABLE_MOVIE;

@SuppressLint("Registered")
public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY,
                TABLE_MOVIE + "/#",
                MOVIE_ID);
    }

    private MovieHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        movieHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        movieHelper.open();
        long added;
        if (sUriMatcher.match(uri) == MOVIE) {
            added = movieHelper.insertProvider(contentValues);
        } else {
            added = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));

        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        movieHelper.open();
        int updated;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            updated = movieHelper.updateProvider(uri.getLastPathSegment(), contentValues);
        } else {
            updated = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));

        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        movieHelper.open();
        int deleted;
        if (sUriMatcher.match(uri) == MOVIE_ID) {
            deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI, new FavoriteMovieFragment.DataObserver(new Handler(), getContext()));

        return deleted;
    }
}
