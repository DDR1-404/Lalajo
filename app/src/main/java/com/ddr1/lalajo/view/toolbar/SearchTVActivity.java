package com.ddr1.lalajo.view.toolbar;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ddr1.lalajo.R;
import com.ddr1.lalajo.adapter.SearchAdapter;
import com.ddr1.lalajo.model.TVshowItem;
import com.ddr1.lalajo.viewmodel.MainViewModel;

import java.util.ArrayList;

public class SearchTVActivity extends AppCompatActivity {

    SearchAdapter adapter;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MainViewModel searchViewModel;

    public static final String EXTRA_QUERY = "extra_query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);

        String query = getIntent().getStringExtra(EXTRA_QUERY);
        recyclerView = findViewById(R.id.rv_search_tv);
        progressBar = findViewById(R.id.progressBar_search_tv);

        searchViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        searchViewModel.getTvs().observe(this, getTv);
        searchViewModel.setSearchTV(query);
        showLoading(true);

        adapter = new SearchAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private Observer<ArrayList<TVshowItem>> getTv = new Observer<ArrayList<TVshowItem>>() {
        @Override
        public void onChanged(ArrayList<TVshowItem> tv) {
            if (tv != null) {
                adapter.setSearchTvShow(tv);
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
