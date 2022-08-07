package com.ivanfranchin.consumer.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "OpeningHoursJavaTimeLocal")
public class OpeningHourJavaTimeLocal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;

    private LocalDateTime dateTimeBegin;
    private LocalDateTime dateTimeEnd;
}
