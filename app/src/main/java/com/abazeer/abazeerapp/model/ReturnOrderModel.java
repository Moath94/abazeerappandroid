package com.abazeer.abazeerapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnOrderModel {

    @Expose
    @SerializedName("x_name")
    private String name;
    @Expose
    @SerializedName("x_studio_abz_status")
    private String statusOdoo;
    @Expose
    @SerializedName("warehouse_name")
    private String warehouse_name;
    @Expose
    @SerializedName("warehouse_id")
    private int warehouse_id;
    @Expose
    @SerializedName("partner_name")
    private String partner_name;
    @Expose
    @SerializedName("partner_id")
    private int partner_id;
    @Expose
    @SerializedName("x_studio_abz_rep_name")
    private String x_studio_abz_rep_name;
    @Expose
    @SerializedName("id")
    private int id;

    private int id_database;
    private int item_count;
    private int status;

    public String getStatusOdoo() {
        return statusOdoo;
    }

    public void setStatusOdoo(String statusOdoo) {
        this.statusOdoo = statusOdoo;
    }

    public int getId_database() {
        return id_database;
    }

    public void setId_database(int id_database) {
        this.id_database = id_database;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWarehouse_name() {
        return warehouse_name;
    }


    public void setWarehouse_name(String warehouse_name) {
        this.warehouse_name = warehouse_name;
    }

    public int getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public int getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(int partner_id) {
        this.partner_id = partner_id;
    }

    public String getX_studio_abz_rep_name() {
        return x_studio_abz_rep_name;
    }

    public void setX_studio_abz_rep_name(String x_studio_abz_rep_name) {
        this.x_studio_abz_rep_name = x_studio_abz_rep_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
