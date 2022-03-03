-- create tables

CREATE TABLE user (
    user_id BIGINT auto_increment NOT NULL,
    username VARCHAR(50) NOT NULL,
    secret_key VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id)
);

CREATE TABLE link (
    short_link VARCHAR(50) NOT NULL,
    full_link VARCHAR(50) NOT NULL,
    click_count BIGINT NOT NULL,
    PRIMARY KEY (short_link)
);


-- insert some data

INSERT INTO user VALUES (1, 'user1', 'secretkey1'), (2, 'user2', 'secretkey2');
INSERT INTO link VALUES ( 'goo', 'https://www.google.com/', 0), ('ym', 'https://market.yandex.ru/', 0);
