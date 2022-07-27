package com.example.cruisecompanyappservlet.dao;

import com.example.cruisecompanyappservlet.entity.Port;
import com.example.cruisecompanyappservlet.entity.Route;
import com.example.cruisecompanyappservlet.entity.builders.RouteBuilder;
import com.example.cruisecompanyappservlet.util.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    private static PortDAO portDAO;

    public RouteDAO() {
        portDAO = new PortDAO();
    }

    private Logger logger = Logger.getLogger(RouteDAO.class);
    private static final String SELECT_ALL_ROUTES = "SELECT * FROM routes";
    private static final String SELECT_BY_ID = "SELECT * FROM routes WHERE id = ?";
    private static final String SELECT_PAGINATED_ROUTES_ORDER_BY_ID = "SELECT * FROM routes " +
            "ORDER BY id LIMIT ? OFFSET ?";
    private static final String UPDATE_ROUTES = "UPDATE routes SET ports = ?, " +
            "delays = ? WHERE id =?";
    private static final String INSERT_ROUTES = "INSERT INTO routes VALUES(default,?,?)";

    public List<Route> findAll() {
        List<Route> routes = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROUTES)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                routes.add(getRouteFromResultSet(set));
            }
            return routes;
        } catch (SQLException e) {
            String message = "Can't find all routes";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public Route findById(long id) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getRouteFromResultSet(set);
            }
            return null;
        } catch (SQLException e) {
            String message = "Can't find route by id";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Route> findPaginatedOrderById(int offset) {
        List<Route> routes = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PAGINATED_ROUTES_ORDER_BY_ID)) {
            statement.setInt(1, 5);
            statement.setInt(2, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                routes.add(getRouteFromResultSet(set));
            }
            return routes;
        } catch (SQLException e) {
            String message = "Can't find paginated routes";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public boolean update(Route route) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ROUTES)) {
            Array ports = connection.createArrayOf("integer",
                    route.getPorts().stream()
                            .map(a -> a.getId())
                            .toArray());
            Array delays = connection.createArrayOf("integer",
                    route.getDelays()
                            .toArray());
            statement.setArray(1, ports);
            statement.setArray(2, delays);
            statement.setLong(3, route.getId());
            return statement.execute();
        } catch (SQLException e) {
            String message = "Can't update routes";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private boolean insert(Route route) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ROUTES)) {
            Array ports = connection.createArrayOf("integer",
                    route.getPorts().stream()
                            .map(a -> a.getId())
                            .toArray());
            Array delays = connection.createArrayOf("integer",
                    route.getDelays()
                            .toArray());
            statement.setArray(1, ports);
            statement.setArray(2, delays);
            return statement.execute();
        } catch (SQLException e) {
            String message = "Can't insert route";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private static Route getRouteFromResultSet(ResultSet set) throws SQLException {
        List<Integer> delays = new ArrayList<>();
        List<Port> ports = new ArrayList<>();
        Array array = set.getArray("ports");
        Object ob = array.getArray();
        Object[] arObj = (Object[]) ob;
         for (Object o : arObj) {
            ports.add(portDAO.findById(Long.parseLong(String.valueOf(o))));
        }
        array = set.getArray("delays");
        ob = array.getArray();
        arObj = (Object[]) ob;
        for (Object o : arObj) {
            delays.add((Integer) o);
        }
        return new RouteBuilder().id(set.getLong("id"))
                .delays(delays)
                .ports(ports)
                .build();
    }
}
