package com.example.viktoriasemina.liquorshop.Database.Model;

public class Customer extends User {
    public static final String TABLE_CUSTOMER = "customer";

    public static final String COLUMN_ID = "customer_id";
    public static final String COLUMN_FIRSTNAME = "first_name";
    public static final String COLUMN_LASTNAME = "last_name";
    public static final String COLUMN_MAIL = "mail";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_BIRTHDATE = "birthdate";
    public static final String COLUMN_PHONE = "phone";

    private int id;
    private String birth_date;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_CUSTOMER + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FIRSTNAME + " TEXT, " + COLUMN_LASTNAME + " TEXT, " + COLUMN_MAIL +
                    " TEXT, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_BIRTHDATE + " TEXT, " + COLUMN_PHONE +
                    " TEXT)";

    public Customer() {
    }

    public Customer(String mail, String password, String first_name, String last_name, String phone, int id, String birth_date) {
        super(mail, password, first_name, last_name, phone);
        this.id = id;
        this.birth_date = birth_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }
}
