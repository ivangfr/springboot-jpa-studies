package com.ivanfranchin.jpaassociations.manytomany.simplerelationship.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@ToString(exclude = "writers")
@EqualsAndHashCode(exclude = "writers")
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "books_writers",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "writer_id")
    )
    private Set<Writer> writers = new LinkedHashSet<>();

    @Column(nullable = false)
    private String name;

    public void addWriter(Writer writer) {
        this.writers.add(writer);
        writer.getBooks().add(this);
    }

    public void removeWriter(Writer writer) {
        this.writers.remove(writer);
        writer.getBooks().remove(this);
    }
}
