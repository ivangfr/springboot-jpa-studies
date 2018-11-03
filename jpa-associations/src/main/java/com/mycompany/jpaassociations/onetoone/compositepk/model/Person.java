package com.mycompany.jpaassociations.onetoone.compositepk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@ToString(exclude = "personDetail")
@EqualsAndHashCode(exclude = "personDetail")
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(mappedBy = "person")
    private PersonDetail personDetail;

    @Column(nullable = false)
    private String name;

    public void addPersonDetail(PersonDetail personDetail) {
        this.personDetail = personDetail;
        personDetail.setPerson(this);
    }

}
