package com.example.viktoriasemina.liquorshop.ViewsClient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.Common.Common;
import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Customer;
import com.example.viktoriasemina.liquorshop.R;

public class ProfileActivity extends AppCompatActivity {

    DBHelper dbHelper;

    EditText mFirstName, mLastName, mPhone, mEmail, mPass, mBirthDate;
    Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DBHelper(this);

        mFirstName = findViewById(R.id.profile_edtName);
        mLastName = findViewById(R.id.profile_edtLastName);
        mPhone = findViewById(R.id.profile_edtPhone);
        mEmail = findViewById(R.id.profile_edtMail);
        mPass = findViewById(R.id.profile_edtPassword);
        mBirthDate = findViewById(R.id.profile_edtBirthDate);
        btnSave = findViewById(R.id.profile_btnSave);
        btnDelete = findViewById(R.id.profile_btnDelete);

        mFirstName.setText(Common.currentCustomer.getFirst_name());
        mLastName.setText(Common.currentCustomer.getLast_name());
        mPhone.setText(Common.currentCustomer.getPhone());
        mEmail.setText(Common.currentCustomer.getMail());
        mPass.setText(Common.currentCustomer.getPassword());
        mBirthDate.setText(Common.currentCustomer.getBirth_date());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCustomer(mFirstName.getText().toString(), mLastName.getText().toString(),
                        mPhone.getText().toString(), mEmail.getText().toString(),
                        mPass.getText().toString(), mBirthDate.getText().toString());
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(Common.currentCustomer);
            }
        });

    }

    private void updateCustomer(String firstName, String lastName, String email, String phone, String password, String birthDate) {
        Customer c = Common.currentCustomer;
        c.setFirst_name(firstName);
        c.setMail(email);
        c.setLast_name(lastName);
        c.setPhone(phone);
        c.setPassword(password);
        c.setBirth_date(birthDate);

        dbHelper.updateCustomer(c);
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
    }

    public void deleteUser(Customer customer) {
        dbHelper.deleteCustomer(customer);
        Intent i = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(i);
        finish();
    }
}
