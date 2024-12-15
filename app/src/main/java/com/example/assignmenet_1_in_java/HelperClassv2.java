package com.example.assignmenet_1_in_java;


public class HelperClassv2 {
    String name, userId, username,age,phone,gender;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public HelperClassv2(String name, String userId, String username, String age, String phone, String gender) {
        this.name = name;
        this.username = username;
        this.age=age;
        this.phone=phone;
        this.gender=gender;
        this.userId=userId;
    }

    public HelperClassv2() {
    }
}