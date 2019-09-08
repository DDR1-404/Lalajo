package com.ddr1.favorite.view;


import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ddr1.favorite.LoadMovieCallback;
import com.ddr1.favorite.R;
import com.ddr1.favorite.adapter.MovieAdapter;
import com.ddr1.favorite.model.MovieItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.ddr1.favorite.db.DatabaseContract.MoviesColumns.CONTENT_URI;
import static com.ddr1.favorite.db.MappingHelper.mapCursorToArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements LoadMovieCallback {
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";


    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);

        progressBar = view.findViewById(R.id.progressBar_fav_movie);
        RecyclerView recyclerView = view.findViewById(R.id.rv_movies_fav);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new MovieAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);
        }

        if (savedInstanceState == null) {
            new LoadMoviesAsync(getContext(), this).execute();
        } else {
            ArrayList<MovieItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getMovieList());
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(Cursor movies) {
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<MovieItem> movieItems = mapCursorToArrayList(movies);
        if (movieItems.size() > 0) {
            adapter.setData(movieItems);
        } else {
            adapter.setData(new ArrayList<MovieItem>());
        }
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadMovieCallback> weakCallback;

        private LoadMoviesAsync(Context context, LoadMovieCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMoviesAsync(getContext(), this).execute();
    }

}

