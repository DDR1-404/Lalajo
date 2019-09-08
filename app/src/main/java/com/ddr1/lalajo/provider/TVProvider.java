package com.ddr1.lalajo.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import androidx.annotation.NonNull;

import com.ddr1.lalajo.db.MovieHelper;
import com.ddr1.lalajo.view.favorite.FavoriteTVFragment;

import java.util.Objects;

import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.AUTHORITY_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.CONTENT_URI_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.TABLE_TV;

@SuppressLint("Registered")
public class TVProvider extends ContentProvider {
    private static final int TV = 1;
    private static final int TV_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY_TV, TABLE_TV, TV);
        sUriMatcher.addURI(AUTHORITY_TV,
                TABLE_TV + "/#",
                TV_ID);
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
            case TV:
                cursor = movieHelper.queryProviderTv();
                break;
            case TV_ID:
                cursor = movieHelper.queryByIdProviderTv(uri.getLastPathSegment());
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
        if (sUriMatcher.match(uri) == TV) {
            added = movieHelper.insertProviderTv(contentValues);
        } else {
            added = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_TV, new FavoriteTVFragment.DataObserver(new Handler(), getContext()));

        return Uri.parse(CONTENT_URI_TV + "/" + added);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        movieHelper.open();
        int updated;
        if (sUriMatcher.match(uri) == TV_ID) {
            updated = movieHelper.updateProviderTv(uri.getLastPathSegment(), contentValues);
        } else {
            updated = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_TV, new FavoriteTVFragment.DataObserver(new Handler(), getContext()));

        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        movieHelper.open();
        int deleted;
        if (sUriMatcher.match(uri) == TV_ID) {
            deleted = movieHelper.deleteProviderTv(uri.getLastPathSegment());
        } else {
            deleted = 0;
        }

        Objects.requireNonNull(getContext()).getContentResolver().notifyChange(CONTENT_URI_TV, new FavoriteTVFragment.DataObserver(new Handler(), getContext()));

        return deleted;
    }


}
