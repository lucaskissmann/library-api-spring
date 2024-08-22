DELETE FROM renters;

INSERT INTO renters( id, birth_date, cpf, email, gender, name, phone )
VALUES (
    1,
    CURRENT_DATE,
    '03746589947',
    'bld@mail.com',
    'FEMININO',
    'Bruna Luiza',
    '51999999899'
)