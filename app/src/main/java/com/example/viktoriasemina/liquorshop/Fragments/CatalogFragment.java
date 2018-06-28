package com.example.viktoriasemina.liquorshop.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.viktoriasemina.liquorshop.Adapters.GridAdapterCatalog;
import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Product;
import com.example.viktoriasemina.liquorshop.ViewsClient.ProductPageActivity;
import com.example.viktoriasemina.liquorshop.R;

import java.util.ArrayList;
import java.util.List;

public class CatalogFragment extends Fragment {

    GridAdapterCatalog productAdapter;
    DBHelper dbHelper;
    private int category_id;
    List<Product> products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);

        dbHelper = new DBHelper(getActivity());

        final GridView grdvwCatalog = view.findViewById(R.id.grdvw_catalog);

        products = new ArrayList<>();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            category_id = bundle.getInt("Category_ID", -1);
            products = dbHelper.getAllProductsByCatId(category_id);
        }else{
            products = dbHelper.getAllProducts();
        }

        productAdapter = new GridAdapterCatalog(getActivity(), products);
        grdvwCatalog.setAdapter(productAdapter);
        grdvwCatalog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                int product_id = products.get(position).getId();
/*                Toast.makeText(getActivity(), "GridView item clicked : " + product_id +
                        "\nAt index position :" + position, Toast.LENGTH_LONG).show();*/

                Intent viewProductPageIntent = new Intent(getActivity(), ProductPageActivity.class);

                viewProductPageIntent.putExtra("ID", product_id);
                startActivity(viewProductPageIntent);
            }
        });
        return view;
    }

}
