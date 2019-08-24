package com.ddr1.lalajo.view.tvshows;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
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
    private MainViewModel mainViewModel;


    public TVFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        adapter = new TVAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.rv_tv_shows);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressBar_tv);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getTvs().observe(this, getTv);
        mainViewModel.setTvs("EXTRA_TV");

        showLoading(true);

        adapter.setOnItemClickCallback(new TVAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(TVshowItem data) {
                showSelectedTV(data);
            }
        });
        return view;
    }

    private void showSelectedTV(TVshowItem tVshowItem) {
        Intent moveWithObjectIntent = new Intent(getActivity(), DetailTVActivity.class);
        moveWithObjectIntent.putExtra(DetailTVActivity.EXTRA_TV, tVshowItem);
        startActivity(moveWithObjectIntent);

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

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
