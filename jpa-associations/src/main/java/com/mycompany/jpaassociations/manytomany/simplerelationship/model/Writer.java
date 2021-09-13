package com.mycompany.jpaassociations.manytomany.simplerelationship.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@ToString(exclude = "books")
@EqualsAndHashCode(exclude = "books")
@Entity
@Table(name = "writers")
public class Writer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(mappedBy = "writers")
    private Set<Book> books = new LinkedHashSet<>();

    @Column(nullable = false)
    private String name;

    public void addBook(Book book) {
        this.books.add(book);
        book.getWriters().add(this);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getWriters().remove(this);
    }
}
