package com.example.viktoriasemina.liquorshop.Database.Model;

import java.util.Date;

public class PromoCode {
    private String promocode;
    private String discount;
    private String startDate;
    private String endDate;

    public PromoCode() {
    }

    public PromoCode(String promocode, String discount, String startDate, String endDate) {
        this.promocode = promocode;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
