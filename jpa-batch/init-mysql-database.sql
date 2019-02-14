DROP TABLE IF EXISTS `hibernate_sequence`;
DROP TABLE IF EXISTS `partners`;
DROP TABLE IF EXISTS `voucher_codes`;

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `partners` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `voucher_codes` (
  `id` bigint(20) NOT NULL,
  `code` varchar(255) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `partner_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PARTNER` (`partner_id`),
  CONSTRAINT `FK_PARTNER` FOREIGN KEY (`partner_id`) REFERENCES `partners` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO hibernate_sequence VALUES (0);