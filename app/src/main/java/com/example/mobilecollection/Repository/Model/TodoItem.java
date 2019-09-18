package com.example.mobilecollection.Repository.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class TodoItem {

    @PrimaryKey
    @SerializedName("id")
    @NonNull
    private int id;

    @SerializedName("contractNo")
    private String contractNo;

    @SerializedName("namaCustomer")
    private String customerName;

    @SerializedName("orderDate")
    private String orderDate;

    @SerializedName("plat")
    private String plat;

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
    private int prioritas;

    @SerializedName("tglJatuhTempo")
    private String tglJatuhTempo;

    @SerializedName("currentBalance")
    private String currentBalance;

    @SerializedName("ptp")
    private String ptp;

    @SerializedName("ptpDate")
    private String ptpDate;

    @SerializedName("ptpAmount")
    private int ptpAmount;

    @SerializedName("remark")
    private String remark;

    @SerializedName("alamatPerubahan")
    private String alamatPerubahan;

    @SerializedName("telpPerubahan")
    private String telpPerubahan;

    @SerializedName("mobilePerubahan")
    private String mobilePerubahan;

    @SerializedName("bertemuDengan")
    private int bertemuDengan;

    @SerializedName("statusAlamat")
    private int statusAlamat;

    @SerializedName("statusTelp")
    private int statusTelp;

    @SerializedName("statusHp")
    private int statusHp;

    @SerializedName("followupType")
    private int followupType;

    @SerializedName("visitResult")
    private int visitResult;

    @SerializedName("kronologis")
    private String kronologis;

    @SerializedName("foto1")
    private String foto1;

    @SerializedName("foto2")
    private String foto2;

    @SerializedName("todoStatus")
    private String todoStatus;

    public String getTodoStatus() {
        return todoStatus;
    }

    public TodoItem(int id, String contractNo, String customerName, String orderDate, String plat, String todoStatus) {
        this.id = id;
        this.contractNo = contractNo;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.plat = plat;
        this.todoStatus = todoStatus;
    }

    public void setTodoStatus(String todoStatus) {
        this.todoStatus = todoStatus;
    }

    public String getCurrentBalance() {
        return currentBalance;
    }

    public String getTglJatuhTempo() {
        return tglJatuhTempo;
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

    public int getPrioritas() { return prioritas; }

    public String getPtp() {
        return ptp;
    }

    public String getPtpDate() {
        return ptpDate;
    }

    public int getPtpAmount() {
        return ptpAmount;
    }

    public String getRemark() {
        return remark;
    }

    public String getAlamatPerubahan() {
        return alamatPerubahan;
    }

    public String getTelpPerubahan() {
        return telpPerubahan;
    }

    public String getMobilePerubahan() {
        return mobilePerubahan;
    }

    public int getBertemuDengan() {
        return bertemuDengan;
    }

    public int getStatusAlamat() {
        return statusAlamat;
    }

    public int getStatusTelp() {
        return statusTelp;
    }

    public int getStatusHp() {
        return statusHp;
    }

    public int getFollowupType() {
        return followupType;
    }

    public int getVisitResult() {
        return visitResult;
    }

    public String getKronologis() {
        return kronologis;
    }

    public String getFoto1() {
        return foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public void setHobAction(String hobAction) {
        this.hobAction = hobAction;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }

    public void setReceivable(String receivable) {
        this.receivable = receivable;
    }

    public void setTotalPeriod(String totalPeriod) {
        this.totalPeriod = totalPeriod;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCurrentUnit(String currentUnit) {
        this.currentUnit = currentUnit;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAngsuran(String angsuran) {
        this.angsuran = angsuran;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPrioritas(int prioritas) {
        this.prioritas = prioritas;
    }

    public void setTglJatuhTempo(String tglJatuhTempo) {
        this.tglJatuhTempo = tglJatuhTempo;
    }

    public void setCurrentBalance(String currentBalance) {
        this.currentBalance = currentBalance;
    }

    public void setPtp(String ptp) {
        this.ptp = ptp;
    }

    public void setPtpDate(String ptpDate) {
        this.ptpDate = ptpDate;
    }

    public void setPtpAmount(int ptpAmount) {
        this.ptpAmount = ptpAmount;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setAlamatPerubahan(String alamatPerubahan) {
        this.alamatPerubahan = alamatPerubahan;
    }

    public void setTelpPerubahan(String telpPerubahan) {
        this.telpPerubahan = telpPerubahan;
    }

    public void setMobilePerubahan(String mobilePerubahan) {
        this.mobilePerubahan = mobilePerubahan;
    }

    public void setBertemuDengan(int bertemuDengan) {
        this.bertemuDengan = bertemuDengan;
    }

    public void setStatusAlamat(int statusAlamat) {
        this.statusAlamat = statusAlamat;
    }

    public void setStatusTelp(int statusTelp) {
        this.statusTelp = statusTelp;
    }

    public void setStatusHp(int statusHp) {
        this.statusHp = statusHp;
    }

    public void setFollowupType(int followupType) {
        this.followupType = followupType;
    }

    public void setVisitResult(int visitResult) {
        this.visitResult = visitResult;
    }

    public void setKronologis(String kronologis) {
        this.kronologis = kronologis;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }
}


