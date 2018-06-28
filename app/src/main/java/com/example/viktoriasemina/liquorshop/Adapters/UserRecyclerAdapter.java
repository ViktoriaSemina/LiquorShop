package com.example.viktoriasemina.liquorshop.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.viktoriasemina.liquorshop.Database.Model.Customer;
import com.example.viktoriasemina.liquorshop.R;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.UserViewHolder> {
    private List<Customer> listCustomers;

    public UserRecyclerAdapter(List<Customer> listCustomers) {
        this.listCustomers = listCustomers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_recycler, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.tvName.setText(listCustomers.get(position).getFirst_name());
        holder.tvEmail.setText(listCustomers.get(position).getMail());
        holder.tvPhone.setText(listCustomers.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        Log.v(UserRecyclerAdapter.class.getSimpleName(), "" + listCustomers.size());
        return listCustomers.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvName, tvEmail, tvPhone;

        public UserViewHolder(View view){
            super(view);
            tvName = view.findViewById(R.id.textViewFirstName);
            tvEmail = view.findViewById(R.id.textViewEmail);
            tvPhone = view.findViewById(R.id.textViewPhone);
        }
    }
}
