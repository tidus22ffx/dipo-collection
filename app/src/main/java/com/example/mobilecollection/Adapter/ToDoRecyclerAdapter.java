package com.example.mobilecollection.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilecollection.R;
import com.example.mobilecollection.Repository.Model.TodoItem;
import com.example.mobilecollection.View.ToDoListActivity;

import java.util.ArrayList;

public class ToDoRecyclerAdapter extends RecyclerView.Adapter<ToDoRecyclerAdapter.ToDoViewHolder> {

    private ArrayList<TodoItem> todoList = new ArrayList<>();
    Listener mlistener;

    public ToDoRecyclerAdapter(ArrayList<TodoItem> todoList, Listener listener){
        this.todoList = todoList;
        this.mlistener = listener;
    }

    public void updateList(ArrayList<TodoItem> newTodoItems){
        todoList.clear();
        todoList = newTodoItems;
        notifyDataSetChanged();
    }

    public interface Listener {
        void onClick(int position);
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list_item, parent, false);
        ToDoViewHolder viewHolder = new ToDoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, final int position) {
        holder.bind(todoList.get(position));
        holder.toDoCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder {

        TextView hob, penanganan, noKontrak, namaCust, alamat, balance, currBalance, ptp, nopol, bucket;
        CardView toDoCv;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);
            hob = itemView.findViewById(R.id.to_do_hobAction);
            penanganan = itemView.findViewById(R.id.to_do_penanganan);
            noKontrak = itemView.findViewById(R.id.to_do_noKontrak);
            namaCust = itemView.findViewById(R.id.to_do_namaCustomer);
            alamat = itemView.findViewById(R.id.to_do_alamat);
            balance = itemView.findViewById(R.id.to_do_balance);
            currBalance = itemView.findViewById(R.id.to_do_currentBalance);
            ptp = itemView.findViewById(R.id.to_do_ptp);
            nopol = itemView.findViewById(R.id.to_do_nopol);
            bucket = itemView.findViewById(R.id.to_do_bucket);
            toDoCv = itemView.findViewById(R.id.to_do_card_view);
        }

        public void bind(TodoItem todoItem) {
            hob.setText(todoItem.getHobAction());
            penanganan.setText(todoItem.getPaid());
            noKontrak.setText(todoItem.getContractNo());
            namaCust.setText(todoItem.getCustomerName());
            alamat.setText(todoItem.getAlamat());
            balance.setText(todoItem.getBalance());
            currBalance.setText(todoItem.getBalance());
            ptp.setText(todoItem.getBalance());
            nopol.setText(todoItem.getPlat());
            bucket.setText(todoItem.getPaid());
        }
    }
}
