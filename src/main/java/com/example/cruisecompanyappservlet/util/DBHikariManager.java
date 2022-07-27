package com.example.cruisecompanyappservlet.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class DBHikariManager {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;
    static {
        config.setDriverClassName(org.postgresql.Driver.class.getName());
        config.setMinimumIdle(400);
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/cruisecompanyservlet");
        config.setUsername("postgres");
        config.setPassword("3228");
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        config.setMaximumPoolSize(35);
        config.setIdleTimeout(40);
        dataSource= new HikariDataSource(config);
    }
    public static Date TimestampToDate(Timestamp timestamp){
        Date d= new Date();
        d.setYear(timestamp.getYear());
        d.setMonth(timestamp.getMonth());
        d.setDate(timestamp.getDate());
        d.setHours(timestamp.getHours());
        d.setMinutes(timestamp.getMinutes());
        d.setSeconds(0);
        return d;
    }
    static public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

