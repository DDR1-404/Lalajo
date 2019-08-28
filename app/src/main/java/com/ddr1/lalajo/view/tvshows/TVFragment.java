package com.ddr1.lalajo.view.tvshows;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ddr1.lalajo.R;
import com.ddr1.lalajo.adapter.TVAdapter;
import com.ddr1.lalajo.model.TVshowItem;
import com.ddr1.lalajo.viewmodel.MainViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVFragment extends Fragment {
    private TVAdapter adapter;
    private ProgressBar progressBar;
    RecyclerView recyclerView;


    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_tv_shows);
        progressBar = view.findViewById(R.id.progressBar_tv);

        showData();
        showLoading(true);

    }

    private void showData() {
        adapter = new TVAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTvs().observe(this, getTv);
        mainViewModel.setTvs();
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private Observer<ArrayList<TVshowItem>> getTv = new Observer<ArrayList<TVshowItem>>() {
        @Override
        public void onChanged(ArrayList<TVshowItem> tv) {
            if (tv != null) {
                adapter.setData(tv);
            }

            showLoading(false);

        }
    };


}
