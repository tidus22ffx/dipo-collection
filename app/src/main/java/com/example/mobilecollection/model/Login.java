package com.example.mobilecollection.model;

import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("password")
    private String password;

    @SerializedName("username")
    private String username;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;


    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public  String getAddress() {
        return this.address;
    }

}
