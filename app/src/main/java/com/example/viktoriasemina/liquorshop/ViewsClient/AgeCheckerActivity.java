package com.example.viktoriasemina.liquorshop.ViewsClient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.viktoriasemina.liquorshop.R;
import com.example.viktoriasemina.liquorshop.ViewsClient.SignInActivity;

public class AgeCheckerActivity extends AppCompatActivity {

    private static final String TAG = "android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_checker);

        getSupportActionBar().hide();
    }

    public void giveAccess(View view) {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(intent);
    }


    public void denyAccess(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        finish();
        System.exit(0);
    }
        /*AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        View mView = getLayoutInflater().inflate(R.layout.policy_dialog, null);
        Button btnOk = mView.findViewById(R.id.btnOk);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });*/
/*        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.policy_dialog);
        dialog.getWindow().setLayout(700, 700);
        dialog.show();*/

}
