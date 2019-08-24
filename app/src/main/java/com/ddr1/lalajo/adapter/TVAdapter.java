package com.ddr1.lalajo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ddr1.lalajo.R;
import com.ddr1.lalajo.model.TVshowItem;

import java.util.ArrayList;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVViewHolder> {

    private ArrayList<TVshowItem> listTv = new ArrayList<>();

    public void setData(ArrayList<TVshowItem> items) {
        listTv.clear();
        listTv.addAll(items);
        notifyDataSetChanged();
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public TVAdapter.TVViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv, viewGroup, false);
        return new TVViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVAdapter.TVViewHolder holder, int i) {
        holder.bind(listTv.get(i));
    }

    public interface OnItemClickCallback {
        void onItemClicked(TVshowItem data);
    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    public class TVViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvTitle, tvDate, tvDescription;

        public TVViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo_tv);
            tvTitle = itemView.findViewById(R.id.txt_title_tv);
            tvDate = itemView.findViewById(R.id.txt_date_tv);
            tvDescription = itemView.findViewById(R.id.txt_description_tv);
        }

        public void bind(TVshowItem tVshowItem) {
            String url_image = "https://image.tmdb.org/t/p/w500" + tVshowItem.getPoster_path();
            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPhoto);
            tvTitle.setText(tVshowItem.getTitle());
            tvDate.setText(tVshowItem.getRelease_date());
            tvDescription.setText(tVshowItem.getOverview());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickCallback.onItemClicked(listTv.get(getAdapterPosition()));
                }
            });
        }
    }
}
