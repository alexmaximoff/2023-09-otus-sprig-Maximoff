package ru.otus.hw.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJdbc implements BookRepository {

    private final NamedParameterJdbcOperations jdbc;

    public BookRepositoryJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Optional<Book> findById(long id) {
        String findByIdQuery = """
                select
                    book_id,
                    book_title,
                    author_id,
                    author_full_name,
                    genre_id,
                    genre_name 
                from
                    v_books
                where
                    book_id = :id""";
        try {
            return Optional.ofNullable(jdbc.queryForObject(findByIdQuery, new MapSqlParameterSource("id", id), new BookRowMapper()));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        String findAllQuery = """
                select
                    book_id,
                    book_title,
                    author_id,
                    author_full_name,
                    genre_id,
                    genre_name 
                from
                    v_books""";
        return jdbc.query(findAllQuery, new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("delete from books where id = :id", new MapSqlParameterSource("id", id));
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        jdbc.update("insert into books(title, author_id, genre_id) values(:title, :author_id, :genre_id)",
                new MapSqlParameterSource().
                        addValue("title", book.getTitle()).
                        addValue("author_id", book.getAuthor().getId()).
                        addValue("genre_id", book.getGenre().getId()),
                keyHolder,
                new String[]{"id"}
        );

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        String updateQuery = """
                update
                    books
                set
                    title = :title,
                    author_id = :author_id,
                    genre_id = :genre_id
                where
                    id = :id""";
        jdbc.update(updateQuery,
                new MapSqlParameterSource("id", book.getId()).
                        addValue("title", book.getTitle()).
                        addValue("author_id", book.getAuthor().getId()).
                        addValue("genre_id", book.getGenre().getId())
        );
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("book_id");
            String title = rs.getString("book_title");
            Author author = new Author(rs.getLong("author_id"), rs.getString("author_full_name"));
            Genre genre = new Genre(rs.getLong("genre_id"), rs.getString("genre_name"));
            return new Book(id, title, author, genre);
        }
    }
}
