package com.example.mobilecollection.Repository.Model;

import com.google.gson.annotations.SerializedName;

public class TodoItem {

    private TodoItem todoItem;

    @SerializedName("id")
    private int id;

    @SerializedName("contractNo")
    private String contractNo;

    @SerializedName("namaCustomer")
    private String customerName;

    @SerializedName("orderDate")
    private String orderDate;

    @SerializedName("plat")
    private String plat;

    @SerializedName("settings")
    private Settings settings;

    @SerializedName("hobAction")
    private String hobAction;

    @SerializedName("overdue")
    private int overdue;

    @SerializedName("receivable")
    private String receivable;

    @SerializedName("totalPeriod")
    private String totalPeriod;

    @SerializedName("paid")
    private String paid;

    @SerializedName("unit")
    private String unit;

    @SerializedName("brand")
    private String brand;

    @SerializedName("currentUnit")
    private String currentUnit;

    @SerializedName("expiredDate")
    private String expiredDate;

    @SerializedName("status")
    private String status;

    @SerializedName("angsuran")
    private String angsuran;

    @SerializedName("balance")
    private String balance;

    @SerializedName("bucket")
    private String bucket;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("telp")
    private String telp;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("prioritas")
    private String prioritas;

    public TodoItem(TodoItem todoItem) {
        this.todoItem = todoItem;
    }

    public int getId() {
        return id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getPlat() { return plat; }

    public Settings getSettings() { return settings; }

    public String getCustomerName() { return customerName; }

    public String getHobAction() { return hobAction; }

    public int getOverdue() { return overdue; }

    public String getReceivable() { return receivable; }

    public String getTotalPeriod() { return totalPeriod; }

    public String getPaid() { return paid; }

    public String getUnit() { return unit; }

    public String getBrand() { return brand; }

    public String getCurrentUnit() { return currentUnit; }

    public String getExpiredDate() { return expiredDate; }

    public String getStatus() { return status; }

    public String getAngsuran() { return angsuran; }

    public String getBalance() { return balance; }

    public String getBucket() { return bucket; }

    public String getAlamat() { return alamat; }

    public String getTelp() { return telp; }

    public String getMobile() { return mobile; }

    public String getPrioritas() { return prioritas; }

    public class Settings{
        private String color;
        private String size;
        private boolean shadow;
        private boolean finished;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public boolean isShadow() {
            return shadow;
        }

        public void setShadow(boolean shadow) {
            this.shadow = shadow;
        }

        public boolean isFinished() {
            return finished;
        }

        public void setFinished(boolean finished) {
            this.finished = finished;
        }
    }
}


