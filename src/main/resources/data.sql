INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
SELECT * FROM authorities;
INSERT INTO authorities (username, authority) VALUES ('Klaas', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Marie', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('Sam', 'ROLE_USER');

INSERT INTO users (username, password, email, enabled) VALUES ('Klaas', '$2a$10$/VA5LkLktaAR1r/E.delUe/L09Zd3p38rEBB4E110iEkjR2ALEQxq','klaas@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled) VALUES ('Marie', '$2a$10$vR7NOB0ewdxFUW.bikAa5ukBt85WQ6DFwu3ATRurQrsNFTHZ72jtu', 'marie@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled) VALUES ('Sam', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'sam@test.nl', TRUE);

INSERT INTO Lp_Product (id, artist, album, description, genre, in_Stock, price_incl_vat, price_ecl_vat)
VALUES
    (1, 'St. Vincent', 'Masseduction', 'Electropop album by a icon',  'ROCK', 50, 30.00, 25.00),
    (2, 'Miles Davis', 'Kind of Blue', 'A jazz masterpiece by Miles Davis', 'JAZZ', 20, 40.00, 33.00),
    (3, 'Christine and the Queens', 'Chaleur Humaine', 'A bold statement from a queer French artist', 'POP', 20, 28.00, 23.00),
    (4, 'Daft Punk', 'Random Access Memories', 'An electronic music album by Daft Punk', 'POP', 60, 32.00, 27.00);