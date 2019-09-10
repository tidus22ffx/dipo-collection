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

public class DeliveredRecyclerAdapter extends RecyclerView.Adapter<DeliveredRecyclerAdapter.DeliveredViewHolder> {
    private ClickListener onClickListener;
    private ArrayList<TodoItem> todoList = new ArrayList<>();
    public DeliveredRecyclerAdapter(ArrayList<TodoItem> todoList){
        this.todoList = todoList;
    }

    public void updateList(ArrayList<TodoItem> newTodoItems, ClickListener onClick){
        todoList.clear();
        todoList = newTodoItems;
        notifyDataSetChanged();
        onClickListener = onClick;
    }

    @NonNull
    @Override
    public DeliveredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_list_item, parent, false);
        DeliveredViewHolder viewHolder = new DeliveredViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveredViewHolder holder, final int position) {
        holder.bind(todoList.get(position));
        final int id = holder.getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(id);
            }
        });
    }

    public interface ClickListener {
        void onClick(int id);
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
        private int itemId;

        public int getId() {
            return itemId;
        }

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
            itemId = todoItem.getId();
        }
    }
}
