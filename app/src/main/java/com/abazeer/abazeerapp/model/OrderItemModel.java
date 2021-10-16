package com.abazeer.abazeerapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItemModel {



    @Expose
    @SerializedName("unit_name")
    private String unit_name;
    @Expose
    @SerializedName("unit_id")
    private int unit_id;
    @Expose
    @SerializedName("group_name")
    private String group_name;
    @Expose
    @SerializedName("group_id")
    private int group_id;
    @Expose
    @SerializedName("m_name")
    private String m_name;
    @Expose
    @SerializedName("m_id")
    private int m_id;
    @Expose
    @SerializedName("l_name")
    private String l_name;
    @Expose
    @SerializedName("l_id")
    private int l_id;
    @Expose
    @SerializedName("p_name")
    private String p_name;
    @Expose
    @SerializedName("p_id")
    private int p_id;
    @Expose
    @SerializedName("reference")
    private String reference;
    @Expose
    @SerializedName("qty_done")
    private int qty_done;
    @Expose
    @SerializedName("qty_return")
    private int qty_return;
    @Expose
    @SerializedName("id")
    private int id;

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getQty_return() {
        return qty_return;
    }

    public void setQty_return(int qty_return) {
        this.qty_return = qty_return;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public int getL_id() {
        return l_id;
    }

    public void setL_id(int l_id) {
        this.l_id = l_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getQty_done() {
        return qty_done;
    }

    public void setQty_done(int qty_done) {
        this.qty_done = qty_done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }
}
