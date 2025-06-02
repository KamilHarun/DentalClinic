package com.example.apigateway.Mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public enum DateMapper {
    DATE_MAPPER;

    public LocalDateTime toLocalDateTime(Instant instant) {
        var zone = ZoneId.systemDefault();
        var zonedDateTime = ZonedDateTime.ofInstant(instant, zone);
        return zonedDateTime.toLocalDateTime();
    }
}
