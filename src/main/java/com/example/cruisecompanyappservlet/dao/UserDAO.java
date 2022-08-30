package com.example.cruisecompanyappservlet.dao;

import com.example.cruisecompanyappservlet.entity.User;
import com.example.cruisecompanyappservlet.entity.builders.UserBuilder;
import com.example.cruisecompanyappservlet.util.DBHikariManager;
import com.example.cruisecompanyappservlet.util.PasswordEncryptor;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Logger logger = Logger.getLogger(UserDAO.class);
    private static String SELECT_ALL_FROM_USERS = "SELECT * FROM users";
    private static String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id =?";
    private static String SELECT_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static String INSERT_USER = "INSERT INTO users VALUES(default,?,?,?,0,0)";
    private static String UPDATE_USER_BY_ID = "UPDATE users SET email = ?," +
            " password = ?,name = ?, money = ?, type = ? WHERE id = ?";

    public List<User> findAll() throws DAOException {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_FROM_USERS)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                users.add(userFromResultSet(set));
            }
            return users;
        } catch (SQLException e) {
            String message = "Can't find all users";
            logger.info(message, e);
            throw new DAOException(message, e);
        }

    }

    public User findById(long id) throws DAOException {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (!set.next()) return null;
            return userFromResultSet(set);
        } catch (SQLException e) {
            String message = "Can't find user by id";
            logger.info(message, e);
            throw new DAOException(message, e);
        }
    }

    public User findByEmail(String email) throws DAOException {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            if (!set.next()) return null;
            return userFromResultSet(set);
        } catch (SQLException e) {
            String message = "Can't find user by email";
            logger.info(message, e);
            throw new DAOException(message, e);
        }
    }

    public boolean insertUser(User user) throws DAOException {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, PasswordEncryptor.getEncrypted(user.getPassword()));
            statement.setString(3, user.getName());
            return statement.execute();
        } catch (SQLException e) {
            String message = "Can't insert user";
            logger.info(message, e);
            throw new DAOException(message, e);
        }
    }

    public boolean updateUser(User user) throws DAOException {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, PasswordEncryptor.getEncrypted(user.getPassword()));
            statement.setString(3, user.getName());
            statement.setInt(4, user.getMoney());
            statement.setInt(5, user.getUserType().ordinal());
            statement.setLong(6, user.getId());
            return statement.execute();
        } catch (SQLException e) {
            String message = "Can't update user";
            logger.info(message, e);
            throw new DAOException(message, e);
        }
    }


    private static User userFromResultSet(ResultSet set) throws SQLException {
        return new UserBuilder().id(set.getInt("id"))
                .email(set.getString("email"))
                .password(PasswordEncryptor.getDecrypted(set.getString("password")))
                .name(set.getString("name"))
                .money(set.getInt("money"))
                .userType(set.getInt("type"))
                .build();
    }

}
