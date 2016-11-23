DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id      INT(8) NOT NULL AUTO_INCREMENT,
  name    VARCHAR(30) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE orders (
  id      INT(8) NOT NULL AUTO_INCREMENT,
  name    VARCHAR(50) NOT NULL,
  user_id INT(8) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE order_items (
  id       INT(8) NOT NULL AUTO_INCREMENT,
  name     VARCHAR(50) NOT NULL,
  order_id INT(8) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

