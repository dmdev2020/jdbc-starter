package com.dmdev.jdbc.starter.entity;

import java.time.LocalDateTime;

public record Flight(Long id,
                     String flightNo,
                     LocalDateTime departureDate,
                     String departureAirportCode,
                     LocalDateTime arrivalDate,
                     String arrivalAirportCode,
                     Integer aircraftId,
                     String status) {
}
