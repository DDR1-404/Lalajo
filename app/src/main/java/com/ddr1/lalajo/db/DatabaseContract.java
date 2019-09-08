package com.ddr1.lalajo.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final class MoviesColumns implements BaseColumns {

        public static final String AUTHORITY = "com.ddr1.lalajo.movie";
        private static final String SCHEME = "content";

        public static final String TABLE_MOVIE = "movie";
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

    public static final class TVColumns implements BaseColumns {

        public static final String AUTHORITY_TV = "com.ddr1.lalajo.tv";
        private static final String SCHEME_TV = "content";

        public static final String TABLE_TV = "tv";
        public static final String ID_TV = "id";
        public static final String POSTER_TV = "poster_path";
        public static final String TITLE_TV = "title";
        public static final String RELEASE_DATE_TV = "release_date";
        public static final String VOTE_TV = "vote_average";
        public static final String LANGUAGE_TV = "original_language";
        public static final String POPULARITY_TV = "popularity";
        public static final String OVERVIEW_TV = "overview";

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME_TV)
                .authority(AUTHORITY_TV)
                .appendPath(TABLE_TV)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

}
