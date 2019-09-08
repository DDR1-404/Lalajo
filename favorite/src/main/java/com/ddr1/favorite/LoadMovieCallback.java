package com.ddr1.favorite;

import android.database.Cursor;

public interface LoadMovieCallback {
    void preExecute();

    void postExecute(Cursor movies);
}
