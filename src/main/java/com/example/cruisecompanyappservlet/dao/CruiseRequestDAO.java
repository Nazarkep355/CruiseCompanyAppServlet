package com.example.cruisecompanyappservlet.dao;


import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.entity.builders.CruiseRequestBuilder;
import com.example.cruisecompanyappservlet.entity.builders.TicketBuilder;
import com.example.cruisecompanyappservlet.util.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CruiseRequestDAO {
    private CruiseDAO cruiseDAO;
    private UserDAO userDAO;

    public CruiseRequestDAO() {
        cruiseDAO = new CruiseDAO();
        userDAO = new UserDAO();
    }

    public CruiseRequestDAO(CruiseDAO cruiseDAO, UserDAO userDAO) {
        this.cruiseDAO = cruiseDAO;
        this.userDAO = userDAO;
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
    private static String UPDATE_USER_BY_ID = "UPDATE users SET email = ?," +
            " password = ?,name = ?, money = ?, type = ? WHERE id = ?";
    private final static String UPDATE_CRUISE = "UPDATE cruises SET route =?, departure= ?, " +
            "costeconom = ?, staff = ?, premium = ?, econom = ?, middle = ?, status = ?, seats =?" +
            ", costmiddle = ?, costpremium = ? WHERE id = ?";
    private static final String INSERT_TICKET = "INSERT INTO tickets VALUES(default,?,?,?,?,?)";
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

    public List<CruiseRequest> findByCruisePaginated(Cruise cruise, int offset) {
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

    public List<CruiseRequest> findAllOrderByIdDescPaginated(int offset) {
        List<CruiseRequest> cruiseRequests = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REQUEST_ORDER_BY_DESC_PAGINATED)) {
            statement.setInt(1, 5);
            statement.setInt(2, offset);
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

    public boolean createTicketTransaction(CruiseRequest cruiseRequest) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statementInsertTicket = connection.prepareStatement(INSERT_TICKET);
             PreparedStatement statementUpdateUser = connection.prepareStatement(UPDATE_USER_BY_ID);
             PreparedStatement statementUpdateCruise = connection.prepareStatement(UPDATE_CRUISE);
             PreparedStatement statementUpdateRequest = connection.prepareStatement(UPDATE_REQUEST)) {
            connection.setAutoCommit(false);
            Ticket ticket = TicketBuilder.createTicketBy(cruiseRequest);
            statementUpdateUser.setString(1, cruiseRequest.getSender().getEmail());
            statementUpdateUser.setString(2, cruiseRequest.getSender().getPassword());
            statementUpdateUser.setString(3, cruiseRequest.getSender().getName());
            statementUpdateUser.setInt(4, cruiseRequest.getSender().getMoney());
            statementUpdateUser.setInt(5, cruiseRequest.getSender().getUserType().ordinal());
            statementUpdateUser.setLong(6, cruiseRequest.getSender().getId());
            statementUpdateUser.execute();


            Date departure = cruiseRequest.getCruise().getSchedule().get(cruiseRequest.getCruise().getRoute().getPorts().get(0));
            Array staffArray = connection.createArrayOf("integer", cruiseRequest.getCruise().getStaff()
                    .stream()
                    .map(a -> a.getId())
                    .toArray());
            int premiumPlaces = cruiseRequest.getCruise().getFreePlaces().get(RoomClass.PREMIUM);
            List<Boolean> booleans = new ArrayList<>();
            for (int i = 0; i < premiumPlaces; i++)
                booleans.add(false);
            Array premiumArray = connection.createArrayOf("boolean", booleans.toArray());
            int economPlaces = cruiseRequest.getCruise().getFreePlaces().get(RoomClass.ECONOM);
            booleans = new ArrayList<>();
            for (int i = 0; i < economPlaces; i++)
                booleans.add(false);
            Array economArray = connection.createArrayOf("boolean", booleans.toArray());
            int middlePlaces = cruiseRequest.getCruise().getFreePlaces().get(RoomClass.MIDDLE);
            booleans = new ArrayList<>();
            for (int i = 0; i < middlePlaces; i++)
                booleans.add(false);
            Array middleArray = connection.createArrayOf("boolean", booleans.toArray());
            statementUpdateCruise.setLong(1, cruiseRequest.getCruise().getRoute().getId());
            statementUpdateCruise.setTimestamp(2, new Timestamp(departure.getTime()));
            statementUpdateCruise.setInt(3, cruiseRequest.getCruise().getCostEconom());
            statementUpdateCruise.setArray(4, staffArray);
            statementUpdateCruise.setArray(5, premiumArray);
            statementUpdateCruise.setArray(6, economArray);
            statementUpdateCruise.setArray(7, middleArray);
            statementUpdateCruise.setString(8, cruiseRequest.getCruise().getStatus().name());
            statementUpdateCruise.setInt(9, cruiseRequest.getCruise().getSeats());
            statementUpdateCruise.setInt(10, cruiseRequest.getCruise().getCostMiddle());
            statementUpdateCruise.setInt(11, cruiseRequest.getCruise().getCostPremium());
            statementUpdateCruise.setLong(12, cruiseRequest.getCruise().getId());
            statementUpdateCruise.execute();



            statementUpdateRequest.setLong(1, cruiseRequest.getSender().getId());
            statementUpdateRequest.setLong(2, cruiseRequest.getCruise().getId());
            statementUpdateRequest.setString(3, cruiseRequest.getPhoto());
            statementUpdateRequest.setString(4, cruiseRequest.getStatus().name());
            statementUpdateRequest.setString(5, cruiseRequest.getRoomClass().name());
            statementUpdateRequest.setLong(6, cruiseRequest.getId());
            statementUpdateRequest.execute();


            statementInsertTicket.setLong(1, ticket.getCruise().getId());
            statementInsertTicket.setLong(2, ticket.getOwner().getId());
            statementInsertTicket.setString(3, ticket.getRoomClass().name());
            statementInsertTicket.setInt(4, ticket.getCost());
            statementInsertTicket.setTimestamp(5, new Timestamp(ticket.getPurchaseDate().getTime()));
            statementInsertTicket.execute();


            connection.commit();
            return true;

        } catch (Throwable e) {
            String message = "Can't update request";
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
