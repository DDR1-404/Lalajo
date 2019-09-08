package com.ddr1.favorite.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final class MoviesColumns implements BaseColumns {

        static final String AUTHORITY = "com.ddr1.lalajo.movie";
        private static final String SCHEME = "content";

        static final String TABLE_MOVIE = "movie";
        public static final String ID = "id";
        public static final String POSTER = "poster_path";
        public static final String TITLE = "title";
        public static final String RELEASE_DATE = "release_date";
        public static final String VOTE = "vote_average";
        public static final String LANGUAGE = "original_language";
        public static final String POPULARITY = "popularity";
        public static final String OVERVIEW = "overview";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

}
