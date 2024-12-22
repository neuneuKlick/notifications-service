CREATE TABLE IF NOT EXISTS notification
(
    id                  UUID                                    PRIMARY KEY NOT NULL,
    author_id           UUID                                    NOT NULL ,
    content             VARCHAR(255),
    notification_type   VARCHAR(50)                             NOT NULL ,
    sent_time           TIMESTAMP WITH TIME ZONE,
    receiver_id         UUID,
    service_type        VARCHAR(50),
    event_id            UUID,
    is_read             BOOLEAN
);

CREATE TABLE IF NOT EXISTS notification_setting
(
    id                          UUID            PRIMARY KEY NOT NULL,
    enable_post                 BOOLEAN,
    enable_post_comment         BOOLEAN,
    enable_comment_comment      BOOLEAN,
    enable_friend_request       BOOLEAN,
    enable_friend_birthday      BOOLEAN,
    enable_message              BOOLEAN,
    enable_send_email_message   BOOLEAN,
    author_id                   UUID            NOT NULL
);
