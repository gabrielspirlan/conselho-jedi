CREATE TABLE jedis (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    midichlorians BIGINT NOT NULL,
    mentor_id BIGINT,
    CONSTRAINT fk_mentor FOREIGN KEY (mentor_id) REFERENCES jedis(id)
);

INSERT INTO jedis (name, status, midichlorians) VALUES ('Yoda', 'Mestre Jedi', 17000);
INSERT INTO jedis (name, status, midichlorians, mentor_id) VALUES ('Luke Skywalker', 'Padawan', 15000, 1);
INSERT INTO jedis (name, status, midichlorians, mentor_id) VALUES ('Anakin Skywalker', 'Jedi', 20000, 1);
