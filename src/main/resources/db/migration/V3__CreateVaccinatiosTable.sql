CREATE TABLE `vaccinations` (
	`vaccination_id` BIGINT(20) NOT NULL AUTO_INCREMENT,
	`citizen_id` BIGINT(20) NOT NULL,
	`vaccination_date` DATETIME NOT NULL,
	`status` VARCHAR(10) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`note` VARCHAR(200) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`vaccination_type` VARCHAR(20) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	PRIMARY KEY (`vaccination_id`) USING BTREE,
	INDEX `citizen_id` (`citizen_id`) USING BTREE,
	CONSTRAINT `vaccinations_ibfk_1` FOREIGN KEY (`citizen_id`) REFERENCES `closingproject`.`citizens` (`citizen_id`) ON UPDATE RESTRICT ON DELETE RESTRICT
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=0
;
