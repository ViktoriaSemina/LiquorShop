package com.example.viktoriasemina.liquorshop.ViewsClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.Common.Common;
import com.example.viktoriasemina.liquorshop.ViewsAdmin.CategoryActivity;
import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.InputValidation;
import com.example.viktoriasemina.liquorshop.R;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {

    Activity activity = LoginActivity.this;

    private TextInputLayout textInputLayoutMail, textInputLayoutPassword;
    private TextInputEditText mail, password;
    private CheckBox rem_userpass;
    private String hashpass;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    InputValidation inputValidation;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        initObjects();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //init views
        mail = findViewById(R.id.edtMail);
        password = findViewById(R.id.edtPassword);
        rem_userpass = findViewById(R.id.checkBox1);
        textInputLayoutMail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);

        mail.setText(sharedPreferences.getString(KEY_USERNAME,""));
        password.setText(sharedPreferences.getString(KEY_PASS,""));

        mail.addTextChangedListener(this);
        password.addTextChangedListener(this);
        rem_userpass.setOnCheckedChangeListener(this);

    }

    public void onClickSignIn(View view) {
        if(mail.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
            Toast.makeText(getApplicationContext(), "Logged in as staff", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
            startActivity(intent);
        }else{
            checkPassword();
            verifyFromSQLite();
            //Toast.makeText(getApplicationContext(), "Incorrect login or password", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    private void managePrefs(){
        if(rem_userpass.isChecked()){
            editor.putString(KEY_USERNAME, mail.getText().toString().trim());
            editor.putString(KEY_PASS, password.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }

    private void initObjects() {
        dbHelper = new DBHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */

    private void verifyFromSQLite() {
        if (!inputValidation.isEditTextFilled(mail, textInputLayoutMail)){
     return;}
        if (!inputValidation.isEditTextMail(mail, textInputLayoutMail)){
            return;
        }
        if (!inputValidation.isEditTextFilled(password, textInputLayoutPassword)){
            return;
        }
        if (dbHelper.checkCustomer(mail.getText().toString().trim(), hashpass)){
            Toast.makeText(getApplicationContext(), "Log in successfully", Toast.LENGTH_SHORT).show();
            Common.currentCustomer = dbHelper.getCustomerByEmail(mail.getText().toString().trim());
            Intent intent = new Intent(activity, BaseDrawerActivity.class);
            startActivity(intent);
            /*Intent accountsIntent = new Intent(activity, UserListActivity.class);
            accountsIntent.putExtra("EMAIL", mail.getText().toString().trim());
            startActivity(accountsIntent);*/
            //Common.currentCustomer = customer; //check in User tutorial

        } else {
            Toast.makeText(activity, "Wrong email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(password.getBytes(), 0, password.length());
        return new BigInteger(1, messageDigest.digest()).toString(16);
    }

    private String checkPassword(){
        hashpass = null;

        String pass = password.getText().toString().trim();

        try {
            hashpass = hashPassword(pass);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashpass;

    }



}
