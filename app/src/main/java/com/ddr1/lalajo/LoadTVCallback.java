package com.ddr1.lalajo;

import android.database.Cursor;

public interface LoadTVCallback {
    void preExecute();

    void postExecute(Cursor tv);
}
