package com.ivanfranchin.producer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
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
