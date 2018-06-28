package com.example.viktoriasemina.liquorshop.ViewsClient;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.Adapters.CartRecyclerAdapter;
import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Cart;
import com.example.viktoriasemina.liquorshop.Database.Model.PromoCode;
import com.example.viktoriasemina.liquorshop.Listeners.RecyclerTouchListener;
import com.example.viktoriasemina.liquorshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    DBHelper dbHelper;

    Button btnPlace;
    TextView txtTotalPrice;

    private RecyclerView recyclerViewCarts;
    private CartRecyclerAdapter cartRecyclerAdapter;

    private List<Cart> cartLines;

    double finalSum;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference promoCode;

    EditText insertCode;
    Button btnApply;

    double discount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_new);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();
        initObjects();


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promoCode.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String promoApplied = insertCode.getText().toString();

                        //check if promocode exists

                        if (dataSnapshot.child(promoApplied).exists()) {
                            PromoCode promo = dataSnapshot.child(promoApplied).getValue(PromoCode.class);
                            //int discount = Integer.parseInt(promo.getDiscount());
                            discount = Double.parseDouble(promo.getDiscount());
                            //Toast.makeText(CartActivity.this, "Discount: " + discount, Toast.LENGTH_SHORT).show();
                            calculateTotalDiscounted(finalSum, discount);
                            Toast.makeText(CartActivity.this, "Promo code applied", Toast.LENGTH_SHORT).show();
                            /*if (promo.getDiscount().equals("20")) {
                                Toast.makeText(CartActivity.this, "Promo code applied", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CartActivity.this, "Promo code is not valid!", Toast.LENGTH_SHORT).show();
                            }*/
                        } else {
                            Toast.makeText(CartActivity.this, "Sorry! This promo code doesn't exist!", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        //local currency
        /*Locale locale = new Locale("en", "NZ");
        NumberFormat nmf = NumberFormat.getCurrencyInstance(locale);
        double totalAmount = productList.get(0).getPrice()*orderLines.get(0).getProduct_quantity();
        txtTotalPrice.setText(nmf.format(totalAmount));*/

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.cleanCart();
                cartLines.removeAll(cartLines);
                cartRecyclerAdapter.notifyDataSetChanged();
                txtTotalPrice.setText("0");
                Toast.makeText(CartActivity.this, "Your order has been sent", Toast.LENGTH_SHORT).show();
                //Order order = new Order(Common.currentCustomer.getFirst_name(), Common.currentCustomer.getPhone(), finalSum, productList);

            }
        });

        calculateTotal();
    }

    public void initViews(){
        btnPlace = findViewById(R.id.btnPlaceOrder);
        txtTotalPrice = findViewById(R.id.totalAmount);
        btnApply = findViewById(R.id.btnApply);
        insertCode = findViewById(R.id.edTxtInsertPromoCode);
        recyclerViewCarts = findViewById(R.id.cart_recycler_view);
    }

    public void initObjects(){

        cartLines = new ArrayList<>();
        cartRecyclerAdapter = new CartRecyclerAdapter(cartLines);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewCarts.setLayoutManager(mLayoutManager);
        recyclerViewCarts.setAdapter(cartRecyclerAdapter);
        recyclerViewCarts.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCarts.setHasFixedSize(true);

        dbHelper = new DBHelper(this);

        getDataFromDatabase();

        //FireBase
        firebaseDatabase = FirebaseDatabase.getInstance();
        promoCode = firebaseDatabase.getReference("PromoCode");

        recyclerViewCarts.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerViewCarts, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void calculateTotal(){
        finalSum = 0;

        for (Cart cart : cartLines)
        {
            finalSum += cart.getProduct_price()* cart.getProduct_quantity();
            txtTotalPrice.setText(Double.toString(finalSum));
        }
    }

    public void calculateTotalDiscounted(double total, double discPercent){

        double discountedSum = finalSum - (finalSum * discPercent / 100);
        txtTotalPrice.setText(Double.toString(discountedSum));

    }

    private void getDataFromDatabase(){
            new AsyncTask<Void, Void, Void>(){
                @Override
                protected Void doInBackground(Void... params) {
                    cartLines.clear();
                    cartLines.addAll(dbHelper.getAllCarts());
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    cartRecyclerAdapter.notifyDataSetChanged();
                }
            }.execute();
        }

    public void deleteProductFromCart(int position) {
        dbHelper.deleteCart(cartLines.get(position));
        cartLines.remove(position);
        cartRecyclerAdapter.notifyItemRemoved(position);
        calculateTotal();
    }

    private void updateQuantity(int quantity, int position) {
            Cart cart = cartLines.get(position);
            cart.setProduct_quantity(quantity);
        dbHelper.updateCart(cart);

        //refreshing the list
        cartLines.set(position, cart);
        cartRecyclerAdapter.notifyDataSetChanged();
        calculateTotal();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit quantity", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showCartDialog(cartLines.get(position), position);
                } else {
                    deleteProductFromCart(position);
                }
            }
        });
        builder.show();
    }

    private void showCartDialog(final Cart cart, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.cart_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(CartActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputQuantity = view.findViewById(R.id.cart_quantity_edit);

        if (cart != null) {
            inputQuantity.setText(Integer.toString(cart.getProduct_quantity()));
        }
        alertDialogBuilderUserInput.setCancelable(false)
                .setPositiveButton("update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogBox, int id) {
                        dialogBox.cancel();
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show toast message when no text is entered
                if (TextUtils.isEmpty(inputQuantity.getText().toString())){
                    Toast.makeText(CartActivity.this, "Enter something!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                // check if user updating note
                if (cart != null){
                    //update note by its id
                    updateQuantity(Integer.parseInt(inputQuantity.getText().toString()), position);
                }
            }
        });
    }

}

