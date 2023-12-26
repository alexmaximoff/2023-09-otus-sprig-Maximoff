package ru.otus.hw.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(orphanRemoval = false)
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
