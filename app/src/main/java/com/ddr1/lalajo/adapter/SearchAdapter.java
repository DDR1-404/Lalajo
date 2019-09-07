package com.ddr1.lalajo.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ddr1.lalajo.R;
import com.ddr1.lalajo.model.MovieItem;
import com.ddr1.lalajo.model.TVshowItem;
import com.ddr1.lalajo.view.movies.DetailMovieActivity;
import com.ddr1.lalajo.view.tvshows.DetailTVActivity;

import java.util.ArrayList;

import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.CONTENT_URI;
import static com.ddr1.lalajo.db.DatabaseContract.TVColumns.CONTENT_URI_TV;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    private ArrayList<TVshowItem> tVshowItems = new ArrayList<>();
    private ArrayList<MovieItem> movieItems = new ArrayList<>();

    private ArrayList<MovieItem> getMovieList() {
        return movieItems;
    }

    private ArrayList<TVshowItem> getTvList() {
        return tVshowItems;
    }

    public void setSearchTvShow(ArrayList<TVshowItem> tVshowItemArrayList) {
        tVshowItems.clear();
        tVshowItems.addAll(tVshowItemArrayList);
        notifyDataSetChanged();
    }

    public void setSearchMovies(ArrayList<MovieItem> movieItemArrayList) {
        movieItems.clear();
        movieItems.addAll(movieItemArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv, viewGroup, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int i) {
        if (movieItems.size() > 0) {
            holder.bind(movieItems.get(i));
        } else if (tVshowItems.size() > 0) {
            holder.bindTv(tVshowItems.get(i));
        }

    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (getMovieList().size() > 0) {
            size = getMovieList().size();
        } else if (getTvList().size() > 0) {
            size = getTvList().size();
        }
        return size;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView tvTitle, tvDate, tvDescription;

        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo_tv);
            tvTitle = itemView.findViewById(R.id.txt_title_tv);
            tvDate = itemView.findViewById(R.id.txt_date_tv);
            tvDescription = itemView.findViewById(R.id.txt_description_tv);
        }

        void bind(MovieItem movieItem) {
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w342" + movieItem.getPoster_path())
                    .into(imgPhoto);
            tvTitle.setText(movieItem.getTitle());
            tvDate.setText(movieItem.getRelease_date());
            tvDescription.setText(movieItem.getOverview());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(itemView.getContext(), DetailMovieActivity.class);
                    Uri uri = Uri.parse(CONTENT_URI + "/" + getMovieList().get(position).getId());
                    intent.setData(uri);
                    intent.putExtra(DetailMovieActivity.EXTRA_POSITION, position);
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieItems.get(position));
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        void bindTv(TVshowItem tVshowItem) {
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w342" + tVshowItem.getPoster_path())
                    .into(imgPhoto);
            tvTitle.setText(tVshowItem.getTitle());
            tvDate.setText(tVshowItem.getRelease_date());
            tvDescription.setText(tVshowItem.getOverview());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(itemView.getContext(), DetailTVActivity.class);
                    Uri uri = Uri.parse(CONTENT_URI_TV + "/" + getTvList().get(position).getId());
                    intent.setData(uri);
                    intent.putExtra(DetailTVActivity.EXTRA_POSITION_TV, position);
                    intent.putExtra(DetailTVActivity.EXTRA_TV, tVshowItems.get(position));
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
