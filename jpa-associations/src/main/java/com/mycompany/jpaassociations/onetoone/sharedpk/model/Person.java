package com.mycompany.jpaassociations.onetoone.sharedpk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "person")
    private PersonDetail personDetail;

    @Column(nullable = false)
    private String name;

    public void addPersonDetail(PersonDetail personDetail) {
        this.personDetail = personDetail;
        personDetail.setPerson(this);
    }

    public void removePersonDetail() {
        this.personDetail.setPerson(null);
        this.personDetail = null;
    }

}
