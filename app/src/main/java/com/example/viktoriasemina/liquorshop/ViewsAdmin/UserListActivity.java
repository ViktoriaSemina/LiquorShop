package com.example.viktoriasemina.liquorshop.ViewsAdmin;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.Adapters.UserRecyclerAdapter;
import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Customer;
import com.example.viktoriasemina.liquorshop.Listeners.RecyclerTouchListener;
import com.example.viktoriasemina.liquorshop.R;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private AppCompatActivity activity = UserListActivity.this;
    private AppCompatTextView tvName;
    private RecyclerView recyclerViewUsers;
    private List<Customer> listCustomers;
    private UserRecyclerAdapter userRecyclerAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews(){
        tvName = findViewById(R.id.textViewLoggedUserName);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
    }

    /**
     * This method is to initialize objects
     */
    private void initObjects(){
        listCustomers = new ArrayList<>();
        userRecyclerAdapter = new UserRecyclerAdapter(listCustomers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewUsers.setLayoutManager(mLayoutManager);
        recyclerViewUsers.setAdapter(userRecyclerAdapter);
        recyclerViewUsers.setItemAnimator(new DefaultItemAnimator());
        recyclerViewUsers.setHasFixedSize(true);
        dbHelper = new DBHelper(activity);

        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        tvName.setText(emailFromIntent);

        getDataFromDatabase();

        recyclerViewUsers.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerViewUsers, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    public void deleteUser(int position) {
        dbHelper.deleteCustomer(listCustomers.get(position));
        listCustomers.remove(position);
        userRecyclerAdapter.notifyItemRemoved(position);
    }

    /**
     * Updating customer in db and updating
     * item in the list by its position
     */

    private void updateCustomerName(String firstName, String email, int position) {
        Customer c = listCustomers.get(position);
        c.setFirst_name(firstName);
        c.setMail(email);

        dbHelper.updateCustomer(c);

        //refreshing the list
        listCustomers.set(position, c);
        userRecyclerAdapter.notifyItemChanged(position);
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showCustomerDialog(listCustomers.get(position), position);
                } else {
                    deleteUser(position);
                }
            }
        });
        builder.show();
    }

    private void showCustomerDialog(final Customer customer, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.customer_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(UserListActivity.this);
        alertDialogBuilderUserInput.setView(view);

        final EditText inputName = view.findViewById(R.id.user_first_name_edit);
        final EditText inputEmail = view.findViewById(R.id.user_email_edit);

        if (customer != null) {
            inputName.setText(customer.getFirst_name());
            inputEmail.setText(customer.getMail());
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
                if (TextUtils.isEmpty(inputName.getText().toString())){
                    Toast.makeText(UserListActivity.this, "Enter something!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                if (customer != null){
                    updateCustomerName(inputName.getText().toString(), inputEmail.getText().toString(), position);
                }
            }
        });
    }

    /**
     * This method is to fetch all customers records from SQLite
     */
    private void getDataFromDatabase(){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                listCustomers.clear();
                listCustomers.addAll(dbHelper.getAllCustomers());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                userRecyclerAdapter.notifyDataSetChanged();
            }
        }.execute();
    }


}
