package com.example.cruisecompanyappservlet.dao;

import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.entity.builders.TicketBuilder;
import com.example.cruisecompanyappservlet.util.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketDAO {
    private CruiseDAO cruiseDAO;
    private UserDAO userDAO;

    public TicketDAO() {
        cruiseDAO = new CruiseDAO();
        userDAO = new UserDAO();
    }

    private static Logger logger = Logger.getLogger(TicketDAO.class);
    private static final String SELECT_ALL_TICKETS = "SELECT * FROM tickets";
    private static final String SELECT_TICKET_BY_ID = "SELECT * FROM tickets WHERE id = ?";
    private static final String SELECT_ALL_TICKETS_BY_USER = "SELECT * FROM tickets WHERE owner = ?";
    private static final String SELECT_ALL_TICKETS_BY_CRUISE = "SELECT * FROM tickets WHERE cruise = ?";
    private static final String SELECT_TICKETS_BY_USER_PAGINATED = "SELECT * FROM tickets WHERE owner = ?" +
            " ORDER BY id DESC LIMIT ? OFFSET ?";
    private static final String UPDATE_TICKET = "UPDATE tickets SET cruise = ?, owner = ?, roomclass = ?," +
            "cost = ?, date =? WHERE id = ?";
    private static final String INSERT_TICKET = "INSERT INTO tickets VALUES(default,?,?,?,?,?)";

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TICKETS)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                tickets.add(getTicketFromResultSet(set));
            }
            return tickets;
        } catch (Throwable e) {
            String message = "Can't find all tickets";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public Ticket findById(long id) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TICKET_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getTicketFromResultSet(set);
            }
            return null;
        } catch (Throwable e) {
            String message = "Can't find ticket by id";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Ticket> findByUser(User user) {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TICKETS_BY_USER)) {
            statement.setLong(1, user.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                tickets.add(getTicketFromResultSet(set));
            }
            return tickets;
        } catch (Throwable e) {
            String message = "Can't find all tickets by user";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }
    public List<Ticket> findByCruise(Cruise cruise){
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TICKETS_BY_CRUISE)) {
            statement.setLong(1,cruise.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()){
                tickets.add(getTicketFromResultSet(set));
            }
            return tickets;
        } catch (Throwable e) {
            String message = "Can't find tickets by cruise";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }
    public List<Ticket> findByUserPaginate(User user,int offset){
        List<Ticket> tickets = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_TICKETS_BY_USER_PAGINATED)) {
            statement.setLong(1,user.getId());
            statement.setInt(2,5);
            statement.setInt(3,offset);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                tickets.add(getTicketFromResultSet(set));
            }
            return tickets;
        } catch (Throwable e) {
            String message = "Can't find tickets by user paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }
    public boolean update(Ticket ticket){
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET)) {
            statement.setLong(1,ticket.getCruise().getId());
            statement.setLong(2,ticket.getOwner().getId());
            statement.setString(3,ticket.getRoomClass().name());
            statement.setInt(4,ticket.getCost());
            statement.setTimestamp(5,new Timestamp(ticket.getPurchaseDate().getTime()));
            statement.setLong(6,ticket.getId());
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't update ticket";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }
    public boolean insert(Ticket ticket){
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_TICKET)) {
            statement.setLong(1,ticket.getCruise().getId());
            statement.setLong(2,ticket.getOwner().getId());
            statement.setString(3,ticket.getRoomClass().name());
            statement.setInt(4,ticket.getCost());
            statement.setTimestamp(5,new Timestamp(ticket.getPurchaseDate().getTime()));
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't insert ticket";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private Ticket getTicketFromResultSet(ResultSet set) throws SQLException, DAOException {
        Long cruiseId = set.getLong("cruise");
        Long ownerId = set.getLong("owner");
        Timestamp timestamp = set.getTimestamp("date");
        RoomClass roomClass = RoomClass.valueOf(set.getString("roomClass"));
        Cruise cruise = cruiseDAO.findById(cruiseId);
        User owner = userDAO.findById(ownerId);
        Date date = new Date(timestamp.getTime());
        return new TicketBuilder()
                .id(set.getLong("id"))
                .cruise(cruise)
                .owner(owner)
                .roomClass(roomClass)
                .cost(set.getInt("cost"))
                .purchaseDate(date)
                .build();
    }
}
