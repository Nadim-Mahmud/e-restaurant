CREATE TABLE restaurant.user
(
    id           INT                NOT NULL PRIMARY KEY,
    first_name   VARCHAR(50),
    last_name    VARCHAR(50),
    joining_date DATE,
    date_of_birth DATE,
    email        VARCHAR(50) UNIQUE NOT NULL,
    password     VARCHAR(50)        NOT NULL,
    type         VARCHAR(50),
    created_at   DATE,
    updated_at   DATE
);

CREATE TABLE restaurant.user_seq(
    next_val BIGINT DEFAULT NULL
);

CREATE TABLE restaurant.restaurant_table
(
    id         INT NOT NULL PRIMARY KEY,
    name       VARCHAR(50),
    user_id    INT,
    created_at DATE,
    updated_at DATE,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE restaurant.restaurant_table_seq(
    next_val BIGINT DEFAULT NULL
);

CREATE TABLE restaurant.category
(
    id         INT NOT NULL PRIMARY KEY,
    name       VARCHAR(50),
    created_at DATE,
    updated_at DATE
);

CREATE TABLE restaurant.category_seq(
    next_val BIGINT DEFAULT NULL
);

CREATE TABLE restaurant.item
(
    id           INT NOT NULL PRIMARY KEY,
    name         VARCHAR(50),
    description  VARCHAR(3000),
    price        DOUBLE,
    availability VARCHAR(50),
    category_id  INT,
    created_at   DATE,
    updated_at   DATE,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE restaurant.item_seq(
    next_val BIGINT DEFAULT NULL
);

CREATE TABLE restaurant.chef_item
(
    user_id INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY (user_id, item_id)
);

CREATE TABLE restaurant.order_table
(
    id             INT NOT NULL PRIMARY KEY,
    placed_at      DATE,
    est_serve_time INT,
    status         VARCHAR(50),
    restaurant_table_id       INT,
    created_at     DATE,
    updated_at     DATE,
    FOREIGN KEY (restaurant_table_id) REFERENCES restaurant_table (id)
);

CREATE TABLE restaurant.order_table_seq(
    next_val BIGINT DEFAULT NULL
);

CREATE TABLE restaurant.order_line_item
(
    id         INT NOT NULL PRIMARY KEY,
    quantity   INT,
    status     VARCHAR(50),
    item_id    INT,
    order_id   INT,
    created_at DATE,
    updated_at DATE,
    FOREIGN KEY (item_id) REFERENCES item (id),
    FOREIGN KEY (order_id) REFERENCES order_table (id)
);

CREATE TABLE restaurant.order_line_seq(
    next_val BIGINT DEFAULT NULL
);
