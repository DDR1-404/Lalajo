package com.ddr1.lalajo.callback;

import android.database.Cursor;

public interface LoadMovieCallback {
    void preExecute();

    void postExecute(Cursor movies);
}
