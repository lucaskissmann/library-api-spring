DELETE FROM renters;

INSERT INTO renters( id, birth_date, cpf, email, gender, name, phone )
VALUES (
    1,
    CURRENT_DATE,
    '253833600773',
    'lk@mail.com',
    'MASCULINO',
    'Lucas Kissmann',
    '51 998753332'
);