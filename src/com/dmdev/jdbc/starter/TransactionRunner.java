package com.dmdev.jdbc.starter;

import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionRunner {

    public static void main(String[] args) throws SQLException {
        long flightId = 8;
        var deleteFlightSql = "DELETE FROM flight WHERE id = " + flightId;
        var deleteTicketsSql = "DELETE FROM ticket WHERE flight_id = " + flightId;
        var createTableSql = "CREATE TABLE test4 (id SERIAL PRIMARY KEY);";
        Connection connection = null;
        Statement statement = null;
        try {
            connection = ConnectionManager.open();
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            statement.addBatch(deleteTicketsSql);
            statement.addBatch(deleteFlightSql);
            statement.addBatch(createTableSql);

            var ints = statement.executeBatch();

            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
}
