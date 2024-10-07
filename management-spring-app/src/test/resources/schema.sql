CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,
                          username VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          enabled BOOLEAN NOT NULL DEFAULT TRUE,
                          account_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
                          credentials_non_expired BOOLEAN NOT NULL DEFAULT TRUE,
                          account_non_locked BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE task (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL,
                      description TEXT,
                      status VARCHAR(50),
                      duration INTEGER,
                      user_id BIGINT,
                      create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      last_update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES app_user(id) ON DELETE CASCADE
);
