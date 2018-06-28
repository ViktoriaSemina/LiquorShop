package com.example.viktoriasemina.liquorshop.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.viktoriasemina.liquorshop.Adapters.GridAdapter;
import com.example.viktoriasemina.liquorshop.Fragments.CatalogFragment;
import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Category;
import com.example.viktoriasemina.liquorshop.R;

import java.util.List;


public class HomeFragment extends Fragment {

    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        dbHelper = new DBHelper(getActivity());

        final GridView gridview = view.findViewById(R.id.grdView);

        final List<Category> allCategories = dbHelper.getAllCategories();
        GridAdapter adapter = new GridAdapter(allCategories, getActivity());

        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {

                int category_id = allCategories.get(position).getId();

                Fragment fragment;
                fragment = new CatalogFragment();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flcontent, fragment).commit();

                Bundle bundle = new Bundle();
                bundle.putInt("Category_ID", category_id);
                fragment.setArguments(bundle);
            }
        });
        return view;
    }
}
