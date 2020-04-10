package com.edudb.bdude.db.modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("amount")
    @Expose
    int amount;
    @SerializedName("product")
    @Expose
    String product;

    public Product() {
    }

    public Product(String text, int count) {
        amount = count;
        product = text;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
