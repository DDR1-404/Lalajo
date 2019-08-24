package com.ddr1.lalajo.adapter;

import android.app.Activity;
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

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<MovieItem> listMovies = new ArrayList<>();

    public void setData(ArrayList<MovieItem> items) {
        listMovies.clear();
        listMovies.addAll(items);
        notifyDataSetChanged();
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }


    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int i) {
        holder.bind(listMovies.get(i));
    }

    public interface OnItemClickCallback {
        void onItemClicked(MovieItem data);
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvDate, tvDescription;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvTitle = itemView.findViewById(R.id.txt_title);
            tvDate = itemView.findViewById(R.id.txt_date);
            tvDescription = itemView.findViewById(R.id.txt_description);
        }

        public void bind(MovieItem movieItem) {
            String url_image = "https://image.tmdb.org/t/p/w342" + movieItem.getPoster_path();
            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPhoto);
            tvTitle.setText(movieItem.getTitle());
            tvDate.setText(movieItem.getRelease_date());
            tvDescription.setText(movieItem.getOverview());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(listMovies.get(getAdapterPosition()));
                }
            });
        }
    }
}
