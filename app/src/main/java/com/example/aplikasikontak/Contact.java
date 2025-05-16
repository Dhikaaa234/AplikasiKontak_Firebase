package com.example.aplikasikontak;

public class Contact {
    private String id;
    private String name;
    private String phoneNumber;
    private int imageResId;

    // constructor kosong untuk Firebase
    public Contact() {}

    public Contact(String name, String phoneNumber, int imageResId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.imageResId = imageResId;
    }

    // Getter dan Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}