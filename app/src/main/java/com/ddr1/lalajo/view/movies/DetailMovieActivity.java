package com.ddr1.lalajo.view.movies;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ddr1.lalajo.R;
import com.ddr1.lalajo.model.MovieItem;

import java.util.Objects;

import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.CONTENT_URI;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.ID;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.POSTER;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.TITLE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.RELEASE_DATE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.VOTE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.LANGUAGE;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.POPULARITY;
import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.OVERVIEW;


public class DetailMovieActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_UPDATE = 200;
    TextView tvDesc, tvTitle, tvSub, tvDate, tvVote, tvPopuler;
    ImageView imagePhoto;

    private int id;
    private int position;
    private boolean isFavorite;
    private MovieItem movieItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        tvDate = findViewById(R.id.movie_release_date);
        tvVote = findViewById(R.id.movie_rating);
        tvPopuler = findViewById(R.id.movie_revenue);
        tvSub = findViewById(R.id.movie_sub);
        tvTitle = findViewById(R.id.movie_title);
        tvDesc = findViewById(R.id.detail_movie_description);
        imagePhoto = findViewById(R.id.detail_movie_image);

        movieItem = getIntent().getParcelableExtra(EXTRA_MOVIE);
        id = movieItem.getId();

        loadData();

        if (movieItem != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isFavorite = false;
        } else {
            movieItem = new MovieItem();
        }

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) movieItem = new MovieItem(cursor);
                cursor.close();
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(movieItem.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void loadData() {
        String url_image = "https://image.tmdb.org/t/p/w780" + movieItem.getPoster_path();

        tvDate.setText(movieItem.getRelease_date());
        tvVote.setText(movieItem.getVote_average());
        tvPopuler.setText(movieItem.getPopularity());
        tvSub.setText(movieItem.getOriginal_language());
        tvTitle.setText(movieItem.getTitle());
        tvDesc.setText(movieItem.getOverview());
        Glide.with(DetailMovieActivity.this)
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imagePhoto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        invalidateOptionsMenu();

        if (item.getItemId() == R.id.favorite) {

            if (!isFavorite) {
                isFavorite = true;
                item.setIcon(R.drawable.ic_favorite_black_24dp);
                Toast.makeText(DetailMovieActivity.this, R.string.add_favorite, Toast.LENGTH_SHORT).show();
            } else {
                isFavorite = false;
                item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                Toast.makeText(DetailMovieActivity.this, R.string.delete_favorite, Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent();
            intent.putExtra(EXTRA_MOVIE, movieItem);
            intent.putExtra(EXTRA_POSITION, position);

            ContentValues values = new ContentValues();

            values.put(ID, movieItem.getId());
            values.put(POSTER, movieItem.getPoster_path());
            values.put(TITLE, movieItem.getTitle());
            values.put(RELEASE_DATE, movieItem.getRelease_date());
            values.put(VOTE, movieItem.getVote_average());
            values.put(LANGUAGE, movieItem.getOriginal_language());
            values.put(POPULARITY, movieItem.getPopularity());
            values.put(OVERVIEW, movieItem.getOverview());

            if (isFavorite) {
                getContentResolver().insert(CONTENT_URI, values);
            } else {
                intent.putExtra(EXTRA_POSITION, position);
                getContentResolver().delete(Objects.requireNonNull(getIntent().getData()), null, null);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (isFavorite(id)) {
            isFavorite = true;
            menu.getItem(0).setIcon(R.drawable.ic_favorite_black_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private boolean isFavorite(int id) {
        Uri uri = CONTENT_URI.buildUpon().appendPath(String.valueOf(id)).build();
        @SuppressLint("Recycle")
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        return cursor.moveToFirst();
    }
}
