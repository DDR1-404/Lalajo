package com.ddr1.lalajo.view.toolbar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ddr1.lalajo.R;
import com.ddr1.lalajo.adapter.SearchAdapter;
import com.ddr1.lalajo.model.MovieItem;
import com.ddr1.lalajo.viewmodel.MainViewModel;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity {

    SearchAdapter adapter;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MainViewModel searchViewModel;

    public static final String EXTRA_QUERY = "extra_query";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String query = getIntent().getStringExtra(EXTRA_QUERY);
        recyclerView = findViewById(R.id.rv_search);
        progressBar = findViewById(R.id.progressBar_search);

        searchViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        searchViewModel.getMovies().observe(this, getMovie);
        searchViewModel.setSearchMovie(query);
        showLoading(true);

        adapter = new SearchAdapter();
        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private Observer<ArrayList<MovieItem>> getMovie = new Observer<ArrayList<MovieItem>>() {
        @Override
        public void onChanged(ArrayList<MovieItem> movies) {
            if (movies != null) {
                adapter.setSearchMovies(movies);
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
