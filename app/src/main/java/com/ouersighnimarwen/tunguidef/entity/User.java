package com.ouersighnimarwen.tunguidef.entity;

public class User {
    private int id;
    private String uinque_id;
    private String name;
    private  String lastName;
    private String email;
    private int phoneNumber;
    private String password;
    private String imageUrl;

    public User(int id, String uinque_id, String name, String lastName, String email, int phoneNumber, String password, String imageUrl) {
        this.id = id;
        this.uinque_id = uinque_id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUinque_id() {
        return uinque_id;
    }

    public void setUinque_id(String uinque_id) {
        this.uinque_id = uinque_id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
