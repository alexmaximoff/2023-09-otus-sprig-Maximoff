package ru.otus.hw.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "v_books")
public class BookAuthorGenre {
    @Id
    private Long id;

    @Column(name = "book_title")
    private String title;

    @Column(name = "author_full_name")
    private String author;

    @Column(name = "genre_name")
    private String genre;
}
