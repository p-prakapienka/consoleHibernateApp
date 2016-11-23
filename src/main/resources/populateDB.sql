DELETE FROM order_items;
DELETE FROM orders;
DELETE FROM users;

INSERT INTO users (id, name) VALUES (100, 'Test user1');
INSERT INTO users (id, name) VALUES (101, 'Test user2');

INSERT INTO orders (id, name, user_id) VALUES (1000, 'User1 order1', 100);
INSERT INTO orders (id, name, user_id) VALUES (1001, 'User1 order2', 100);
INSERT INTO orders (id, name, user_id) VALUES (1002, 'User2 order3', 101);
INSERT INTO orders (id, name, user_id) VALUES (1003, 'User2 order4', 101);

INSERT INTO order_items (id, name, order_id) VALUES (10000, 'Item1 - Order1 - User1', 1000);
INSERT INTO order_items (id, name, order_id) VALUES (10001, 'Item2 - Order1 - User1', 1000);
INSERT INTO order_items (id, name, order_id) VALUES (10002, 'Item3 - Order2 - User1', 1001);
INSERT INTO order_items (id, name, order_id) VALUES (10003, 'Item4 - Order2 - User1', 1001);
INSERT INTO order_items (id, name, order_id) VALUES (10004, 'Item5 - Order3 - User2', 1002);
INSERT INTO order_items (id, name, order_id) VALUES (10005, 'Item6 - Order3 - User2', 1002);
INSERT INTO order_items (id, name, order_id) VALUES (10006, 'Item7 - Order4 - User2', 1003);
INSERT INTO order_items (id, name, order_id) VALUES (10007, 'Item8 - Order4 - User2', 1003);