package com.ddr1.lalajo.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ddr1.lalajo.R;
import com.ddr1.lalajo.db.DatabaseHelper;
import com.ddr1.lalajo.model.MovieItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.ddr1.lalajo.db.MovieHelper.DATABASE_TABLE;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<MovieItem> movieList = new ArrayList<>();
    private final Context mContext;
    private int widgetId;

    StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        movieList.clear();
        getFavoriteMovies(mContext);

    }

    private void getFavoriteMovies(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        Cursor cursor = database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , null);

        if (cursor != null && cursor.moveToFirst()){
            do {
                MovieItem movies = new MovieItem(cursor);
                movieList.add(movies);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        MovieItem movies = movieList.get(position);
        try {
            Bitmap preview = Glide.with(mContext)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w342" + movies.getPoster_path())
                    .apply(new RequestOptions().fitCenter())
                    .submit()
                    .get();
            remoteViews.setImageViewBitmap(R.id.imageView, preview);
            remoteViews.setTextViewText(R.id.txt_title, movies.getTitle());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Bundle extras= new Bundle();
        extras.putInt(ImageBannerWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
