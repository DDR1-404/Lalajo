package com.ddr1.lalajo.view.tvshows;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ddr1.lalajo.R;
import com.ddr1.lalajo.model.MovieItem;
import com.ddr1.lalajo.model.TVshowItem;
import com.ddr1.lalajo.view.movies.DetailMovieActivity;

public class DetailTVActivity extends AppCompatActivity {
    public static final String EXTRA_TV = "extra_movie";
    TextView tvDesc, tvTitle, tvSub, tvDate, tvVote, tvPopuler;
    ImageView imagePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv);
        tvDate = findViewById(R.id.movie_release_date);
        tvVote = findViewById(R.id.movie_rating);
        tvPopuler = findViewById(R.id.movie_revenue);
        tvSub = findViewById(R.id.movie_sub);
        tvTitle = findViewById(R.id.movie_title);
        tvDesc = findViewById(R.id.detail_movie_description);
        imagePhoto = findViewById(R.id.detail_movie_image);

        TVshowItem tv = getIntent().getParcelableExtra(EXTRA_TV);
        String url_image = "https://image.tmdb.org/t/p/w780" + tv.getPoster_path();

        tvDate.setText(tv.getRelease_date());
        tvVote.setText(tv.getVote_average());
        tvPopuler.setText(tv.getPopularity());
        tvSub.setText(tv.getOriginal_language());
        tvTitle.setText(tv.getTitle());
        tvDesc.setText(tv.getOverview());
        Glide.with(DetailTVActivity.this)
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imagePhoto);
    }
}
