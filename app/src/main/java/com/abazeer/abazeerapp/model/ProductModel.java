package com.abazeer.abazeerapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModel {


    @Expose
    @SerializedName("created_by")
    private String created_by;
    @Expose
    @SerializedName("product_create")
    private String product_create;
    @Expose
    @SerializedName("product_expiry")
    private String product_expiry;
    @Expose
    @SerializedName("product_quantity")
    private double product_quantity;
    @Expose
    @SerializedName("product_barcode")
    private String product_barcode;
    @Expose
    @SerializedName("product_name")
    private String product_name;
    @Expose
    @SerializedName("size")
    private String size;
    @Expose
    @SerializedName("delete")
    private int delete;
    @Expose
    @SerializedName("id")
    private int id;

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getProduct_create() {
        return product_create;
    }

    public void setProduct_create(String product_create) {
        this.product_create = product_create;
    }

    public String getProduct_expiry() {
        return product_expiry;
    }

    public void setProduct_expiry(String product_expiry) {
        this.product_expiry = product_expiry;
    }

    public double getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(double product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_barcode() {
        return product_barcode;
    }

    public void setProduct_barcode(String product_barcode) {
        this.product_barcode = product_barcode;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
