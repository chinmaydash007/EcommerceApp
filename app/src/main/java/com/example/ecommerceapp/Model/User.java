package com.example.ecommerceapp.Model;

public class User {
    private String name;
    private String address;
    private String pincode;
    private String phone_number;

    public User() {
    }

    public User(String name, String address, String pincode, String phone_number) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
