package com.mycompany.jpaassociations.onetoone.compositepk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@ToString(exclude = "person")
@EqualsAndHashCode(exclude = "person")
@Entity
@Table(name = "person_details")
@IdClass(PersonDetailId.class)
public class PersonDetail {

    @Id
    @GeneratedValue
    private Long id;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    private String description;

}
