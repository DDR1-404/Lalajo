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

import com.ddr1.favorite.LoadTVCallback;
import com.ddr1.favorite.R;
import com.ddr1.favorite.adapter.TVAdapter;
import com.ddr1.favorite.model.TVshowItem;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.ddr1.favorite.db.DatabaseContract.TVColumns.CONTENT_URI_TV;
import static com.ddr1.favorite.db.MappingHelper.mapCursorToArrayList_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTVFragment extends Fragment implements LoadTVCallback {
    private TVAdapter adapter;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";


    public FavoriteTVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);

        progressBar = view.findViewById(R.id.progressBar_fav_tv);
        RecyclerView recyclerView = view.findViewById(R.id.rv_fav_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, getContext());
        if (getActivity() != null){
            getActivity().getContentResolver().registerContentObserver(CONTENT_URI_TV, true, myObserver);
        }
        adapter = new TVAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        if (savedInstanceState == null) {
            new LoadTVsAsync(getContext(), this).execute();
        } else {
            ArrayList<TVshowItem> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setData(list);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getTvList());
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(Cursor tv) {
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<TVshowItem> tVshowItems = mapCursorToArrayList_TV(tv);
        if (tVshowItems.size() > 0) {
            adapter.setData(tVshowItems);
        } else {
            adapter.setData(new ArrayList<TVshowItem>());
        }
    }

    private static class LoadTVsAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTVCallback> weakCallback;

        private LoadTVsAsync(Context context, LoadTVCallback callback) {
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
            return context.getContentResolver().query(CONTENT_URI_TV, null, null, null, null);
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
        new LoadTVsAsync(getContext(), this).execute();
    }

}
