package com.example.cruisecompanyappservlet.service;

import com.example.cruisecompanyappservlet.dao.DAOException;
import com.example.cruisecompanyappservlet.dao.UserDAO;
import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.entity.builders.UserBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserDAO userDAO;
    private UserService userService;
    public UserServiceTest(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userDAO);
    }

    @Test
    void getAllUsers() throws DAOException {
        userService.getAllUsers();
        verify(userDAO,times(1)).findAll();
    }

    @Test
    void isUserWithEmailExist() throws DAOException {
        String email1="email@gmail.com";
        String email2 = "email@mail.com";
        when(userDAO.findByEmail(email1)).thenReturn(new User());
        when(userDAO.findByEmail(email2)).thenReturn(null);
        Assertions.assertTrue(userService.isUserWithEmailExist(email1));
        assertFalse(userService.isUserWithEmailExist(email2));


    }

    @Test
    void registerUser() throws DAOException {
        String name = "name";
        String password = "Passw0rd";
        String email = "email@gmai.com";
        String emailInUse = "email2@gmail.com";
        User user = new UserBuilder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        when(userDAO.findByEmail(emailInUse)).thenReturn(new User());
        Assertions.assertThrows(IllegalArgumentException.class,()->userService.registerUser(emailInUse,password,name),"emailInUse");
        userService.registerUser(email,password,name);
        verify(userDAO,times(1)).insertUser(user);


    }

    @Test
    void update() throws DAOException {
        User user = new User();
        userService.update(user);
        verify(userDAO,times(1)).updateUser(user);
    }

    @Test
    void changeUserMoney() throws DAOException {
        User user = new UserBuilder()
                .money(500)
                .build();
        User user2 = new UserBuilder()
                .money(1000)
                .build();
        userService.ChangeUserMoney(user,500);
        verify(userDAO,times(1)).updateUser(user2);
    }

    @Test
    void loginUser() throws DAOException {
        String name = "name";
        String password = "Passw0rd";
        String email = "email@gmai.com";
        String emailInUse = "email2@gmail.com";
        User user = new UserBuilder()
                .email(emailInUse)
                .password(password)
                .name(name)
                .build();
        when(userDAO.findByEmail(emailInUse)).thenReturn(user);
        Assertions.assertThrows(IllegalArgumentException.class,()->userService.loginUser(email,password),"Wrong password");
        Assertions.assertThrows(IllegalArgumentException.class,()->userService.loginUser(email,"password"),"Wrong password");
        User userLogged = userService.loginUser(emailInUse,password);
        Assertions.assertEquals(user,userLogged);

    }
}