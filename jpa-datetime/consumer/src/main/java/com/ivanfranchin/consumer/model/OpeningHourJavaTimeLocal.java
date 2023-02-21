package com.ivanfranchin.consumer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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
