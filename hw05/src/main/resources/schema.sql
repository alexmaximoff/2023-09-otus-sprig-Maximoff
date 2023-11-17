create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);

create or replace view v_books as
    select
        books.id          as book_id,
        books.title       as book_title,
        books.author_id   as author_id,
        authors.full_name as author_full_name,
        books.genre_id    as genre_id,
        genres.name       as genre_name
    from
        books
    left join
        authors
    on
        authors.id = books.author_id
    left join
        genres
    on
        genres.id = books.genre_id;