package com.ddr1.favorite;

import android.database.Cursor;

public interface LoadTVCallback {
    void preExecute();

    void postExecute(Cursor tv);
}
