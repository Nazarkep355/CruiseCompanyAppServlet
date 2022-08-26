package com.example.cruisecompanyappservlet.dao;

import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.entity.Route;
import com.example.cruisecompanyappservlet.entity.builders.PortBuilder;
import com.example.cruisecompanyappservlet.util.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PortDAO {
    private Logger logger = Logger.getLogger(PortDAO.class);
    private static final String SELECT_ALL_PORTS = "SELECT * FROM ports";
    private static final String SELECT_PORT_BY_ID = "SELECT * FROM ports WHERE id = ?";
    private static final String SELECT_ALL_PORTS_BY_ROUTE = "SELECT p.id, p.city FROM ports p, routes r" +
            "WHERE r.ports @> ARRAY[p.id] AND r.id = ?";
    private static final String SELECT_PAGINATED_PORTS_BY_CITY = "SELECT * FROM ports" +
            " WHERE city LIKE  '%' || ? || '%' ORDER BY id LIMIT ? OFFSET ?";

    private static final String SELECT_PORTS_PAGINATED = "SELECT * FROM ports ORDER BY city LIMIT ? OFFSET ?";

    private static final String SELECT_PORT_BY_CITY = "SELECT * FROM ports WHERE city = ?";
    private static final String UPDATE_PORT = "UPDATE ports SET city = ? WHERE id =?";

    private static final String INSERT_PORT = "INSERT INTO ports VALUES(default,?)";

    public List<Port> findAll() {
        List<Port> ports = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PORTS)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                ports.add(getPortFromResultSet(set));
            }
            return ports;
        } catch (Throwable e) {
            String message = "Can't find all ports";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public Port findById(long id) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PORT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getPortFromResultSet(set);
            }
            return null;
        } catch (Throwable e) {
            String message = "Can't find port by id";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Port> findByRoute(Route route) {
        List<Port> ports = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PORTS_BY_ROUTE)) {
            statement.setLong(1, route.getId());
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                ports.add(getPortFromResultSet(set));
            }
            return ports;
        } catch (Throwable e) {
            String message = "Can't find ports by route";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Port> findPaginatedLikeCityOrderById(String city, int offset) {
        List<Port> ports = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PAGINATED_PORTS_BY_CITY)) {
            statement.setString(1, city);
            statement.setInt(2, 5);
            statement.setInt(3, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                ports.add(getPortFromResultSet(set));
            }
            return ports;
        } catch (Throwable e) {
            String message = "Can't find ports by city";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }
    public List<Port> findPortOrderByCityPaginated(int offset){
        List<Port> ports = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PORTS_PAGINATED)) {
            statement.setInt(1, 6);
            statement.setInt(2, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                ports.add(getPortFromResultSet(set));
            }
            return ports;
        } catch (Throwable e) {
            String message = "Can't find ports paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public boolean update(Port port) {
        List<Port> ports = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PORT)) {
            statement.setString(1, port.getCity());
            statement.setLong(2, port.getId());
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't update ports";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public Optional<Port> findByCity(String city) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PORT_BY_CITY)) {
            statement.setString(1, city);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getPortFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (Throwable e) {
            String message = "Can't find port by city";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public boolean insert(Port port) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_PORT)) {
            statement.setString(1, port.getCity());
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't insert port";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private static Port getPortFromResultSet(ResultSet set) throws SQLException {
        return new PortBuilder()
                .id(set.getInt("id"))
                .city(set.getString("city"))
                .build();
    }

}
