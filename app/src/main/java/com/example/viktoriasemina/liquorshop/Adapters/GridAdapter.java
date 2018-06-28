package com.example.viktoriasemina.liquorshop.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.viktoriasemina.liquorshop.Database.Model.Category;
import com.example.viktoriasemina.liquorshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    //ArrayList categories;
    public static Activity activity;

    //private LayoutInflater layoutInflater;
    private List<Category> categoriesList;
    //private Context context;

    public GridAdapter(List<Category> categoriesList, Activity activity) {
        this.categoriesList = categoriesList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return categoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /*public GridAdapter(Activity activity, ArrayList categories) {
        this.categories = categories;
        this.activity = activity;
    }*/

    /*@Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;

        if (convertView == null) {

            listViewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(activity);
            convertView = inflater.inflate(R.layout.item_fragment_home, null);

            listViewHolder.img = convertView.findViewById(R.id.imageView);
            listViewHolder.categoryName = convertView.findViewById(R.id.productName);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(activity)
                .load(categoriesList.get(position).getImg())
                .resize(80, 80)
                .placeholder(R.drawable.ic_sync_problem_black_24dp)
                .into(listViewHolder.img);
        listViewHolder.categoryName.setText(categoriesList.get(position).getName());


        /*ImageView imageView = convertView.findViewById(R.id.imageView);

        TextView textView = convertView.findViewById(R.id.productName);*/



        //choose category and move to product page
        /*switch (categories.get(i).toString()) {
            case "Champagne":
                Picasso.get().load("https://goo.gl/jhMEQP").into(imageView);
                //imageView.setImageResource(R.drawable.champagne2);
                textView.setText("Champagne");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, CatActivity.class);
                        activity.startActivity(intent);
                    }
                });
            break;
            case "Beer":
                imageView.setImageResource(R.drawable.beer);
                textView.setText("Beer");
                break;
            case "Whiskey":
                imageView.setImageResource(R.drawable.whiskey);
                textView.setText("Whiskey");
                break;
            case "Cider":
                imageView.setImageResource(R.drawable.cider);
                textView.setText("Cider");
                break;
            case "Wine":
                imageView.setImageResource(R.drawable.wine);
                textView.setText("Wine");
                break;
            case "Cocktails":
                imageView.setImageResource(R.drawable.cocktails);
                textView.setText("Cocktails");
                break;

            default:
                break;
        }*/
        return convertView;
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder listViewHolder;

        if(convertView == null){
            listViewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_fragment_home, parent);

            //convertView = layoutInflater.inflate(R.layout.item_fragment_home, parent);
            listViewHolder.img = convertView.findViewById(R.id.imageView);
            listViewHolder.categoryName = convertView.findViewById(R.id.productName);

            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        Picasso.get().load(categoriesList.get(position).getImg()).into(listViewHolder.img);
        listViewHolder.categoryName.setText(categoriesList.get(position).getName());

        return convertView;
    }*/

    static class ViewHolder{
        ImageView img;
        TextView categoryName;
    }
}
