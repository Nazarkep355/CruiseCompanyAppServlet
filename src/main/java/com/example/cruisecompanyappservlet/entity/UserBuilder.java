package com.example.cruisecompanyappservlet.entity;

public class UserBuilder {
    private long id;
    private String email;
    private String password;
    private String name;
    private int money;
    private UserType userType;

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder money(int money) {
        this.money = money;
        return this;
    }

    public UserBuilder userType(UserType type) {
        this.userType = type;
        return this;
    }

    public UserBuilder userType(int number) {
        this.userType = UserType.values()[number];
        return this;
    }

    public UserBuilder id(long id) {
        this.id = id;
        return this;
    }

    public User build() {
        User user = new User();
        user.setUserType(this.userType);
        user.setId(this.id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setMoney(money);
        return user;
    }
}
