package com.example.mobilecollection.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        private TextView orderDate;
        private TextView plat;

        public DeliveredViewHolder(@NonNull View itemView) {
            super(itemView);
            contractNo = itemView.findViewById(R.id.delivered_contract_no);
            orderDate = itemView.findViewById(R.id.delivered_order_date);
            plat = itemView.findViewById(R.id.delivered_plat);
        }

        void bind(TodoItem todoItem){
            contractNo.setText(todoItem.getContractNo());
            orderDate.setText(todoItem.getOrderDate());
            plat.setText(todoItem.getPlat());
        }
    }
}
