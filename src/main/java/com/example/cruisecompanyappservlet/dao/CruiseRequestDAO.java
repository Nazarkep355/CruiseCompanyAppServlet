package com.example.cruisecompanyappservlet.dao;


import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.entity.builders.CruiseRequestBuilder;
import com.example.cruisecompanyappservlet.util.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CruiseRequestDAO {
    private CruiseDAO cruiseDAO;
    private UserDAO userDAO;

    public CruiseRequestDAO() {
        cruiseDAO = new CruiseDAO();
        userDAO = new UserDAO();
    }

    private static Logger logger = Logger.getLogger(CruiseRequestDAO.class);
    private static final String SELECT_ALL_REQUESTS = "SELECT * FROM cruiserequests";
    private static final String SELECT_REQUEST_BY_ID = "SELECT * FROM cruiserequests WHERE id =?";
    private static final String SELECT_ALL_REQUESTS_OF_USER = "SELECT * FROM cruiserequests" +
            " WHERE sender = ?";
    private static final String SELECT_ALL_REQUESTS_OF_CRUISE = "SELECT * FROM cruiserequests" +
            " WHERE cruise = ?";
    private static final String SELECT_ALL_REQUESTS_OF_CRUISE_AND_STATUS = "SELECT * FROM cruiserequests" +
            " WHERE cruise = ? AND status =?";
    private static final String SELECT_REQUESTS_OF_CRUISE_AND_STATUS_PAGINATED = "SELECT * FROM " +
            "cruiserequests WHERE cruise = ? AND status = ? ORDER BY id DESC LIMIT ? OFFSET ?";
    private static final String SELECT_REQUESTS_OF_CRUISE_PAGINATED = "SELECT * FROM " +
            "cruiserequests WHERE cruise = ? ORDER BY id DESC LIMIT ? OFFSET ?";
    private static final String SELECT_REQUESTS_OF_USER_PAGINATED = "SELECT * FROM cruiserequests " +
            "WHERE sender = ? AND ORDER BY id DESC LIMIT ? OFFSET ?";
    private static final String SELECT_REQUEST_ORDER_BY_DESC_PAGINATED = "SELECT * FROM cruiserequests " +
            " ORDER BY id DESC LIMIT ? OFFSET ?";
    private static final String UPDATE_REQUEST = "UPDATE cruiserequests SET sender = ?, " +
            "cruise = ?, photo = ?, status = ?, class = ? WHERE id = ?";
    private static final String INSERT_REQUEST = "INSERT INTO cruiserequests VALUES(default ,?,?,?,?,?)";

    public List<CruiseRequest> findAll() {
        List<CruiseRequest> cruiseRequests = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruiseRequests.add(getCruiseRequestFromResultSet(set));
            }
            return cruiseRequests;
        } catch (Throwable e) {
            String message = "Can't find all requests";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<CruiseRequest> findByUser(User user) {
        List<CruiseRequest> cruiseRequests = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS_OF_USER)) {
            statement.setLong(1, user.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruiseRequests.add(getCruiseRequestFromResultSet(set));
            }
            return cruiseRequests;
        } catch (Throwable e) {
            String message = "Can't find requests by sender";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<CruiseRequest> findByCruise(Cruise cruise) {
        List<CruiseRequest> cruiseRequests = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS_OF_CRUISE)) {
            statement.setLong(1, cruise.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruiseRequests.add(getCruiseRequestFromResultSet(set));
            }
            return cruiseRequests;
        } catch (Throwable e) {
            String message = "Can't find requests by cruise";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<CruiseRequest> findByCruiseAndStatus(Cruise cruise, Status status) {
        List<CruiseRequest> cruiseRequests = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REQUESTS_OF_CRUISE_AND_STATUS)) {
            statement.setLong(1, cruise.getId());
            statement.setString(2, status.name());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruiseRequests.add(getCruiseRequestFromResultSet(set));
            }
            return cruiseRequests;
        } catch (Throwable e) {
            String message = "Can't find requests by cruise and status";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public CruiseRequest findById(Long id) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REQUEST_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getCruiseRequestFromResultSet(set);
            }
            return null;
        } catch (Throwable e) {
            String message = "Can't find request by id";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<CruiseRequest> findByCruiseAndStatusPaginated(Cruise cruise, Status status, int offset) {
        List<CruiseRequest> cruiseRequests = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REQUESTS_OF_CRUISE_AND_STATUS_PAGINATED)) {
            statement.setLong(1, cruise.getId());
            statement.setString(2, status.name());
            statement.setInt(3, 5);
            statement.setInt(4, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruiseRequests.add(getCruiseRequestFromResultSet(set));
            }
            return cruiseRequests;
        } catch (Throwable e) {
            String message = "Can't find requests by cruise and status paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }
    public List<CruiseRequest> findByCruisePaginated(Cruise cruise, int offset){
        List<CruiseRequest> cruiseRequests = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REQUESTS_OF_CRUISE_PAGINATED)) {
            statement.setLong(1, cruise.getId());
            statement.setInt(2, 5);
            statement.setInt(3, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruiseRequests.add(getCruiseRequestFromResultSet(set));
            }
            return cruiseRequests;
        } catch (Throwable e) {
            String message = "Can't find requests by cruise and status paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<CruiseRequest> findByUserPaginated(User user, int offset) {
        List<CruiseRequest> cruiseRequests = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REQUESTS_OF_USER_PAGINATED)) {
            statement.setLong(1, user.getId());
            statement.setInt(2, 5);
            statement.setLong(3, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruiseRequests.add(getCruiseRequestFromResultSet(set));
            }
            return cruiseRequests;
        } catch (Throwable e) {
            String message = "Can't find requests by user paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }
    public List<CruiseRequest> findAllOrderByIdDescPaginated(int offset){
        List<CruiseRequest> cruiseRequests = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REQUEST_ORDER_BY_DESC_PAGINATED)) {
            statement.setInt(1,5);
            statement.setInt(2,offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruiseRequests.add(getCruiseRequestFromResultSet(set));
            }
            return cruiseRequests;
        } catch (Throwable e) {
            String message = "Can't find all requests paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public boolean update(CruiseRequest request) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_REQUEST)) {
            statement.setLong(1, request.getSender().getId());
            statement.setLong(2, request.getCruise().getId());
            statement.setString(3, request.getPhoto());
            statement.setString(4, request.getStatus().name());
            statement.setString(5, request.getRoomClass().name());
            statement.setLong(6, request.getId());
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't update request";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public boolean insert(CruiseRequest request) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_REQUEST)) {
            statement.setLong(1, request.getSender().getId());
            statement.setLong(2, request.getCruise().getId());
            statement.setString(3, request.getPhoto());
            statement.setString(4, request.getStatus().name());
            statement.setString(5, request.getRoomClass().name());
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't insert request";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private CruiseRequest getCruiseRequestFromResultSet(ResultSet set) throws SQLException, DAOException {
        User sender = userDAO.findById(set.getLong("sender"));
        Cruise cruise = cruiseDAO.findById(set.getLong("cruise"));
        return new CruiseRequestBuilder()
                .id(set.getLong("id"))
                .cruise(cruise)
                .sender(sender)
                .photo(set.getString("photo"))
                .status(Status.valueOf(set.getString("status")))
                .roomClass(RoomClass.valueOf(set.getString("class")))
                .build();

    }
}
