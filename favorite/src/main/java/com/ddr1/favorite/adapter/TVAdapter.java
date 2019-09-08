package com.ddr1.favorite.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ddr1.favorite.R;
import com.ddr1.favorite.model.TVshowItem;
import com.ddr1.favorite.view.DetailTVActivity;

import java.util.ArrayList;

import static com.ddr1.favorite.db.DatabaseContract.TVColumns.CONTENT_URI_TV;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVViewHolder> {

    private final Activity activity;
    private ArrayList<TVshowItem> listTv = new ArrayList<>();

    public TVAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<TVshowItem> getTvList() {
        return listTv;
    }

    public void setData(ArrayList<TVshowItem> items) {
        this.listTv.clear();
        this.listTv.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVAdapter.TVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new TVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVAdapter.TVViewHolder holder, int i) {
        TVshowItem tVshowItem = getTvList().get(i);
        Glide.with(activity)
                .load("https://image.tmdb.org/t/p/w342" + tVshowItem.getPoster_path())
                .into(holder.imgPhoto);
        holder.tvTitle.setText(tVshowItem.getTitle());
        holder.tvDate.setText(tVshowItem.getRelease_date());
        holder.tvDescription.setText(tVshowItem.getOverview());
    }

    @Override
    public int getItemCount() {
        return getTvList().size();
    }

    class TVViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvDate, tvDescription;

        TVViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvTitle = itemView.findViewById(R.id.txt_title);
            tvDate = itemView.findViewById(R.id.txt_date);
            tvDescription = itemView.findViewById(R.id.txt_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(activity, DetailTVActivity.class);
                    Uri uri = Uri.parse(CONTENT_URI_TV + "/" + getTvList().get(position).getId());
                    intent.setData(uri);
                    intent.putExtra(DetailTVActivity.EXTRA_POSITION_TV, position);
                    intent.putExtra(DetailTVActivity.EXTRA_TV, listTv.get(position));
                    activity.startActivityForResult(intent, DetailTVActivity.REQUEST_UPDATE_TV);
                }
            });
        }
    }
}

