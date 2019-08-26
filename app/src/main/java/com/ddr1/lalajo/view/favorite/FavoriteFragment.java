package com.ddr1.lalajo.view.favorite;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ddr1.lalajo.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    FrameLayout tabMovie, tabTV;
    View view1, view2;
    TextView tvmovie, tvtv;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        init(view);

        tabMovie.setOnClickListener(clik);
        tabTV.setOnClickListener(clik);

        loadPage(new FavoriteMovieFragment());
        tvmovie.setTextColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.colorPrimary));

        return view;
    }

    public void init(View v){
        tabMovie = v.findViewById(R.id.tab_movie);
        tabTV = v.findViewById(R.id.tab_tv);
        view1 = v.findViewById(R.id.view1);
        view2 = v.findViewById(R.id.view2);
        tvmovie = v.findViewById(R.id.tvmovie);
        tvtv = v.findViewById(R.id.tvtv);
    }

    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tab_movie:
                    loadPage(new FavoriteMovieFragment());

                    tvmovie.setTextColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.colorPrimary));
                    tvtv.setTextColor(getActivity().getResources().getColor(R.color.colorAccent));

                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.INVISIBLE);
                    break;
                case R.id.tab_tv:
                    loadPage(new FavoriteTVFragment());

                    tvmovie.setTextColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.colorAccent));
                    tvtv.setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));

                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private void loadPage(Fragment fragment) {

        if (fragment != null) {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerpage, fragment)
                    .commit();
        }
    }

}
