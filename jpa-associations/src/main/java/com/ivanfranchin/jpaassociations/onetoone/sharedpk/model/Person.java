package com.ivanfranchin.jpaassociations.onetoone.sharedpk.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
