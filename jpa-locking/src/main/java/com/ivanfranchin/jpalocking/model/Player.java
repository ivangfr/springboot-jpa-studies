package com.ivanfranchin.jpalocking.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Instant;
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
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void onPrePersist() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = Instant.now();
    }
}
