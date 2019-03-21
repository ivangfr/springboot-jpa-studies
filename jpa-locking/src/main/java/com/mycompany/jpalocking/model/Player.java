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
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@ToString(exclude = "lives")
@EqualsAndHashCode(exclude = "lives")
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private int numStars = 0;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private Set<Life> lives = new LinkedHashSet<>();

    @Version
    private Long version;

    public void addLife(Life life) {
        life.setPlayer(this);
        lives.add(life);
    }

}
