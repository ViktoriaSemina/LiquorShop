package com.example.viktoriasemina.liquorshop.Database.Model;

public class Cart {

    public static final String TABLE_CART = "cart";

    public static final String COLUMN_ID = "cart_id";
    public static final String COLUMN_PRODUCT = "product_id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRODUCT_PRICE = "product_price";
    public static final String COLUMN_PRODUCT_IMG = "product_img";

    private int id;
    private int product_id, product_quantity;
    private double product_price;
    private String product_name, product_img;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_CART + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PRODUCT + " INTEGER," + COLUMN_QUANTITY + " INTEGER, " + COLUMN_PRODUCT_NAME
                    + " TEXT, " + COLUMN_PRODUCT_PRICE + " FLOAT, " + COLUMN_PRODUCT_IMG +
                    " TEXT, "
                    + "FOREIGN KEY ("+COLUMN_PRODUCT+") REFERENCES "+Product.TABLE_PRODUCT+
                    "(ID) ON UPDATE CASCADE" + ")";

    public Cart() {
    }

    public Cart(int id, int product_id, int product_quantity, double product_price, String product_name, String product_img) {
        this.id = id;
        this.product_id = product_id;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.product_name = product_name;
        this.product_img = product_img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }
}
