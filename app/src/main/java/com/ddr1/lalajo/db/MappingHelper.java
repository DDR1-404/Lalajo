package com.ddr1.lalajo.db;

import android.database.Cursor;

import com.ddr1.lalajo.model.MovieItem;

import java.util.ArrayList;

import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.ID;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.POSTER;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.TITLE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.RELEASE_DATE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.VOTE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.LANGUAGE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.POPULARITY;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.OVERVIEW;

public class MappingHelper {

    public static ArrayList<MovieItem> mapCursorToArrayList(Cursor movieCursor) {

        ArrayList<MovieItem> movieList = new ArrayList<>();

        while (movieCursor.moveToNext()) {
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(ID));
            String poster_path = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POSTER));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(TITLE));
            String release_date = movieCursor.getString(movieCursor.getColumnIndexOrThrow(RELEASE_DATE));
            String vote_average = movieCursor.getString(movieCursor.getColumnIndexOrThrow(VOTE));
            String original_language = movieCursor.getString(movieCursor.getColumnIndexOrThrow(LANGUAGE));
            String popularity = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POPULARITY));
            String overview = movieCursor.getString(movieCursor.getColumnIndexOrThrow(OVERVIEW));
            movieList.add(new MovieItem(id, poster_path, title, release_date, vote_average, original_language, popularity, overview));
        }
        return movieList;
    }
}
