package com.mycompany.jpalocking.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Data
@ToString(exclude = "player")
@EqualsAndHashCode(exclude = "player")
@Entity
@Table(name = "star_collections")
public class StarCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(nullable = false)
    private Integer numCollected;

    @Column(nullable = false)
    private Integer numAvailable;

    @Version
    private Long version;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onPrePersist() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
