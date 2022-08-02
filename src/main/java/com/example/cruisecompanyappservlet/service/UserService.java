package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.dao.UserDAO;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.entity.builders.UserBuilder;

import java.util.List;

public class UserService {
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private UserDAO userDAO;
    public UserService(){
        userDAO = new UserDAO();
    }
    public List<User> getAllUsers() throws DAOException {
        return userDAO.findAll();
    }
    public boolean isUserWithEmailExist(String email) throws DAOException {
        return userDAO.findByEmail(email)!=null;
    }
    public User registerUser(String email,String password,String name) throws DAOException {
        if(isUserWithEmailExist(email))
            throw new IllegalArgumentException("emailInUse");
        User user = new UserBuilder().email(email).password(password).name(name).build();
        userDAO.insertUser(user);
        return userDAO.findByEmail(email);
    }
    public boolean update(User user) throws DAOException {
        return userDAO.updateUser(user);
    }
    public User loginUser(String email, String password) throws DAOException {
        if(!isUserWithEmailExist(email)){
            throw new IllegalArgumentException("Wrong password");
        }
        User user= userDAO.findByEmail(email);
        if(!user.getPassword().equals(password))
            throw new IllegalArgumentException("Wrong password");
        return user;
    }
}
