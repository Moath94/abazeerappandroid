package com.abazeer.abazeerapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnItemModel {


    @Expose
    @SerializedName("lot_name")
    private String lot_name;
    @Expose
    @SerializedName("lot_id")
    private int lot_id;
    @Expose
    @SerializedName("product_name")
    private String product_name;
    @Expose
    @SerializedName("product_id")
    private int product_id;
    @Expose
    @SerializedName("x_studio_abz_total_qty_driver")
    private int x_studio_abz_total_qty_driver;
    @Expose
    @SerializedName("x_studio_abz_product_exp_driver")
    private int x_studio_abz_product_exp_driver;
    @Expose
    @SerializedName("x_studio_abz_product_damaged_driver")
    private int x_studio_abz_product_damaged_driver;
    @Expose
    @SerializedName("x_studio_abz_product_valid_driver")
    private int x_studio_abz_product_valid_driver;
    @Expose
    @SerializedName("id")
    private int id;

    public String getLot_name() {
        return lot_name;
    }

    public void setLot_name(String lot_name) {
        this.lot_name = lot_name;
    }

    public int getLot_id() {
        return lot_id;
    }

    public void setLot_id(int lot_id) {
        this.lot_id = lot_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getX_studio_abz_total_qty_driver() {
        return x_studio_abz_total_qty_driver;
    }

    public void setX_studio_abz_total_qty_driver(int x_studio_abz_total_qty_driver) {
        this.x_studio_abz_total_qty_driver = x_studio_abz_total_qty_driver;
    }

    public int getX_studio_abz_product_exp_driver() {
        return x_studio_abz_product_exp_driver;
    }

    public void setX_studio_abz_product_exp_driver(int x_studio_abz_product_exp_driver) {
        this.x_studio_abz_product_exp_driver = x_studio_abz_product_exp_driver;
    }

    public int getX_studio_abz_product_damaged_driver() {
        return x_studio_abz_product_damaged_driver;
    }

    public void setX_studio_abz_product_damaged_driver(int x_studio_abz_product_damaged_driver) {
        this.x_studio_abz_product_damaged_driver = x_studio_abz_product_damaged_driver;
    }

    public int getX_studio_abz_product_valid_driver() {
        return x_studio_abz_product_valid_driver;
    }

    public void setX_studio_abz_product_valid_driver(int x_studio_abz_product_valid_driver) {
        this.x_studio_abz_product_valid_driver = x_studio_abz_product_valid_driver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
