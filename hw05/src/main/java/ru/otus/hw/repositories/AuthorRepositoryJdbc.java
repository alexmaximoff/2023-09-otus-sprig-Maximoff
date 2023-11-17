package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJdbc implements AuthorRepository {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorRepositoryJdbc(NamedParameterJdbcOperations jdbc) {this.jdbc = jdbc;}

    @Override
    public List<Author> findAll() {
        return jdbc.query("select id, full_name from authors", new AuthorRowMapper());
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.of(jdbc.queryForObject("select id, full_name from authors where id = :id", new MapSqlParameterSource("id", id), new AuthorRowMapper()));
    }

    private static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int i) throws SQLException {
            long id = rs.getLong("id");
            String fullName = rs.getString("full_name");
            return new Author(id, fullName);
        }
    }
}
