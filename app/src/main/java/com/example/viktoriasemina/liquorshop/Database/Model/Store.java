package com.example.viktoriasemina.liquorshop.Database.Model;

public class Store {
    public static final String TABLE_STORE = "store";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOCATION = "location";

    private int id;
    private String location;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_STORE + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LOCATION + " TEXT" + ")";

    public Store() {
    }

    public Store(int id, String location) {
        this.id = id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
