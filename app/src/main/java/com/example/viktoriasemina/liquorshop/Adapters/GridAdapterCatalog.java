package com.example.viktoriasemina.liquorshop.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viktoriasemina.liquorshop.Database.Model.Product;
import com.example.viktoriasemina.liquorshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapterCatalog extends BaseAdapter {

    public static Activity catalogActivity;
    private List<Product> productsList;


    public GridAdapterCatalog(Activity catalogActivity, List<Product> productsList) {
        this.catalogActivity = catalogActivity;
        this.productsList = productsList;
    }
    @Override
    public int getCount() {
        return productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return productsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderCatalog productViewHolder;

        if (convertView == null) {

            productViewHolder = new GridAdapterCatalog.ViewHolderCatalog();
            LayoutInflater inflater = LayoutInflater.from(catalogActivity);
            convertView = inflater.inflate(R.layout.item_activity_catalog, null);

            productViewHolder.productImg = convertView.findViewById(R.id.imgViewProduct);
            productViewHolder.productName = convertView.findViewById(R.id.productTitle);
            productViewHolder.productPrice = convertView.findViewById(R.id.productPrice);

            convertView.setTag(productViewHolder);
        }else{
            productViewHolder = (GridAdapterCatalog.ViewHolderCatalog)convertView.getTag();
        }
        Picasso.with(catalogActivity)
                .load(productsList.get(position).getImg())
                .placeholder(R.drawable.ic_sync_problem_black_24dp)
                .into(productViewHolder.productImg);
        productViewHolder.productName.setText(productsList.get(position).getName());

        String priceNew = Double.toString(productsList.get(position).getPrice());
        productViewHolder.productPrice.setText("$" + priceNew);

        return convertView;
    }

    static class ViewHolderCatalog{
        ImageView productImg;
        TextView productName;
        TextView productPrice;
    }
}
