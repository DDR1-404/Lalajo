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
import com.ddr1.lalajo.view.movies.DetailMovieActivity;

import java.util.ArrayList;

import static com.ddr1.lalajo.db.DatabaseContract.MoviesColumns.CONTENT_URI;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Activity activity;
    private ArrayList<MovieItem> listMovies = new ArrayList<>();

    public MovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<MovieItem> getMovieList() {
        return listMovies;
    }

    public void setData(ArrayList<MovieItem> items) {
        this.listMovies.clear();
        this.listMovies.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int i) {
        MovieItem movieItem = getMovieList().get(i);
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/w342" + movieItem.getPoster_path())
                .into(holder.imgPhoto);
        holder.tvTitle.setText(movieItem.getTitle());
        holder.tvDate.setText(movieItem.getRelease_date());
        holder.tvDescription.setText(movieItem.getOverview());
    }

    @Override
    public int getItemCount() {
        return getMovieList().size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvDate, tvDescription;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvTitle = itemView.findViewById(R.id.txt_title);
            tvDate = itemView.findViewById(R.id.txt_date);
            tvDescription = itemView.findViewById(R.id.txt_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(activity, DetailMovieActivity.class);
                    Uri uri = Uri.parse(CONTENT_URI + "/" + getMovieList().get(position).getId());
                    intent.setData(uri);
                    intent.putExtra(DetailMovieActivity.EXTRA_POSITION, position);
                    intent.putExtra(DetailMovieActivity.EXTRA_MOVIE, listMovies.get(position));
                    activity.startActivityForResult(intent, DetailMovieActivity.REQUEST_UPDATE);
                }
            });
        }

    }
}
