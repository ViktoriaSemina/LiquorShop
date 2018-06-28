package com.example.viktoriasemina.liquorshop.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viktoriasemina.liquorshop.Database.Model.Cart;
import com.example.viktoriasemina.liquorshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.CartViewHolder> {
    private List<Cart> cartList;

    Context context;

    public CartRecyclerAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartRecyclerAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_recycler, parent, false);
        return new CartRecyclerAdapter.CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartRecyclerAdapter.CartViewHolder holder, int position) {

        Picasso.with(context)
                .load(cartList.get(position).getProduct_img())
                .placeholder(R.drawable.ic_sync_problem_black_24dp)
                .into(holder.productImg);
        holder.productName.setText(cartList.get(position).getProduct_name());
        holder.productQuantity.setText(Integer.toString(cartList.get(position).getProduct_quantity()));

        String priceProduct = Double.toString(cartList.get(position).getProduct_price());
        holder.productPrice.setText("$" + priceProduct);
    }

    @Override
    public int getItemCount() {
        Log.v(CartRecyclerAdapter.class.getSimpleName(), "" + cartList.size());
        return cartList.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView productImg;
        TextView productName;
        TextView productPrice;
        TextView productQuantity;

        public CartViewHolder(View view){
            super(view);

            productImg = view.findViewById(R.id.list_image);
            productName = view.findViewById(R.id.from_name);
            productPrice = view.findViewById(R.id.plist_price_text);
            productQuantity = view.findViewById(R.id.cart_product_quantity_tv);
        }

    }

}
