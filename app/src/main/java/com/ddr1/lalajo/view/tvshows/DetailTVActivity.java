package com.ddr1.lalajo.view.tvshows;

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
import com.ddr1.lalajo.model.TVshowItem;


import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.CONTENT_URI_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.ID_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.POSTER_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.TITLE_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.RELEASE_DATE_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.VOTE_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.LANGUAGE_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.POPULARITY_TV;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.OVERVIEW_TV;

import java.util.Objects;

public class DetailTVActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION_TV = "extra_position";
    public static final int REQUEST_UPDATE_TV = 200;
    public static final String EXTRA_TV = "extra_movie";
    TextView tvDesc, tvTitle, tvSub, tvDate, tvVote, tvPopuler;
    ImageView imagePhoto;

    private int id;
    private int position;
    private boolean isFavorite;
    private TVshowItem tVshowItem;

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

        tVshowItem = getIntent().getParcelableExtra(EXTRA_TV);
        id = tVshowItem.getId();

        loadData();

        if (tVshowItem != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION_TV, 0);
            isFavorite = false;
        } else {
            tVshowItem = new TVshowItem();
        }

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) tVshowItem = new TVshowItem(cursor);
                cursor.close();
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(tVshowItem.getTitle());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    private void loadData() {
        String url_image = "https://image.tmdb.org/t/p/w780" + tVshowItem.getPoster_path();

        tvDate.setText(tVshowItem.getRelease_date());
        tvVote.setText(tVshowItem.getVote_average());
        tvPopuler.setText(tVshowItem.getPopularity());
        tvSub.setText(tVshowItem.getOriginal_language());
        tvTitle.setText(tVshowItem.getTitle());
        tvDesc.setText(tVshowItem.getOverview());
        Glide.with(DetailTVActivity.this)
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
                Toast.makeText(DetailTVActivity.this, R.string.add_favorite, Toast.LENGTH_SHORT).show();
            } else {
                isFavorite = false;
                item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                Toast.makeText(DetailTVActivity.this, R.string.delete_favorite, Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent();
            intent.putExtra(EXTRA_TV, tVshowItem);
            intent.putExtra(EXTRA_POSITION_TV, position);

            ContentValues values = new ContentValues();

            values.put(ID_TV, tVshowItem.getId());
            values.put(POSTER_TV, tVshowItem.getPoster_path());
            values.put(TITLE_TV, tVshowItem.getTitle());
            values.put(RELEASE_DATE_TV, tVshowItem.getRelease_date());
            values.put(VOTE_TV, tVshowItem.getVote_average());
            values.put(LANGUAGE_TV, tVshowItem.getOriginal_language());
            values.put(POPULARITY_TV, tVshowItem.getPopularity());
            values.put(OVERVIEW_TV, tVshowItem.getOverview());

            if (isFavorite) {
                getContentResolver().insert(CONTENT_URI_TV, values);
            } else {
                intent.putExtra(EXTRA_POSITION_TV, position);
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
        Uri uri = CONTENT_URI_TV.buildUpon().appendPath(String.valueOf(id)).build();
        @SuppressLint("Recycle")
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        assert cursor != null;
        return cursor.moveToFirst();
    }
}
