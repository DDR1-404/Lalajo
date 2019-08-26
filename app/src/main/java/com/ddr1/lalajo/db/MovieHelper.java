package com.ddr1.lalajo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.ID;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.TABLE_MOVIE;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.ID_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.TABLE_TV;



public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static final String DATABASE_TABLE_TV = TABLE_TV;
    private final DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;

    private SQLiteDatabase database;

    private MovieHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public Cursor queryByIdProviderMovie(String id) {
        return database.query(DATABASE_TABLE, null
                , ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProviderMovie() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , ID + " ASC");
    }

    public long insertProviderMovie(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProviderMovie(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, ID + " = ?", new String[]{id});
    }

    public int deleteProviderMovie(String id) {
        return database.delete(DATABASE_TABLE, ID + " = ?", new String[]{id});
    }

    //TV
    public Cursor queryByIdProviderTv(String id) {
        return database.query(DATABASE_TABLE_TV, null
                , ID_TV + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProviderTv() {
        return database.query(DATABASE_TABLE_TV
                , null
                , null
                , null
                , null
                , null
                , ID_TV + " ASC");
    }

    public long insertProviderTv(ContentValues values) {
        return database.insert(DATABASE_TABLE_TV, null, values);
    }

    public int updateProviderTv(String id, ContentValues values) {
        return database.update(DATABASE_TABLE_TV, values, ID_TV + " = ?", new String[]{id});
    }

    public int deleteProviderTv(String id) {
        return database.delete(DATABASE_TABLE_TV, ID_TV + " = ?", new String[]{id});
    }

}
