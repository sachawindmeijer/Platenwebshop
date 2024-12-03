INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
SELECT * FROM authorities;
INSERT INTO authorities (username, authority)
VALUES  ('Klaas', 'ROLE_ADMIN'),
        ('Marie', 'ROLE_USER'),
        ('Sam', 'ROLE_USER');

INSERT INTO users (username, password, email, enabled)
VALUES  ('Klaas', '$2a$10$/VA5LkLktaAR1r/E.delUe/L09Zd3p38rEBB4E110iEkjR2ALEQxq','klaas@test.nl', TRUE),
        ('Marie', '$2a$10$vR7NOB0ewdxFUW.bikAa5ukBt85WQ6DFwu3ATRurQrsNFTHZ72jtu', 'marie@test.nl', TRUE),
        ('Sam', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'sam@test.nl', TRUE);

INSERT INTO Lp_Product (id, artist, album, description, genre, in_Stock, price_incl_vat, price_ecl_vat)
VALUES
    (3, 'St. Vincent', 'Masseduction', 'Electropop album by a icon',  'ROCK', 50, 30.00, 25.00),
    (4, 'Miles Davis', 'Kind of Blue', 'A jazz masterpiece by Miles Davis', 'JAZZ', 20, 40.00, 33.00),
    (5, 'Christine and the Queens', 'Chaleur Humaine', 'A bold statement from a queer French artist', 'POP', 20, 28.00, 23.00),
    (6, 'Daft Punk', 'Random Access Memories', 'An electronic music album by Daft Punk', 'POP', 60, 32.00, 27.00);

INSERT INTO customer_order (username, order_date , shipping_cost, shipping_adress)
VALUES  ('Klaas', '2024-10-25', 0, 'Kerkstraat 12, 1017 GP, Amsterdam, Nederland' ),
        ('Marie', '2024-10-30', 6.85, 'Havenweg 5, 3511 CA, Utrecht, Nederland' ),
        ('Sam', '2024-10-28', 6.85, 'Dorpsstraat 22, 5611 LC, Eindhoven, Nederland' );

INSERT INTO order_items (order_id, lpproduct_id, quantity)
VALUES
    (1, 3, 2),
    (2, 6, 1),
    (3, 6, 1);

INSERT INTO Invoice (order_id, invoice_number, date, payment_status, delivery_status, invoice_date, total_cost, total_amount_excl_vat)
VALUES
    (1, 'INV10001', '2024-10-29', 0, 'PENDING', '2024-10-29', 60.00, 50.00),
    (2, 'INV10002', '2024-10-29', 1, 'SHIPPED', '2024-10-29', 38.85, 27.00),
    (3, 'INV10003', '2024-10-29', 1, 'PROCESSING', '2024-10-29', 38.85, 27.00);

INSERT INTO report (comment, rapport_datum, total_revenue)
VALUES
        ('Monthly Sales Report', CURRENT_DATE, 0.0),
        ('Weekly Sales Summary', CURRENT_DATE, 0.0),
        ('Annual Performance Review', CURRENT_DATE, 0.0);

INSERT INTO report_top_selling_products (report_id, lpproduct_id)
VALUES
    (1, 3),
    (2, 4);


INSERT INTO report_low_selling_products (report_id, lp_product_id)
VALUES
    (1, 6),
    (3, 5);
