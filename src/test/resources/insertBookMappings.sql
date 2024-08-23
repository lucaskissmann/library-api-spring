-- DELETE FROM books;
--
-- INSERT INTO books( id, isbn, publication_date, state, title )
-- VALUES (
--    1,
--    '9783161484100',
--    CURRENT_DATE,
--    '0',
--    '1984'
-- );
--
-- DELETE FROM authors;
--
-- INSERT INTO authors( id, gender, age, name, cpf )
-- VALUES (
--    1,
--    'MASCULINO',
--    '23',
--    'Lucas',
--    '79187561000'
-- );

DELETE FROM book_mappings;

INSERT INTO book_mappings(ref_author, ref_book)
VALUES (
   1,
   1
);