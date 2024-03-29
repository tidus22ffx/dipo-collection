package com.example.mobilecollection.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.HomeMenu;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<HomeMenu> mHome = new ArrayList<>();
    private Context mContext;
    Listener mlistener;

    public HomeAdapter(List<HomeMenu> mHome, Context mContext, Listener mListener) {
        this.mHome = mHome;
        this.mContext = mContext;
        this.mlistener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.home_recycler_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mHome.get(position).getImg())
                .into(holder.circleImageView);

        holder.title.setText(mHome.get(position).getTitle());
        holder.desc.setText(mHome.get(position).getDesc());
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHome.size();
    }

    public interface Listener {
        void onClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView circleImageView;
        TextView title, desc;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.imgItem);
            title = itemView.findViewById(R.id.titleItem);
            desc = itemView.findViewById(R.id.descItem);
            cv = itemView.findViewById(R.id.cvItem);
        }
    }
}
