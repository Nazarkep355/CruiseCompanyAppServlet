package com.example.cruisecompanyappservlet.dao;

import com.example.cruisecompanyappservlet.entity.*;
import com.example.cruisecompanyappservlet.entity.builders.CruiseBuilder;
import com.example.cruisecompanyappservlet.util.CruiseUtil;
import com.example.cruisecompanyappservlet.util.DBHikariManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CruiseDAO {


    private static Logger logger = Logger.getLogger(CruiseDAO.class);
    private StaffDAO staffDAO;
    private RouteDAO routeDAO;
    public CruiseDAO(StaffDAO staffDAO, RouteDAO routeDAO) {
        this.staffDAO = staffDAO;
        this.routeDAO = routeDAO;
    }

    public CruiseDAO() {
        routeDAO = new RouteDAO();
        staffDAO = new StaffDAO();
    }
    private static final String SELECT_ALL_CRUISES = "SELECT * FROM cruises";
    private static final String SELECT_PAGINATED_CRUISES = "SELECT * FROM cruises ORDER" +
            " BY id DESC LIMIT ? OFFSET ?";
    private static final String SELECT_CRUISE_BY_ID = "SELECT * FROM cruises WHERE id = ?";
    private static final String SELECT_CRUISE_ACTUAL = "SELECT * FROM cruises WHERE " +
            "departure > now() ORDER BY id DESC LIMIT ? OFFSET ?";
    private static final String SELECT_CRUISE_WITH_FREE_PLACES_PAGINATED = "SELECT * FROM cruises WHERE " +
            "departure> now() AND (premium @> ARRAY[false] " +
            "OR middle @> ARRAY[false] OR econom @> ARRAY[false])" +
            " ORDER BY id LIMIT ? OFFSET ?";
    private static final String SELECT_CRUISE_BY_CITY = "SELECT distinct c.id,c.route, c.departure, " +
            "c.costeconom, c.costmiddle, c.costpremium, c.seats , c.econom," +
            "                c.middle, c.premium, c.staff, c.status  FROM cruises c," +
            "                ports p, routes r WHERE c.departure > now()" +
            "  AND premium @> ARRAY [false]  AND c.route =r.id AND r.ports @> ARRAY [p.id]" +
            " AND p.city LIKE '%' || ? || '%' AND( middle @> ARRAY [false] " +
            "OR econom @> ARRAY [false]) LIMIT ? OFFSET ?";
    private final static String INSERT_CRUISE = "INSERT INTO cruises VALUES(default,?,?,?,?,?,?," +
            "'WAITING',?,?,?,?)";
    private final static String UPDATE_CRUISE = "UPDATE cruises SET route =?, departure= ?, " +
            "costeconom = ?, staff = ?, premium = ?, econom = ?, middle = ?, status = ?, seats =?" +
            ", costmiddle = ?, costpremium = ? WHERE id = ?";
    public List<Cruise> findAll() {
        List<Cruise> cruises = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CRUISES)) {
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruises.add(getCruiseFromResultSet(set));
            }
            return cruises;
        } catch (Throwable e) {
            String message = "Can't find all cruises";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Cruise> findAllPaginatedOrderByIdDesc(int offset) {
        List<Cruise> cruises = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PAGINATED_CRUISES)) {
            statement.setInt(1, 5);
            statement.setInt(2, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruises.add(getCruiseFromResultSet(set));
            }
            return cruises;
        } catch (Throwable e) {
            String message = "Can't find all cruises paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public Cruise findById(long id) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CRUISE_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getCruiseFromResultSet(set);
            }
            return null;
        } catch (Throwable e) {
            String message = "Can't find cruise by id";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Cruise> findActualPaginatedOrderByIdDesc(int offset) {
        List<Cruise> cruises = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CRUISE_ACTUAL)) {
            statement.setInt(1, 5);
            statement.setInt(2, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruises.add(getCruiseFromResultSet(set));
            }
            return cruises;
        } catch (Throwable e) {
            String message = "Can't find actual cruises paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Cruise> findActualWithFreePlacesOrderByIdPaginated(int offset) {
        List<Cruise> cruises = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CRUISE_WITH_FREE_PLACES_PAGINATED)) {
            statement.setInt(1, 5);
            statement.setInt(2, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruises.add(getCruiseFromResultSet(set));
            }
            return cruises;
        } catch (Throwable e) {
            String message = "Can't find cruises with free places paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public List<Cruise> findActualWithFreePlacesCityLike(String city, int offset) {
        List<Cruise> cruises = new ArrayList<>();
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CRUISE_BY_CITY)) {
            statement.setString(1, city);
            statement.setInt(2, 5);
            statement.setInt(3, offset);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                cruises.add(getCruiseFromResultSet(set));
            }
            return cruises;
        } catch (Throwable e) {
            String message = "Can't find cruises with free places with city like{*} paginated";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public boolean insert(Cruise cruise) {
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CRUISE)) {
            Date departure = cruise.getSchedule().get(cruise.getRoute().getPorts().get(0));
            Array staffArray = connection.createArrayOf("integer", cruise.getStaff()
                    .stream()
                    .map(Staff::getId)
                    .toArray());
            int premiumPlaces = cruise.getFreePlaces().get(RoomClass.PREMIUM);
            List<Boolean> booleans = new ArrayList<>();
            for (int i = 0; i < premiumPlaces; i++)
                booleans.add(false);
            Array premiumArray = connection.createArrayOf("boolean", booleans.toArray());
            int economPlaces = cruise.getFreePlaces().get(RoomClass.ECONOM);
            booleans = new ArrayList<>();
            for (int i = 0; i < economPlaces; i++)
                booleans.add(false);
            Array economArray = connection.createArrayOf("boolean", booleans.toArray());
            int middlePlaces = cruise.getFreePlaces().get(RoomClass.MIDDLE);
            booleans = new ArrayList<>();
            for (int i = 0; i < middlePlaces; i++)
                booleans.add(false);
            Array middleArray = connection.createArrayOf("boolean", booleans.toArray());
            statement.setLong(1, cruise.getRoute().getId());
            statement.setTimestamp(2, new Timestamp(departure.getTime()));
            statement.setArray(3, staffArray);
            statement.setArray(4, premiumArray);
            statement.setArray(5, economArray);
            statement.setArray(6, middleArray);
            statement.setInt(7,cruise.getSeats());
            statement.setInt(8,cruise.getCostEconom());
            statement.setInt(9,cruise.getCostMiddle());
            statement.setInt(10,cruise.getCostPremium());
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't insert cruise";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public boolean update(Cruise cruise){
        try (Connection connection = DBHikariManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CRUISE)) {
            Date departure = cruise.getSchedule().get(cruise.getRoute().getPorts().get(0));
            Array staffArray = connection.createArrayOf("integer", cruise.getStaff()
                    .stream()
                    .map(a->a.getId())
                    .toArray());
            int premiumPlaces = cruise.getFreePlaces().get(RoomClass.PREMIUM);
            List<Boolean> booleans = new ArrayList<>();
            for (int i = 0; i < premiumPlaces; i++)
                booleans.add(false);
            Array premiumArray = connection.createArrayOf("boolean", booleans.toArray());
            int economPlaces = cruise.getFreePlaces().get(RoomClass.ECONOM);
            booleans = new ArrayList<>();
            for (int i = 0; i < economPlaces; i++)
                booleans.add(false);
            Array economArray = connection.createArrayOf("boolean", booleans.toArray());
            int middlePlaces = cruise.getFreePlaces().get(RoomClass.MIDDLE);
            booleans = new ArrayList<>();
            for (int i = 0; i < middlePlaces; i++)
                booleans.add(false);
            Array middleArray = connection.createArrayOf("boolean", booleans.toArray());
            statement.setLong(1, cruise.getRoute().getId());
            statement.setTimestamp(2, new Timestamp(departure.getTime()));
            statement.setInt(3, cruise.getCostEconom());
            statement.setArray(4, staffArray);
            statement.setArray(5, premiumArray);
            statement.setArray(6, economArray);
            statement.setArray(7, middleArray);
            statement.setString(8,cruise.getStatus().name());
            statement.setInt(9,cruise.getSeats());
            statement.setInt(10,cruise.getCostMiddle());
            statement.setInt(11,cruise.getCostPremium());
            statement.setLong(12,cruise.getId());
            return statement.execute();
        } catch (Throwable e) {
            String message = "Can't update cruise";
            logger.info(message, e);
            throw new RuntimeException(message, e);
        }
    }

    private Cruise getCruiseFromResultSet(ResultSet set) throws SQLException {
        Route route = routeDAO.findById(set.getLong("route"));
        List<Staff> staff = new ArrayList<>();
        HashMap<RoomClass, Integer> freePlaces = new HashMap<>();
        freePlaces.put(RoomClass.PREMIUM, 0);
        freePlaces.put(RoomClass.ECONOM, 0);
        freePlaces.put(RoomClass.MIDDLE, 0);
        Array array = set.getArray("staff");
        Object ob = array.getArray();
        Object[] objArr = (Object[]) ob;
        for (Object o : objArr) {
            Staff st = staffDAO.findById(Long.parseLong(String.valueOf(o)));
            staff.add(st);
        }
        array = set.getArray("premium");
        ob = array.getArray();
        objArr = (Object[]) ob;
        for (Object o : objArr) {
            if (!((Boolean) o))
                freePlaces.put(RoomClass.PREMIUM, freePlaces.get(RoomClass.PREMIUM) + 1);
        }
        array = set.getArray("econom");
        ob = array.getArray();
        objArr = (Object[]) ob;
        for (Object o : objArr) {
            if (!((Boolean) o))
                freePlaces.put(RoomClass.ECONOM, freePlaces.get(RoomClass.ECONOM) + 1);
        }
        array = set.getArray("middle");
        ob = array.getArray();
        objArr = (Object[]) ob;
        for (Object o : objArr) {
            if (!((Boolean) o))
                freePlaces.put(RoomClass.MIDDLE, freePlaces.get(RoomClass.MIDDLE) + 1);
        }
        Timestamp timestamp = set.getTimestamp("departure");
        Date depDate = new Date(timestamp.getTime());
        HashMap<Port, Date> schedule = CruiseUtil
                .calculateSchedule(depDate, route.getPorts(), route.getDelays());
        Status status = Status.valueOf(set.getString("status"));
        return new CruiseBuilder().id(set.getLong("id"))
                .costEconom(set.getInt("costeconom"))
                .costMiddle(set.getInt("costmiddle"))
                .costPremium(set.getInt("costpremium"))
                .seats(set.getInt("seats"))
                .schedule(schedule)
                .route(route)
                .status(status)
                .staff(staff)
                .freePlaces(freePlaces)
                .build();
    }


}
