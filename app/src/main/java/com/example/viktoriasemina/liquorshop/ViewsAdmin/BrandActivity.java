package com.example.viktoriasemina.liquorshop.ViewsAdmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Brand;
import com.example.viktoriasemina.liquorshop.R;

public class BrandActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private EditText mBrandName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        initViews();
        initObjects();
    }

    public void initViews(){
        mBrandName = findViewById(R.id.edTxtBrandName);
    }

    public void initObjects(){
        dbHelper = new DBHelper(this);
    }

    public void onClickSaveBrand(View view) {
        String brandName = mBrandName.getText().toString();

        Brand brand = new Brand(0, brandName);

        dbHelper.open();

        boolean status = dbHelper.addBrand(brand);

        dbHelper.close();
        if(status == true){
            Toast.makeText(getApplicationContext(), "Brand added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}
