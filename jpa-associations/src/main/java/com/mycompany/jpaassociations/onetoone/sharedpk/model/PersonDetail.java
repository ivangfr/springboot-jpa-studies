package com.mycompany.jpaassociations.onetoone.sharedpk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@ToString(exclude = "person")
@EqualsAndHashCode(exclude = "person")
@Entity
@Table(name = "person_details")
public class PersonDetail {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Person person;

    private String description;
}
