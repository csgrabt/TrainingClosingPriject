CREATE TABLE `zipcodes` (
	`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`zip` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`city` VARCHAR(255) NOT NULL COLLATE 'utf8_general_ci',
	`district` VARCHAR(255) DEFAULT 'none' COLLATE 'utf8_general_ci',
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0
;