package com.ddr1.lalajo.db;

import android.database.Cursor;

import com.ddr1.lalajo.model.MovieItem;
import com.ddr1.lalajo.model.TVshowItem;

import java.util.ArrayList;

import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.ID;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.POSTER;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.TITLE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.RELEASE_DATE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.VOTE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.LANGUAGE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.POPULARITY;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.OVERVIEW;

import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.ID_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.POSTER_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.TITLE_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.RELEASE_DATE_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.VOTE_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.LANGUAGE_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.POPULARITY_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.OVERVIEW_TV;

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

    public static ArrayList<TVshowItem> mapCursorToArrayList_TV(Cursor tvCursor) {

        ArrayList<TVshowItem> tVshowItems = new ArrayList<>();

        while (tvCursor.moveToNext()) {
            int id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(ID_TV));
            String poster_path = tvCursor.getString(tvCursor.getColumnIndexOrThrow(POSTER_TV));
            String title = tvCursor.getString(tvCursor.getColumnIndexOrThrow(TITLE_TV));
            String release_date = tvCursor.getString(tvCursor.getColumnIndexOrThrow(RELEASE_DATE_TV));
            String vote_average = tvCursor.getString(tvCursor.getColumnIndexOrThrow(VOTE_TV));
            String original_language = tvCursor.getString(tvCursor.getColumnIndexOrThrow(LANGUAGE_TV));
            String popularity = tvCursor.getString(tvCursor.getColumnIndexOrThrow(POPULARITY_TV));
            String overview = tvCursor.getString(tvCursor.getColumnIndexOrThrow(OVERVIEW_TV));
            tVshowItems.add(new TVshowItem(id, poster_path, title, release_date, vote_average, original_language, popularity, overview));
        }
        return tVshowItems;
    }
}
