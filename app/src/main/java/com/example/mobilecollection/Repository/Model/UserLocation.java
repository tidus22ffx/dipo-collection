package com.example.mobilecollection.Repository.Model;


import com.google.gson.annotations.SerializedName;

public class UserLocation {

    @SerializedName("id")
    int id;

    @SerializedName("longitude")
    double longitude;

    @SerializedName("latitude")
    double latitude;

}
