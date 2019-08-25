package com.ddr1.lalajo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.TABLE_MOVIE;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbmovie";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s REAL NOT NULL)",
            TABLE_MOVIE,
            MoviesColumns.ID,
            MoviesColumns.POSTER,
            MoviesColumns.TITLE,
            MoviesColumns.RELEASE_DATE,
            MoviesColumns.VOTE,
            MoviesColumns.LANGUAGE,
            MoviesColumns.POPULARITY,
            MoviesColumns.OVERVIEW
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(db);
    }

}
