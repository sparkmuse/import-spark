
CREATE TABLE IF NOT EXISTS `deletions`
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    `creationDateTime` timestamp DEFAULT NULL,
    `creator`          varchar(250),
    `deletionDateTime` timestamp DEFAULT NULL,
    `deletor`          varchar(250),
    `subject`          varchar(250),
    `predicate`        varchar(250),
    `object`           varchar(250),
    `languageCode`     varchar(250)
) ENGINE=InnoDB;

TRUNCATE TABLE `deletions`;