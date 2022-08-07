package com.ivanfranchin.jpaassociations.onetomany.compositepk.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@ToString(exclude = "weapons")
@EqualsAndHashCode(exclude = "weapons")
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Weapon> weapons = new LinkedHashSet<>();

    @Column(nullable = false)
    private String name;
}
