package com.example.viktoriasemina.liquorshop.Database.Model;

public class Staff extends User {
    public static final String TABLE_STAFF = "staff";

    public static final String COLUMN_ID = "staff_id";
    public static final String COLUMN_FIRSTNAME = "first_name";
    public static final String COLUMN_LASTNAME = "last_name";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_PHONE = "phone";
    private int id;
    private String position;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_STAFF + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FIRSTNAME + " TEXT, " + COLUMN_LASTNAME + " TEXT, " + COLUMN_MAIL +
                    " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_POSITION + " TEXT, " + COLUMN_PHONE +
                    " TEXT)";

    public Staff() {
    }

    public Staff(String mail, String password, String first_name, String last_name, String phone, int id, String position) {
        super(mail, password, first_name, last_name, phone);
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
