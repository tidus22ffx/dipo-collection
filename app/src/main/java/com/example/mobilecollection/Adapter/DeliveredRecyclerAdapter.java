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
import com.example.mobilecollection.ViewModel.DeliveredViewModel;

import java.util.ArrayList;

public class DeliveredRecyclerAdapter extends RecyclerView.Adapter<DeliveredRecyclerAdapter.DeliveredViewHolder> {

    private ArrayList<TodoItem> todoList = new ArrayList<>();
    public DeliveredRecyclerAdapter(ArrayList<TodoItem> todoList){
        this.todoList = todoList;
    }

    public void updateList(ArrayList<TodoItem> newTodoItems){
        todoList.clear();
        todoList = newTodoItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeliveredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_list_item, parent, false);
        DeliveredViewHolder viewHolder = new DeliveredViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredViewHolder holder, int position) {
        holder.bind(todoList.get(position));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    class DeliveredViewHolder extends RecyclerView.ViewHolder {

        private TextView contractNo;
        private TextView customerName;
        private TextView plat;
        private ImageView imageView;

        public DeliveredViewHolder(@NonNull View itemView) {
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
            imageView.setImageResource(R.drawable.ic_checklist);
        }
    }
}