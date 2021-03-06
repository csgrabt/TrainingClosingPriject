CREATE TABLE `citizens` (
	`citizen_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`citizen_name` VARCHAR(200) NOT NULL COLLATE 'utf8_general_ci',
	`zip` CHAR(4) NOT NULL COLLATE 'utf8_general_ci',
	`age` BIGINT(20) NOT NULL,
	`email` VARCHAR(200) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`taj` VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`number_of_vaccination` BIGINT(20) DEFAULT 0,
	`last_vaccination` DATETIME NULL DEFAULT NULL,
	PRIMARY KEY (`citizen_id`) USING BTREE,
	UNIQUE INDEX `taj` (`taj`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0
;