package com.example.viktoriasemina.liquorshop.ViewsClient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.Common.Common;
import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Customer;
import com.example.viktoriasemina.liquorshop.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    EditText firstname, lastname, mail, password, phone, birthdate;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        db = new DBHelper(this);

        firstname = findViewById(R.id.nameCreate);
        lastname = findViewById(R.id.lastnameCreate);
        mail = findViewById(R.id.emailCreate);
        password = findViewById(R.id.passwordCreate);
        phone = findViewById(R.id.phoneCreate);
        birthdate = findViewById(R.id.birthdateCreate);
    }

    public void onClickRegisterNewUser(View view) {

        String first_name = firstname.getText().toString();
        String last_name = lastname.getText().toString();
        String email = mail.getText().toString();
        String pass = password.getText().toString();
        String phone_number = phone.getText().toString();
        String bday = birthdate.getText().toString();

        String hashpass = null;

        try {
            hashpass = hashPassword(pass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Customer customer = new Customer(email, hashpass, first_name, last_name, phone_number, 0, bday);



        db.open();

        boolean status = db.addCustomer(customer);

        db.close();

        if (status==true) {
            Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
            Common.currentCustomer = customer;
        } else {
            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(getApplicationContext(), BaseDrawerActivity.class);
        startActivity(intent);
    }

    //hashing password

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes(), 0, password.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }

}
