package com.abazeer.abazeerapp.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.List;

public  class ProductsModel {


    @Expose
    @SerializedName("lots")
    private List<Lots> lots;
    @Expose
    @SerializedName("barcode")
    private BigDecimal barcode;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("size")
    private String size;
    @Expose
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("lot")
    private int lot;





    public static class Lots {
        @Expose
        @SerializedName("product_id")
        private int product_id;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

        public Lots(String name) {
            this.name = name;
        }

        public Lots() {
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public List<Lots> getLots() {
        return lots;
    }

    public void setLots(List<Lots> lots) {
        this.lots = lots;
    }
    public BigDecimal getBarcode() {
        return barcode;
    }

    public void setBarcode(BigDecimal barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }
}
