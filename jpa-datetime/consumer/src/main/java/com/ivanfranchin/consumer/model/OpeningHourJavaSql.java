package com.ivanfranchin.consumer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
@Entity
@Table(name = "OpeningHoursJavaSql")
public class OpeningHourJavaSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private Time begin;
    private Time end;

    private java.util.Date dateTimeBegin;
    private java.util.Date dateTimeEnd;
}
