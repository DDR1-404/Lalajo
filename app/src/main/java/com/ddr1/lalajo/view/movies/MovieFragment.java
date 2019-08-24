package com.ddr1.lalajo.view.movies;


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
import com.ddr1.lalajo.adapter.MovieAdapter;
import com.ddr1.lalajo.model.MovieItem;
import com.ddr1.lalajo.viewmodel.MainViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        adapter = new MovieAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.progressBar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getMovies().observe(this, getMovie);
        mainViewModel.setMovies("EXTRA_MOVIE");

        showLoading(true);

        adapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MovieItem data) {
                showSelectedMovie(data);
            }
        });

        return view;
    }

    private void showSelectedMovie(MovieItem movie) {
        Intent moveWithObjectIntent = new Intent(getActivity(), DetailMovieActivity.class);
        moveWithObjectIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectIntent);

    }

    private Observer<ArrayList<MovieItem>> getMovie = new Observer<ArrayList<MovieItem>>() {
        @Override
        public void onChanged(ArrayList<MovieItem> movies) {
            if (movies != null) {
                adapter.setData(movies);
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
