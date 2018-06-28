package com.example.viktoriasemina.liquorshop.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.widget.Toast;

import com.example.viktoriasemina.liquorshop.Database.Model.Brand;
import com.example.viktoriasemina.liquorshop.Database.Model.Cart;
import com.example.viktoriasemina.liquorshop.Database.Model.Category;
import com.example.viktoriasemina.liquorshop.Database.Model.Customer;
import com.example.viktoriasemina.liquorshop.Database.Model.Product;
import com.example.viktoriasemina.liquorshop.Database.Model.Staff;
import com.example.viktoriasemina.liquorshop.Database.Model.Store;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 19;
    private static String DATABASE_NAME ="shop.db";
    private Context context;
    private SQLiteDatabase db;


    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Category.CREATE_TABLE);
            Toast.makeText(context, "Category table created", Toast.LENGTH_LONG).show();
            db.execSQL(Brand.CREATE_TABLE);
            Toast.makeText(context, "Brand table created", Toast.LENGTH_LONG).show();
            db.execSQL(Product.CREATE_TABLE);
            Toast.makeText(context, "Product table created", Toast.LENGTH_LONG).show();
            db.execSQL(Customer.CREATE_TABLE);
            Toast.makeText(context, "Customer table created", Toast.LENGTH_LONG).show();
            db.execSQL(Store.CREATE_TABLE);
            Toast.makeText(context, "Store table created", Toast.LENGTH_LONG).show();
            db.execSQL(Staff.CREATE_TABLE);
            Toast.makeText(context, "Staff table created", Toast.LENGTH_LONG).show();
            db.execSQL(Cart.CREATE_TABLE);
            Toast.makeText(context, "Cart table created", Toast.LENGTH_LONG).show();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + Category.TABLE_CATEGORY);
        db.execSQL("drop table if exists " + Brand.TABLE_BRAND);
        db.execSQL("drop table if exists " + Product.TABLE_PRODUCT);
        db.execSQL("drop table if exists " + Customer.TABLE_CUSTOMER);
        db.execSQL("drop table if exists " + Store.TABLE_STORE);
        db.execSQL("drop table if exists " + Staff.TABLE_STAFF);
        db.execSQL("drop table if exists " + Cart.TABLE_CART);
        onCreate(db);
    }

    public void open(){
        db = this.getWritableDatabase();
    }

    public boolean addCustomer(Customer customer) {

        ContentValues cv = new ContentValues();
        cv.put(Customer.COLUMN_FIRSTNAME, customer.getFirst_name());
        cv.put(Customer.COLUMN_LASTNAME, customer.getLast_name());
        cv.put(Customer.COLUMN_MAIL, customer.getMail());
        cv.put(Customer.COLUMN_PASSWORD, customer.getPassword());
        cv.put(Customer.COLUMN_BIRTHDATE, customer.getBirth_date());
        cv.put(Customer.COLUMN_PHONE, customer.getPhone());

        // Inserting Row
        Long status = db.insert(Customer.TABLE_CUSTOMER, null, cv);

        if (status!= -1){
            return true;
        } else {
            return  false;
        }
    }

    public Cursor getCustomerById(int id){
        Cursor cursor = db.query(Customer.TABLE_CUSTOMER, null, Customer.COLUMN_ID + "=?", new String[] {id+""}, null,
                null, null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getAllCustomerData(){
        Cursor cursor;
        cursor = db.rawQuery("select * from " + Customer.TABLE_CUSTOMER, null);
        return cursor;
    }

    public List<Customer> getAllCustomers() {
        // array of columns to fetch
        String[] columns = {
                Customer.COLUMN_ID,
                Customer.COLUMN_FIRSTNAME,
                Customer.COLUMN_LASTNAME,
                Customer.COLUMN_MAIL,
                Customer.COLUMN_PASSWORD,
                Customer.COLUMN_BIRTHDATE,
                Customer.COLUMN_PHONE
        };

        // sorting orders
        String sortOrder =
                Customer.COLUMN_FIRSTNAME + " ASC";
        List<Customer> customerList = new ArrayList<Customer>();

        db = this.getReadableDatabase();

        // query the customer table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(Customer.TABLE_CUSTOMER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Customer.COLUMN_ID))));
                customer.setFirst_name(cursor.getString(cursor.getColumnIndex(Customer.COLUMN_FIRSTNAME)));
                customer.setLast_name(cursor.getString(cursor.getColumnIndex(Customer.COLUMN_LASTNAME)));
                customer.setMail(cursor.getString(cursor.getColumnIndex(Customer.COLUMN_MAIL)));
                customer.setPassword(cursor.getString(cursor.getColumnIndex(Customer.COLUMN_PASSWORD)));
                customer.setBirth_date(cursor.getString(cursor.getColumnIndex(Customer.COLUMN_BIRTHDATE)));
                customer.setPhone(cursor.getString(cursor.getColumnIndex(Customer.COLUMN_PHONE)));
                // Adding user record to list
                customerList.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return customerList;
    }

    public Customer getCustomer(int id) {
        // get readable database as we are not inserting anything
        db = this.getReadableDatabase();

        Cursor cursor = db.query(Customer.TABLE_CUSTOMER, new String[]{Customer.COLUMN_ID, Customer.COLUMN_FIRSTNAME, Customer.COLUMN_LASTNAME,
                        Customer.COLUMN_MAIL, Customer.COLUMN_PASSWORD, Customer.COLUMN_PHONE, Customer.COLUMN_BIRTHDATE},
                Customer.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        // prepare an object
        Customer customer = new Customer(
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_MAIL)),
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_FIRSTNAME)),
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_LASTNAME)),
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_PHONE)),
                        cursor.getInt(cursor.getColumnIndex(Customer.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Customer.COLUMN_BIRTHDATE)));

        //close the db connection
        cursor.close();
        return customer;
    }

    public Customer getCustomerByEmail(String email) {
        // get readable database as we are not inserting anything
        db = this.getReadableDatabase();

        Cursor cursor = db.query(Customer.TABLE_CUSTOMER, new String[]{Customer.COLUMN_ID, Customer.COLUMN_FIRSTNAME, Customer.COLUMN_LASTNAME,
                        Customer.COLUMN_MAIL, Customer.COLUMN_PASSWORD, Customer.COLUMN_PHONE, Customer.COLUMN_BIRTHDATE},
                Customer.COLUMN_MAIL + "=?", new String[]{String.valueOf(email)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        // prepare an object
        Customer customer = new Customer(
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_MAIL)),
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_FIRSTNAME)),
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_LASTNAME)),
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_PHONE)),
                cursor.getInt(cursor.getColumnIndex(Customer.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Customer.COLUMN_BIRTHDATE)));

        //close the db connection
        cursor.close();
        return customer;
    }

    /**
     * Update Customer records
     * @param customer
     */

    public void updateCustomer(Customer customer){
        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Customer.COLUMN_FIRSTNAME, customer.getFirst_name());
        cv.put(Customer.COLUMN_LASTNAME, customer.getLast_name());
        cv.put(Customer.COLUMN_MAIL, customer.getMail());
        cv.put(Customer.COLUMN_PASSWORD, customer.getPassword());
        cv.put(Customer.COLUMN_BIRTHDATE, customer.getBirth_date());
        cv.put(Customer.COLUMN_PHONE, customer.getPhone());

        //update row
        db.update(Customer.TABLE_CUSTOMER, cv, Customer.COLUMN_ID + " = ?",
                new String[]{String.valueOf(customer.getId())});
        db.close();
    }

    /**
     * This method is to delete Customer records
     * @param customer
     */

    public void deleteCustomer(Customer customer){
        db = this.getWritableDatabase();
        //delete user record by id
        db.delete(Customer.TABLE_CUSTOMER, Customer.COLUMN_ID + " = ?",
                new String[]{String.valueOf(customer.getId())});
        db.close();
    }

    /**
     * This method is to check whether Customer exists in the database
     * @param mail, password
     * @return true/false
     */

    public boolean checkCustomer(String mail, String password){
        //array of columns to fetch
        String[] columns = {
                Customer.COLUMN_ID
        };
        db = this.getReadableDatabase();

        //selection criteria
        String selection = Customer.COLUMN_MAIL + " = ?" + " AND " + Customer.COLUMN_PASSWORD + " = ?";

        //selection argument
        String[] selectionArgs = {mail, password};

        //query Customer table with condition
        Cursor cursor = db.query(Customer.TABLE_CUSTOMER, //table to query
                columns, //columns to return
                selection, //columns for the where clause
                selectionArgs, //the values for the where clause
                null, //group the rows
                null, //filter by row groups
                null); //the sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * This method is to create a new category
     * @param category
     * @return true;
     */

    public boolean addCategory(Category category) {

        ContentValues cv = new ContentValues();
        cv.put(Category.COLUMN_NAME, category.getName());
        cv.put(Category.COLUMN_IMG, category.getImg());

        // Inserting Row
        Long status = db.insert(Category.TABLE_CATEGORY, null, cv);

        if (status!= -1){
            return true;
        } else {
            return  false;
        }
    }

    /**
     * This method is to fetch all categories and return arraylist
     * @return categoryList
     */

    public List<Category> getAllCategories() {
        // array of columns to fetch
        String[] columns = {
                Category.COLUMN_ID,
                Category.COLUMN_NAME,
                Category.COLUMN_IMG
        };

        // sorting orders
        String sortOrder =
                Category.COLUMN_NAME + " ASC";
        List<Category> categoryList = new ArrayList<Category>();

        db = this.getReadableDatabase();

        // query the category table

        Cursor cursor = db.query(Category.TABLE_CATEGORY, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Category.COLUMN_ID))));
                category.setName(cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME)));
                category.setImg(cursor.getString(cursor.getColumnIndex(Category.COLUMN_IMG)));

                // Adding user record to list
                categoryList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return categoryList;
    }

    /**
     * This method is to be used with Gridview or spinner to display all categories
     * @return cursor
     */
    public Cursor getAllCategoriesData(){
        Cursor cursor;
        cursor = db.rawQuery("select * from " + Category.TABLE_CATEGORY, null);
        return cursor;
    }

    public Cursor getCategoryById(int id){
        Cursor cursor = db.query(Category.TABLE_CATEGORY, null, Category.COLUMN_ID + "=?", new String[] {id+""}, null,
                null, null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getCategoryByName(String title){
        Cursor cursor = db.query(Category.TABLE_CATEGORY, null, Category.COLUMN_NAME + "=?", new String[] {title+""}, null,
                null, null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    // Getting single Category
    public Category getCategory(int id) {

        Cursor cursor = db.query(Category.TABLE_CATEGORY, null,Category.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Category category = new Category(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));

        return category;
    }

    public void deleteCategory(Category category){
        db = this.getWritableDatabase();
        //delete category record by id
        db.delete(Category.TABLE_CATEGORY, Category.COLUMN_ID + " = ?",
                new String[]{String.valueOf(category.getId())});
        db.close();
    }

    public boolean addBrand(Brand brand) {

        ContentValues cv = new ContentValues();
        cv.put(Brand.COLUMN_NAME, brand.getName());

        // Inserting Row
        Long status = db.insert(Brand.TABLE_BRAND, null, cv);

        if (status!= -1){
            return true;
        } else {
            return  false;
        }
    }

    public List<Brand> getAllBrands() {
        // array of columns to fetch
        String[] columns = {
                Brand.COLUMN_ID,
                Brand.COLUMN_NAME
        };

        List<Brand> brandList = new ArrayList<Brand>();

        db = this.getReadableDatabase();

        // query the category table

        Cursor cursor = db.query(Brand.TABLE_BRAND, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Brand b = new Brand();
                b.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Brand.COLUMN_ID))));
                b.setName(cursor.getString(cursor.getColumnIndex(Brand.COLUMN_NAME)));

                brandList.add(b);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return brandList;
    }

    public Cursor getAllBrandsData(){
        Cursor cursor;
        cursor = db.rawQuery("select * from " + Brand.TABLE_BRAND, null);
        return cursor;
    }

    public Cursor getBrandByName(String name){
        Cursor cursor = db.query(Brand.TABLE_BRAND, null,
                Brand.COLUMN_NAME + "=?", new String[] {name+""}, null,
                null, null);
        if(cursor!=null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean addProduct(Product product) {

        ContentValues cv = new ContentValues();
        cv.put(Product.COLUMN_NAME, product.getName());
        cv.put(Product.COLUMN_IMG, product.getImg());
        cv.put(Product.COLUMN_BRAND, product.getBrand_id());
        cv.put(Product.COLUMN_CATEGORY, product.getCategory_id());
        cv.put(Product.COLUMN_PRICE, product.getPrice());
        cv.put(Product.COLUMN_WEIGHT, product.getWeight());

        // Inserting Row
        Long status = db.insert(Product.TABLE_PRODUCT, null, cv);

        if (status!= -1){
            return true;
        } else {
            return  false;
        }
    }

    public List<Product> getAllProducts() {
        // array of columns to fetch
        String[] columns = {
                Product.COLUMN_ID,
                Product.COLUMN_NAME,
                Product.COLUMN_IMG,
                Product.COLUMN_PRICE,
                Product.COLUMN_WEIGHT,
                Product.COLUMN_BRAND,
                Product.COLUMN_CATEGORY
        };
        List<Product> productList = new ArrayList<Product>();

        db = this.getReadableDatabase();

        Cursor cursor = db.query(Product.TABLE_PRODUCT, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_ID))));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME)));
                product.setImg(cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMG)));
                product.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Product.COLUMN_PRICE))));
                product.setWeight(cursor.getString(cursor.getColumnIndex(Product.COLUMN_WEIGHT)));
                product.setBrand_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_BRAND))));
                product.setCategory_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_CATEGORY))));

                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return productList;
    }

    // Getting single Product
    public Product getProduct(int id) {

        db = this.getReadableDatabase();
        Cursor cursor = db.query(Product.TABLE_PRODUCT, null,Product.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Product product = new Product(
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_ID))),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_WEIGHT)),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMG)),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(Product.COLUMN_PRICE))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_CATEGORY))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_BRAND)))
                );

        db.close();

        return product;
    }

    public Product getProductByCategoryId(int id) {

        db = this.getReadableDatabase();
        Cursor cursor = db.query(Product.TABLE_PRODUCT, null,Product.COLUMN_CATEGORY + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Product product = new Product(
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_ID))),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_WEIGHT)),
                cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMG)),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(Product.COLUMN_PRICE))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_CATEGORY))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_BRAND)))
        );

        db.close();

        return product;
    }

    public List<Product> getAllProductsByCatId(int id) {
        // array of columns to fetch
        String[] columns = {
                Product.COLUMN_ID,
                Product.COLUMN_NAME,
                Product.COLUMN_IMG,
                Product.COLUMN_PRICE,
                Product.COLUMN_WEIGHT,
                Product.COLUMN_BRAND,
                Product.COLUMN_CATEGORY
        };
        List<Product> productList = new ArrayList<Product>();

        db = this.getReadableDatabase();

        Cursor cursor = db.query(Product.TABLE_PRODUCT, //Table to query
                columns,    //columns to return
                Product.COLUMN_CATEGORY + "=?",
                new String[]{String.valueOf(id)},
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_ID))));
                product.setName(cursor.getString(cursor.getColumnIndex(Product.COLUMN_NAME)));
                product.setImg(cursor.getString(cursor.getColumnIndex(Product.COLUMN_IMG)));
                product.setPrice(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Product.COLUMN_PRICE))));
                product.setWeight(cursor.getString(cursor.getColumnIndex(Product.COLUMN_WEIGHT)));
                product.setBrand_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_BRAND))));
                product.setCategory_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Product.COLUMN_CATEGORY))));

                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return productList;
    }

    public List<Cart> getCarts(){
        db = this.getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect ={Cart.COLUMN_ID, Cart.COLUMN_PRODUCT, Cart.COLUMN_QUANTITY};
        String sqlTable = Cart.TABLE_CART;

        qb.setTables(sqlTable);
        Cursor cursor = qb.query(db, sqlSelect, null, null, null, null, null);

        final List<Cart> carts = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                /*carts.add(new Cart(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_ID))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_QUANTITY)))));*/
            } while (cursor.moveToNext());

        }
        return carts;
    }

    public void addToCart(Cart cart){
        db = this.getReadableDatabase();
        String query = String.format("INSERT INTO " + Cart.TABLE_CART + "(" + Cart.COLUMN_ID + ", " + Cart.COLUMN_PRODUCT
        + ", " + Cart.COLUMN_QUANTITY + " VALUES('%s', '%s', '%s')",
                cart.getId(),
                cart.getProduct_id(),
                cart.getProduct_quantity());
        db.execSQL(query);

    }

    public void cleanCart(){
        db = this.getReadableDatabase();
        String query = String.format("DELETE FROM " + Cart.TABLE_CART);
        db.execSQL(query);
        db.close();

    }

    public boolean addProductToCart(Cart cart){
        ContentValues cv = new ContentValues();
        cv.put(Cart.COLUMN_PRODUCT, cart.getProduct_id());
        cv.put(Cart.COLUMN_QUANTITY, cart.getProduct_quantity());
        cv.put(Cart.COLUMN_PRODUCT_NAME, cart.getProduct_name());
        cv.put(Cart.COLUMN_PRODUCT_PRICE, cart.getProduct_price());
        cv.put(Cart.COLUMN_PRODUCT_IMG, cart.getProduct_img());

        Long status = db.insert(Cart.TABLE_CART, null, cv);
        if (status!= -1){
            return true;
        } else {
            return  false;
        }
    }

    /**
     * This method is to fetch all carts and return arraylist
     * @return cartList
     */

    public List<Cart> getAllCarts() {
        // array of columns to fetch
        String[] columns = {
                Cart.COLUMN_ID,
                Cart.COLUMN_PRODUCT,
                Cart.COLUMN_QUANTITY,
                Cart.COLUMN_PRODUCT_PRICE,
                Cart.COLUMN_PRODUCT_NAME,
                Cart.COLUMN_PRODUCT_IMG
        };


        List<Cart> cartList = new ArrayList<Cart>();

        db = this.getReadableDatabase();

        /*final String MY_QUERY = "SELECT r.name, r.site_name, r.age, r.gender, rt.rtype_name, " +
                "r.parent_code, r.code, r.site_code, r.interview_date FROM respondentTable r," +
                "respondentType rt WHERE r.type_code = rt.rtype_code and r.site_code='" +
                  "'";

        String select = " SELECT Cart." + Cart.COLUMN_ID + ", Cart." + Cart.COLUMN_QUANTITY +
                ", Product." + Product.COLUMN_PRICE + ", Product." + Product.COLUMN_NAME + " FROM " + Cart.TABLE_CART + " INNER JOIN " +
                Product.TABLE_PRODUCT + " Cart ON Cart." + Cart.COLUMN_PRODUCT + "= Product." + Product.COLUMN_ID;*/

        // query the category table

        Cursor cursor = db.query(Cart.TABLE_CART, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Cart cart = new Cart();
                cart.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_ID))));
                cart.setProduct_id(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT))));
                cart.setProduct_quantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_QUANTITY))));
                cart.setProduct_name(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_NAME)));
                cart.setProduct_price(Double.parseDouble(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_PRICE))));
                cart.setProduct_img(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_IMG)));

                cartList.add(cart);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return cartList;
    }

    public int checkProductInCart(int prod_id){
        //array of columns to fetch
        String[] columns = {
                Cart.COLUMN_PRODUCT
        };
        db = this.getReadableDatabase();

        //selection criteria
        String selection = Cart.COLUMN_PRODUCT + " = ?";

        //selection argument
        String[] selectionArgs = {Integer.toString(prod_id)};

        //query Customer table with condition
        Cursor cursor = db.query(Cart.TABLE_CART, //table to query
                columns, //columns to return
                selection, //columns for the where clause
                selectionArgs, //the values for the where clause
                null, //group the rows
                null, //filter by row groups
                null); //the sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        /*if(cursorCount == 0) {
            return 0;
        }*/
        return cursorCount;
    }

    public void updateCart(Cart cart){
        db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Cart.COLUMN_PRODUCT, cart.getProduct_id());
        cv.put(Cart.COLUMN_QUANTITY, cart.getProduct_quantity());
        cv.put(Cart.COLUMN_PRODUCT_NAME, cart.getProduct_name());
        cv.put(Cart.COLUMN_PRODUCT_PRICE, cart.getProduct_price());
        cv.put(Cart.COLUMN_PRODUCT_IMG, cart.getProduct_img());

        //update row
        db.update(Cart.TABLE_CART, cv, Cart.COLUMN_ID + " = ?",
                new String[]{String.valueOf(cart.getId())});
        db.close();
    }

    public Cart getCart(int id) {

        // get readable database as we are not inserting anything
        db = this.getReadableDatabase();

        Cursor cursor = db.query(Cart.TABLE_CART, new String[]{Cart.COLUMN_ID, Cart.COLUMN_PRODUCT,
                        Cart.COLUMN_QUANTITY,
                        Cart.COLUMN_PRODUCT_PRICE,
                        Cart.COLUMN_PRODUCT_NAME,
                        Cart.COLUMN_PRODUCT_IMG},
                Cart.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();

        Cart cart = new Cart(
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_ID))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT))),
                Integer.parseInt(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_QUANTITY))),
                Double.parseDouble(cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_PRICE))),
                cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_NAME)),
                cursor.getString(cursor.getColumnIndex(Cart.COLUMN_PRODUCT_IMG)));

        //close the db connection
        cursor.close();
        return cart;
    }

    public void deleteCart(Cart cart){
        db = this.getWritableDatabase();
        db.delete(Cart.TABLE_CART, Cart.COLUMN_ID + " = ?",
                new String[]{String.valueOf(cart.getId())});
        db.close();
    }
}