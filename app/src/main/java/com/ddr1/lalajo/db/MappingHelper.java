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

    public static ArrayList<MovieItem> mapCursorToArrayList(Cursor notesCursor) {

        ArrayList<MovieItem> movieList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(ID));
            String poster_path = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POSTER));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String release_date = notesCursor.getString(notesCursor.getColumnIndexOrThrow(RELEASE_DATE));
            String vote_average = notesCursor.getString(notesCursor.getColumnIndexOrThrow(VOTE));
            String original_language = notesCursor.getString(notesCursor.getColumnIndexOrThrow(LANGUAGE));
            String popularity = notesCursor.getString(notesCursor.getColumnIndexOrThrow(POPULARITY));
            String overview = notesCursor.getString(notesCursor.getColumnIndexOrThrow(OVERVIEW));
            movieList.add(new MovieItem(id, poster_path, title, release_date, vote_average, original_language, popularity, overview));
        }
        return movieList;
    }
}
