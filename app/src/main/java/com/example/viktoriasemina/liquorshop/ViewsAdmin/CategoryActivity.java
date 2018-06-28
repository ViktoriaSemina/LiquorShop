package com.example.viktoriasemina.liquorshop.ViewsAdmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.Database.DBHelper;
import com.example.viktoriasemina.liquorshop.Database.Model.Category;
import com.example.viktoriasemina.liquorshop.R;
import com.example.viktoriasemina.liquorshop.ViewsClient.BaseDrawerActivity;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    EditText mCategoryName, mCategoryUrl;
    private DBHelper dbHelper;
    GridView mGridView;
    Button btnSelect;
    ArrayList<String> categoriesList;
    ArrayAdapter<String> catArrayAdapter;
    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initViews();
        initObjects();
        showAll(null);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                 showActionsDialog(position);
                                             }
                                         }
        );

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    public void removeCategory(int position) {
        dbHelper.deleteCategory(dbHelper.getAllCategories().get(position));
        showAll(null);
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    removeCategory(position);
                } else {
                    removeCategory(position);
                }
            }
        });
        builder.show();
    }

    public void initViews(){
        mCategoryName = findViewById(R.id.edTxtCategoryName);
        mCategoryUrl = findViewById(R.id.edTxtImageUrl);
        mGridView = findViewById(R.id.grdVwCategories);
        btnSelect = findViewById(R.id.btnSelect);
    }

    public void initObjects(){
        dbHelper = new DBHelper(this);
        categoriesList = new ArrayList<>();
        catArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoriesList);
    }

    public void onClickSaveCategory(View view) {
        String catName = mCategoryName.getText().toString();
        String catUrl = mCategoryUrl.getText().toString();

        Category category = new Category(0, catName, catUrl);

        dbHelper.open();

        boolean status = dbHelper.addCategory(category);

        dbHelper.close();
        if(status == true){
            Toast.makeText(CategoryActivity.this, "Category added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CategoryActivity.this, "Error", Toast.LENGTH_LONG).show();
        }
        emptyEditText();

        showAll(view);
    }

    private void emptyEditText(){
        mCategoryName.setText(null);
        mCategoryUrl.setText(null);
    }

    public void onClickMoveToHome(View view) {
        Intent intent =new Intent(getApplicationContext(), BaseDrawerActivity.class);
        startActivity(intent);
    }

    public void showAll(View view){

        //open connection with database
        catArrayAdapter.clear();
        dbHelper.open();

        //SQL get DATA
        Cursor cursor = dbHelper.getAllCategoriesData();
        while (cursor.moveToNext()){ ;
            String category_name = cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME));
            categoriesList.add(String.valueOf(category_name));
        }

        mGridView.setAdapter(catArrayAdapter);
        dbHelper.close();
    }

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null){
            saveUri = data.getData();
            btnSelect.setText("Image selected");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_category:
                Intent intent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_customers:
                Intent intent2 = new Intent(getApplicationContext(), UserListActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_brand:
                Intent intent3 = new Intent(getApplicationContext(), BrandActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_product:
                Intent intent5 = new Intent(getApplicationContext(), ProductActivity.class);
                startActivity(intent5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
