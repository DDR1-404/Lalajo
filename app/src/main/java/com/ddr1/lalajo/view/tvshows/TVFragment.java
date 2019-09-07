package com.ddr1.lalajo.view.tvshows;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ddr1.lalajo.R;
import com.ddr1.lalajo.adapter.TVAdapter;
import com.ddr1.lalajo.model.TVshowItem;
import com.ddr1.lalajo.view.toolbar.SearchTVActivity;
import com.ddr1.lalajo.viewmodel.MainViewModel;

import java.util.ArrayList;

import static com.ddr1.lalajo.view.toolbar.SearchMovieActivity.EXTRA_QUERY;

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
        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        final MenuItem searchmenuItem = menu.findItem(R.id.search);

        searchView.setQueryHint(getString(R.string.search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String string) {
                Intent intent = new Intent(getContext(), SearchTVActivity.class);
                intent.putExtra(EXTRA_QUERY, string);
                startActivity(intent);

                searchmenuItem.getIcon().setVisible(false, false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu,inflater);
    }


}
