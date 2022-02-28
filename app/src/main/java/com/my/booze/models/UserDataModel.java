package com.my.booze.models;

import com.google.gson.annotations.SerializedName;

public class UserDataModel {

    @SerializedName("user_Id")
    private int user_Id;
    @SerializedName("user_Name")
    private String user_Name;
    @SerializedName("user_Email")
    private String user_Email;
    @SerializedName("user_Contact")
    private String user_Contact;
    @SerializedName("userPassword")
    private String userPassword;
    @SerializedName("user_Status")
    private String user_Status;
    @SerializedName("user_RecentAddress")
    private String user_RecentAddress;
    @SerializedName("user_Latitude")
    private String user_Latitude;
    @SerializedName("user_Longitude")
    private String user_Longitude;
    @SerializedName("user_VerificationCode")
    private String user_VerificationCode;

    public UserDataModel(int user_Id, String user_Name, String user_Email,
                         String user_Contact, String userPassword, String user_Status, String user_RecentAddress, String user_VerificationCode) {
        this.user_Id = user_Id;
        this.user_Name = user_Name;
        this.user_Email = user_Email;
        this.user_Contact = user_Contact;
        this.userPassword = userPassword;
        this.user_Status = user_Status;
        this.user_RecentAddress = user_RecentAddress;
        this.user_VerificationCode = user_VerificationCode;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_Email() {
        return user_Email;
    }

    public void setUser_Email(String user_Email) {
        this.user_Email = user_Email;
    }

    public String getUser_Contact() {
        return user_Contact;
    }

    public void setUser_Contact(String user_Contact) {
        this.user_Contact = user_Contact;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUser_Status() {
        return user_Status;
    }

    public void setUser_Status(String user_Status) {
        this.user_Status = user_Status;
    }

    public String getUser_RecentAddress() {
        return user_RecentAddress;
    }

    public void setUser_RecentAddress(String user_RecentAddress) {
        this.user_RecentAddress = user_RecentAddress;
    }

    public String getUser_VerificationCode() {
        return user_VerificationCode;
    }

    public void setUser_VerificationCode(String user_VerificationCode) {
        this.user_VerificationCode = user_VerificationCode;
    }
}
