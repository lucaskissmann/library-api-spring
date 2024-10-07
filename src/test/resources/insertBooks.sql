DELETE FROM books;

INSERT INTO books( id, isbn, publication_date, state, title, category )
VALUES (
    1,
    '9783161484100',
    CURRENT_DATE,
    '0',
    '1984',
    'TERROR'
);