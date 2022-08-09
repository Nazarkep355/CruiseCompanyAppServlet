package com.example.cruisecompanyappservlet.dao;

import com.example.cruisecompanyappservlet.entity.Cruise;
import com.example.cruisecompanyappservlet.entity.Staff;
import com.example.cruisecompanyappservlet.entity.builders.StaffBuilder;
import com.example.cruisecompanyappservlet.util.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private Logger logger = Logger.getLogger(StaffDAO.class);
    private static final String SELECT_ALL_STAFF = "SELECT * FROM staff";
    private static final String SELECT_ALL_BY_CRUISE = "SELECT s.id,s.name,s.position FROM " +
            "staff s, cruises c WHERE c.staff @> ARRAY[s.id] AND c.id = ?";
    private static final String SELECT_PAGINATED_STAFF_BY_CRUISE = "SELECT s.id,s.name,s.position FROM" +
            " staff s, cruises c WHERE c.staff @> ARRAY[s.id] AND c.id = ? LIMIT ? OFFSET ?";
    private static final String SELECT_PAGINATED_STAFF_ORDERED_BY_ID = "SELECT * FROM staff ORDER BY id" +
            " LIMIT ? OFFSET ?";
    private static final String SELECT_STAFF_LIKE_NAME = "SELECT * FROM staff WHERE name " +
            "LIKE '%' || ? || '%' LIMIT ? OFFSET ?";
    private static final String SELECT_STAFF_BY_ID = "SELECT * FROM staff WHERE id = ?";
    private static final String SELECT_STAFF_BY_NAME = "SELECT * FROM staff WHERE name = ?";
    private static final String UPDATE_STAFF_BY_ID = "UPDATE staff SET name =?," +
            " position = ? WHERE id = ?";
    private static final String INSERT_STAFF = "INSERT INTO staff values(default,?,?)";

    public List<Staff> findAll() {
        List<Staff> staff = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_STAFF)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                staff.add(getStaffFromResultSet(set));
            }
            return staff;
        } catch (Throwable e) {
            String message = "Can't find all staff";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Staff> findAllByCruise(Cruise cruise) {
        List<Staff> staff = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_BY_CRUISE)) {
            statement.setLong(1, cruise.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                staff.add(getStaffFromResultSet(set));
            }
            return staff;
        } catch (Throwable e) {
            String message = "Can't find staff by cruise";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Staff> findPaginatedByCruise(Cruise cruise, int offset) {
        List<Staff> staff = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PAGINATED_STAFF_BY_CRUISE)) {
            statement.setLong(1, cruise.getId());
            statement.setInt(2, 5);
            statement.setInt(3, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                staff.add(getStaffFromResultSet(set));
            }
            return staff;
        } catch (Throwable e) {
            String message = "Can't find paginated staff by cruise";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Staff> findPaginatedOrderById(int offset) {
        List<Staff> staff = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PAGINATED_STAFF_ORDERED_BY_ID)) {
            statement.setInt(1, 5);
            statement.setInt(2, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                staff.add(getStaffFromResultSet(set));
            }
            return staff;
        } catch (Throwable e) {
            String message = "Can't find paginated staff";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Staff> findPaginatedListThatNameContains(String partName, int offset) {
        List<Staff> staff = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STAFF_LIKE_NAME)) {
            statement.setString(1, partName);
            statement.setInt(2, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                staff.add(getStaffFromResultSet(set));
            }
            return staff;
        } catch (Throwable e) {
            String message = "Can't find paginated staff by name";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public Staff findById(long id) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STAFF_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getStaffFromResultSet(set);
            }
            return null;
        } catch (Throwable e) {
            String message = "Can't find staff by id";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public boolean update(Staff staff) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STAFF_BY_ID)) {
            statement.setString(1, staff.getName());
            statement.setString(2, staff.getPosition());
            statement.setLong(3, staff.getId());
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't update staff by id";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }
    public boolean isStaffWithNameExists(String name){
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_STAFF_BY_NAME)) {
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();
            return (set.next());
        } catch (Throwable e) {
            String message = "Can't insert staff";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public boolean insert(Staff staff) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_STAFF)) {
            statement.setString(1, staff.getName());
            statement.setString(2, staff.getPosition());
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't insert staff";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private static Staff getStaffFromResultSet(ResultSet set) throws SQLException {
        return new StaffBuilder().id(set.getInt("id"))
                .name(set.getString("name"))
                .position(set.getString("position"))
                .build();
    }
}
