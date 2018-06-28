package com.example.viktoriasemina.liquorshop.Database.Model;

public class Product {
    public static final String TABLE_PRODUCT = "product";

    public static final String COLUMN_ID = "product_id";
    public static final String COLUMN_NAME = "product_name";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_BRAND = "brand";

    private int id;
    private String name, weight, img;
    private double price;
    private int category_id;
    private int brand_id;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_PRODUCT + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT," + COLUMN_IMG + " TEXT,"
                    + COLUMN_WEIGHT + " TEXT," + COLUMN_PRICE + " FLOAT, "+ COLUMN_CATEGORY + " INTEGER, " + COLUMN_BRAND +
                    " INTEGER, " + "FOREIGN KEY ("+COLUMN_CATEGORY+") REFERENCES "+Category.TABLE_CATEGORY+ "(ID) ON UPDATE CASCADE, " +
                    "FOREIGN KEY (" + COLUMN_BRAND + ") REFERENCES " + Brand.TABLE_BRAND + "(ID) ON UPDATE CASCADE" + ")";

    public Product() {
    }

    public Product(int id, String name, String weight, String img, double price, int category_id, int brand_id) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.img = img;
        this.price = price;
        this.category_id = category_id;
        this.brand_id = brand_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img_small) {
        this.img = img_small;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }
}
