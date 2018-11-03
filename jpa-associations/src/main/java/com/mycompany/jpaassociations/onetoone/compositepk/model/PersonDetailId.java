package com.mycompany.jpaassociations.onetoone.compositepk.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PersonDetailId implements Serializable {

    private Long id;
    private Long person;

}
