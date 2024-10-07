INSERT INTO app_user (username, password, enabled, account_non_expired, credentials_non_expired, account_non_locked)
VALUES
    ('user1', 'password1', TRUE, TRUE, TRUE, TRUE),
    ('user2', 'password2', TRUE, TRUE, TRUE, TRUE);

INSERT INTO task (name, description, status, duration, user_id, create_date, last_update_date)
VALUES
    ('Task 1', 'Description for Task 1', 'OPEN', 30, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Task 2', 'Description for Task 2', 'IN_PROGRESS', 60, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Task 3', 'Description for Task 3', 'CLOSED', 45, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
