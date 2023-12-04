package com.example.doancs2nhom7.model;

public class ProfileModel {
    private String name;
    private String email;
    private String phone;
    private int bookmarksCount;

    public ProfileModel(String name, String email, String phone, int bookmarksCount) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.bookmarksCount = bookmarksCount;
    }

    public int getBookmarksCount() {
        return bookmarksCount;
    }

    public void setBookmarksCount(int bookmarksCount) {
        this.bookmarksCount = bookmarksCount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
