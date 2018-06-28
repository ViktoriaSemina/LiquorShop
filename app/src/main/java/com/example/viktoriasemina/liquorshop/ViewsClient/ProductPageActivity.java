package com.example.viktoriasemina.liquorshop.ViewsClient;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Cart;
import com.example.viktoriasemina.liquorshop.Database.Model.Product;
import com.example.viktoriasemina.liquorshop.R;
import com.squareup.picasso.Picasso;

public class ProductPageActivity extends AppCompatActivity {
    private ImageView mProductImg;
    private TextView mProductName, mProductPrice;
    DBHelper dbHelper;
    Product product_selected;
    private int product_id;
    double product_selected_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        initObjects();

        Intent receivedIntent = getIntent();

        //now get the productID we passed as an extra

        product_id = receivedIntent.getIntExtra("ID", -1);

        product_selected = dbHelper.getProduct(product_id);

        product_selected_price = product_selected.getPrice();

        Picasso.with(this)
                .load(product_selected.getImg())
                .placeholder(R.drawable.ic_sync_problem_black_24dp)
                .into(mProductImg);

        mProductName.setText(product_selected.getName());
        mProductPrice.setText(Double.toString(product_selected_price));

        final String stores[] = new String[]{"New market", "Ponsonby", "Albany"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a store where you would like to collect");
        builder.setItems(stores, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on stores[which]
            }
        });
        final AlertDialog a = builder.create();

        mProductImg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                a.show();
                return false;
            }
        });


    }

    public void initViews() {
        mProductImg = findViewById(R.id.img_pp_product);
        mProductName = findViewById(R.id.txt_pp_productName);
        mProductPrice = findViewById(R.id.txt_pp_productPrice);
    }

    public void initObjects() {
        dbHelper = new DBHelper(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClickAddToCart(View view) {

        Cart cart = new Cart(0, product_id, 1, product_selected_price, product_selected.getName(), product_selected.getImg());
        dbHelper.open();

        boolean status = dbHelper.addProductToCart(cart);

        dbHelper.close();

        if (status) {
            Toast.makeText(this, "Added to cart", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();


        /*int counter = dbHelper.checkProductInCart(product_id);

        if(counter == 0){
            cart = new Cart(0, product_id, 1, product_selected_price, product_selected.getName(), product_selected.getImg());
            dbHelper.open();

            boolean status = dbHelper.addProductToCart(cart, product_selected);

            dbHelper.close();

            if(status){
                Toast.makeText(this, "Added to cart", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            }
        } else {
            int quantityTemp = counter++;
        }*/
            //cart = new Cart(0, product_id, quantityTemp, product_selected_price, product_selected.getName(), product_selected.getImg());
            //cart.getId();
            //dbHelper.getCart();
            //dbHelper.updateCart(cart, product_selected);
            //Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        /*Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);*/
        }

    }
}
