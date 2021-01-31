package com.dmdev.jdbc.starter.dao;

import com.dmdev.jdbc.starter.entity.Flight;
import com.dmdev.jdbc.starter.exception.DaoException;
import com.dmdev.jdbc.starter.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {

    private static final FlightDao INSTANCE = new FlightDao();

    private static final String FIND_BY_ID_SQL = """
            SELECT id,
                flight_no,
                departure_date,
                departure_airport_code,
                arrival_date,
                arrival_airport_code,
                status,
                aircraft_id
            FROM flight
            WHERE id = ?
            """;

    private FlightDao() {
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Flight save(Flight ticket) {
        return null;
    }

    @Override
    public void update(Flight ticket) {

    }

    @Override
    public Optional<Flight> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    public Optional<Flight> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Flight flight = null;
            if (resultSet.next()) {
                flight = new Flight(
                        resultSet.getLong("id"),
                        resultSet.getString("flight_no"),
                        resultSet.getTimestamp("departure_date").toLocalDateTime(),
                        resultSet.getString("departure_airport_code"),
                        resultSet.getTimestamp("arrival_date").toLocalDateTime(),
                        resultSet.getString("arrival_airport_code"),
                        resultSet.getInt("aircraft_id"),
                        resultSet.getString("status")
                );
            }
            return Optional.ofNullable(flight);
        } catch (SQLException throwables) {
            throw new DaoException(throwables);
        }
    }

    @Override
    public List<Flight> findAll() {
        return null;
    }
}
