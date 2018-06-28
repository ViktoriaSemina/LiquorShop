package com.example.viktoriasemina.liquorshop.ViewsAdmin;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Product;
import com.example.viktoriasemina.liquorshop.R;
import com.example.viktoriasemina.liquorshop.ViewsClient.BaseDrawerActivity;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private Spinner spinnerBrand;
    private Spinner spinnerCategory;
    private ArrayAdapter<String> brandArrayAdapter;
    private ArrayAdapter<String> categoryArrayAdapter;
    private DBHelper dbHelper;
    private EditText mProductName, mProductWeight, mProductPrice, mProductImgUrl;
    private int brand_id;
    private int category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initViews();
        initObjects();
        addItemsOnSpinner();

        spinnerBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBrand = parent.getItemAtPosition(position).toString();

                dbHelper.open();
                Cursor cursor = dbHelper.getBrandByName(selectedBrand);
                if (cursor.getCount() > 0) {
                    brand_id = cursor.getInt(0);
                    /*Toast.makeText(parent.getContext(), "You selected: " + brand_id,
                            Toast.LENGTH_LONG).show();*/
                }
                dbHelper.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = spinnerCategory.getSelectedItem().toString();

                dbHelper.open();
                Cursor cursor_category_id = dbHelper.getCategoryByName(selectedCategory);
                if (cursor_category_id.getCount()>0) {
                    category_id = cursor_category_id.getInt(0);
                    /*Toast.makeText(parent.getContext(), "You selected: " + category_id,
                            Toast.LENGTH_LONG).show();*/
                }
                dbHelper.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initViews(){
        mProductName = findViewById(R.id.edTxtProductName);
        mProductPrice = findViewById(R.id.edTxtProductPrice);
        mProductWeight = findViewById(R.id.edTxtProductWeight);
        mProductImgUrl = findViewById(R.id.edTxtProductImageUrl);
        spinnerBrand = findViewById(R.id.spinner_brand);
        spinnerCategory = findViewById(R.id.spinner_category);
    }

    public void initObjects(){
        dbHelper = new DBHelper(this);
    }

    public void addItemsOnSpinner() {
        //spinner 1
        ArrayList<String> brandList = new ArrayList<String>();

        brandArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brandList);

        brandArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        dbHelper.open();

        Cursor brands_cursor = dbHelper.getAllBrandsData();
        while (brands_cursor.moveToNext()){
            String brand_name = brands_cursor.getString(1);
            brandList.add(brand_name);
        }

        spinnerBrand.setAdapter(brandArrayAdapter);

        dbHelper.close();

        //spinner 2
        ArrayList<String> categoriesList = new ArrayList<String>();

        categoryArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesList);

        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dbHelper.open();

        //SQL get DATA
        Cursor cursor = dbHelper.getAllCategoriesData();
        while (cursor.moveToNext()){
            String title = cursor.getString(1);
            categoriesList.add(title);
        }

        spinnerCategory.setAdapter(categoryArrayAdapter);
        dbHelper.close();
    }

    public void onClickSaveProduct(View view) {
        String p_name = mProductName.getText().toString();
        double p_price = Double.parseDouble(mProductPrice.getText().toString());
        String p_weight = mProductWeight.getText().toString();
        String p_url = mProductImgUrl.getText().toString();

        Product product = new Product(0, p_name, p_weight, p_url, p_price, category_id, brand_id);

        dbHelper.open();

        boolean status = dbHelper.addProduct(product);

        dbHelper.close();
        if(status == true){
            Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickMoveToHomeScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), BaseDrawerActivity.class);
        startActivity(intent);
    }


}
