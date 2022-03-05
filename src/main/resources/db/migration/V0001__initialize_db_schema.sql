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

CREATE TABLE link_stats (
    link_stats_id BIGINT NOT NULL AUTO_INCREMENT,
    short_link VARCHAR(50) NOT NULL,
    click_count BIGINT NOT NULL,
    PRIMARY KEY (link_stats_id)
);

-- insert some data

INSERT INTO user (username, secret_key) VALUES ('user1', 'secretkey1'), ('user2', 'secretkey2');
INSERT INTO link (short_link, full_link) VALUES
    ('goo', 'https://www.google.com/'),
    ('ym', 'https://market.yandex.ru/'),
    ('stack', 'https://stackoverflow.com'),
    ('sqlex', 'https://www.sql-ex.ru/')
;
INSERT INTO link_stats (short_link, click_count) VALUES
    ('goo', 5),
    ('ym', 90),
    ('stack', 56),
    ('sqlex', 6)
;
