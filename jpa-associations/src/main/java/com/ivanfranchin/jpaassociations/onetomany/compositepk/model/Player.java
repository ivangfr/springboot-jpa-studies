package com.ivanfranchin.jpaassociations.onetomany.compositepk.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
