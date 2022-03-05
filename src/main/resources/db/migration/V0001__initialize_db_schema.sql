-- create tables

CREATE TABLE user (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    secret_key VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE link (
    short_link VARCHAR(50) NOT NULL,
    full_link VARCHAR(50) NOT NULL,
    PRIMARY KEY (short_link)
);

CREATE TABLE clicking_log (
    clicking_log_id BIGINT NOT NULL AUTO_INCREMENT,
    short_link VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (clicking_log_id),
    FOREIGN KEY (short_link) REFERENCES link (short_link) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE
);

-- insert some data

INSERT INTO user (username, secret_key) VALUES
    ('user1', 'secretkey1'),
    ('user2', 'secretkey2'),
    ('user3', 'secretkey3'),
    ('user4', 'secretkey4')
;
INSERT INTO link (short_link, full_link) VALUES
    ('goo', 'https://www.google.com/'),
    ('ym', 'https://market.yandex.ru/'),
    ('stack', 'https://stackoverflow.com'),
    ('sqlex', 'https://www.sql-ex.ru/')
;
INSERT INTO clicking_log (short_link, user_id) VALUES
    ('goo', 1),
    ('goo', 1),
    ('goo', 2),
    ('goo', 1),
    ('goo', 2),
    ('ym', 2),
    ('stack', 2),
    ('stack', 1),
    ('sqlex', 4),
    ('sqlex', 1),
    ('sqlex', 2)
;
