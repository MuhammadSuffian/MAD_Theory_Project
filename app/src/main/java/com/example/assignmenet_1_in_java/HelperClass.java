package com.example.assignmenet_1_in_java;


public class HelperClass {
    String name, email, username, password,age,phone,gender;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public HelperClass(String name, String email, String username, String password, String age, String phone, String gender) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.age=age;
        this.phone=phone;
        this.gender=gender;
    }

    public HelperClass() {
    }
}