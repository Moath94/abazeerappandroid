package com.abazeer.abazeerapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemModel {


    @Expose
    @SerializedName("location")
    private String location;
    @Expose
    @SerializedName("delete")
    private int delete;
    @Expose
    @SerializedName("updated_at")
    private String updated_at;
    @Expose
    @SerializedName("created_at")
    private String created_at;
    @Expose
    @SerializedName("expirydate")
    private String expirydate;
    @Expose
    @SerializedName("quantitycheck")
    private double quantitycheck;
    @Expose
    @SerializedName("quantity")
    private double quantity;
    @Expose
    @SerializedName("product_name")
    private String product_name;
    @Expose
    @SerializedName("note")
    private String note;
    @Expose
    @SerializedName("product_barcode")
    private String product_barcode;
    @Expose
    @SerializedName("createby")
    private String createby;
    @Expose
    @SerializedName("createby_id")
    private int createby_id;
    @Expose
    @SerializedName("delete_id")
    private int delete_id;
    @Expose
    @SerializedName("delete_by")
    private String delete_by;
    @Expose
    @SerializedName("checkby")
    private String checkby;
    @Expose
    @SerializedName("checkby_id")
    private int checkby_id;
    @SerializedName("description")
    private String description;
    @Expose
    @SerializedName("id")
    private int id;

    @Expose
    @SerializedName("lot_id")
    private int lot_id;
    @Expose
    @SerializedName("lot_name")
    private String lot_name;


    public int getDelete_id() {
        return delete_id;
    }

    public void setDelete_id(int delete_id) {
        this.delete_id = delete_id;
    }

    public String getDelete_by() {
        return delete_by;
    }

    public void setDelete_by(String delete_by) {
        this.delete_by = delete_by;
    }

    public int getLot_id() {
        return lot_id;
    }

    public void setLot_id(int lot_id) {
        this.lot_id = lot_id;
    }

    public String getLot_name() {
        return lot_name;
    }

    public void setLot_name(String lot_name) {
        this.lot_name = lot_name;
    }

    public int getCreateby_id() {
        return createby_id;
    }

    public void setCreateby_id(int createby_id) {
        this.createby_id = createby_id;
    }

    public int getCheckby_id() {
        return checkby_id;
    }

    public void setCheckby_id(int checkby_id) {
        this.checkby_id = checkby_id;
    }

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCheckby() {
        return checkby;
    }

    public void setCheckby(String checkby) {
        this.checkby = checkby;
    }

    public String getProduct_barcode() {
        return product_barcode;
    }

    public void setProduct_barcode(String product_barcode) {
        this.product_barcode = product_barcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public double getQuantitycheck() {
        return quantitycheck;
    }

    public void setQuantitycheck(double quantitycheck) {
        this.quantitycheck = quantitycheck;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCreateby() {
        return createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
