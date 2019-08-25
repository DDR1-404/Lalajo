package com.ddr1.lalajo;

import android.database.Cursor;

public interface LoadMovieCallback {
    void preExecute();

    void postExecute(Cursor movies);
}
