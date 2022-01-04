CREATE EVENT IF NOT EXISTS `token_cleaner`
    ON SCHEDULE
        EVERY 5 MINUTE
    COMMENT 'Cleans token blacklist table from old tokens'
    DO
        DELETE
        FROM `auth_db`.`token_blacklist`
        WHERE `expiration` <= CURRENT_TIMESTAMP;