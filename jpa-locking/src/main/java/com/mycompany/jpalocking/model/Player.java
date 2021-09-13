package com.mycompany.jpalocking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@ToString(exclude = {"lives", "stars"})
@EqualsAndHashCode(exclude = {"lives", "stars"})
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<StarCollection> stars = new LinkedHashSet<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<Life> lives = new LinkedHashSet<>();

    public void addStarCollection(StarCollection starCollection) {
        starCollection.setPlayer(this);
        stars.add(starCollection);
    }

    public void addLife(Life life) {
        life.setPlayer(this);
        lives.add(life);
    }

    @Column(nullable = false)
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    private ZonedDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        createdAt = updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = ZonedDateTime.now();
    }
}
