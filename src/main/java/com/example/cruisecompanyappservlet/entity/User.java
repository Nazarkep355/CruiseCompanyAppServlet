package com.example.cruisecompanyappservlet.entity;

import java.util.Objects;

public class User {
    private long id;
    private String email;
    private String password;
    private String name;
    private int money;
    private UserType userType;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && money == user.money && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(name, user.name) && userType == user.userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", money=" + money +
                ", userType=" + userType +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, name, money, userType);
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setUserType(int ordinal){
        this.userType = UserType.values()[ordinal];
    }
    public boolean isAdmin(){
        return userType==UserType.ADMIN;
    }
}
