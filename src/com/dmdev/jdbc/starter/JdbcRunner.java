package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcRunner {

    public static void main(String[] args) throws SQLException {
        String flightId = "2 OR '' = ''";
        var result = getTicketsByFlightId(flightId);
        System.out.println(result);
    }

    private static List<Long> getTicketsByFlightId(String flightId) throws SQLException {
        String sql = """
                SELECT id
                FROM ticket
                WHERE flight_id = %s
                """.formatted(flightId);
        List<Long> result = new ArrayList<>();
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
//                result.add(resultSet.getLong("id"));
                result.add(resultSet.getObject("id", Long.class)); // NULL safe
            }
        }

        return result;
    }














}
