package com.example.viktoriasemina.liquorshop.ViewsClient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.R;

public class SignInActivity extends AppCompatActivity {

    Button btnSignUp, btnLogIn;
    TextView txtSlogan;

    //declare counter for Agechecker activity
    public int count=0;
    int tempInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        getSupportActionBar().hide();

        //get value how many times ageChecker activity was opened on this device, if it's the first time, we show it and write in SP
        //If it has been opened at least once, we show SignInActivity
        count = readSharedPreferenceInt("cntSP","cntKey");
        if(count==0){
            Intent intent = new Intent();
            intent.setClass(SignInActivity.this, AgeCheckerActivity.class);
            startActivity(intent);
            count++;
            writeSharedPreference(count,"cntSP","cntKey");
            Toast.makeText(getApplicationContext(), "value " + count, Toast.LENGTH_SHORT).show();
        }

        //initialize buttons and txtview
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
            startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //read shared preferences to get value of how many times was the first activity opened
    public int readSharedPreferenceInt(String spName,String key){
        SharedPreferences sharedPreferences = getSharedPreferences(spName, Context.MODE_PRIVATE);
        return tempInt = sharedPreferences.getInt(key, 0);
    }

    //write shared preferences in integer
    public void writeSharedPreference(int amount,String spName,String key){

        SharedPreferences sharedPreferences = getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(key, amount);
        editor.commit();
    }
}
