-- DELETE FROM renters;
--
-- INSERT INTO renters( id, birth_date, cpf, email, gender, name, phone )
-- VALUES (
--    1,
--    CURRENT_DATE,
--    '44205815069',
--    'bld@mail.com',
--    'FEMININO',
--    'Bruna Luiza',
--    '51999999899'
-- );


INSERT INTO rentals( id, is_returned, rental_date, return_date, ref_renter)
VALUES (
   1,
   false,
   '2024-01-01',
   '2024-01-03',
   1
);