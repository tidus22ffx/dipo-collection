package com.example.mobilecollection.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;

import java.util.ArrayList;

public class PendingRecyclerAdapter extends RecyclerView.Adapter<PendingRecyclerAdapter.PendingViewHolder> {

    private ArrayList<TodoItem> pendingList = new ArrayList<>();
    public PendingRecyclerAdapter(ArrayList<TodoItem> pendingList){
        this.pendingList = pendingList;
    }

    public void updateList(ArrayList<TodoItem> newTodoItems){
        pendingList.clear();
        pendingList = newTodoItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_list_item, parent, false);
        PendingRecyclerAdapter.PendingViewHolder viewHolder = new PendingRecyclerAdapter.PendingViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PendingViewHolder holder, int position) {
        holder.bind(pendingList.get(position));
    }

    @Override
    public int getItemCount() {
        return pendingList.size();
    }

    class PendingViewHolder extends RecyclerView.ViewHolder {

        private TextView contractNo;
        private TextView customerName;
        private TextView plat;
        private ImageView imageView;

        public PendingViewHolder(@NonNull View itemView) {
            super(itemView);
            contractNo = itemView.findViewById(R.id.delivered_contract_no);
            customerName = itemView.findViewById(R.id.delivered_customer_name);
            plat = itemView.findViewById(R.id.delivered_plat);
            imageView = itemView.findViewById(R.id.delivered_image);
        }

        void bind(TodoItem todoItem){
            contractNo.setText(todoItem.getContractNo());
            customerName.setText(todoItem.getCustomerName());
            plat.setText(todoItem.getPlat());
            imageView.setImageResource(R.drawable.ic_pending_list);
        }
    }
}
