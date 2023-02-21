package com.ivanfranchin.producer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "OpeningHoursJavaTimeZone")
public class OpeningHourJavaTimeZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;

    private ZonedDateTime dateTimeBegin;
    private ZonedDateTime dateTimeEnd;
}
