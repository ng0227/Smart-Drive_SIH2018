package com.techhive.smartdrive.Utilities;

/**
 * Created by Sunain Mittal on 1/1/2018.
 */
public class User {

    private String fullName;
    private String photo;
    private String phonenumber;
    private String email;
    private String password;
    public User() {
    }
    public User(String mFullName, String mPhoto, String mEmail, String phonenumber,String password) {
        this.fullName = mFullName;
        this.photo = mPhoto;
        this.email = mEmail;
        this.phonenumber=phonenumber;
        this.password=password;
    }


    public String getFullName() {
        return fullName;
    }

    public String getPassword(){return password;}
    public String getPhoto() {
        return photo;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }


}