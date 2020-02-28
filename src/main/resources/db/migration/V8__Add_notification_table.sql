CREATE TABLE `community`.`notification`  (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `notifier` bigint(255) NOT NULL,
  `receiver` bigint(255) NOT NULL,
  `outerId` bigint(255) NOT NULL,
  `type` int(255) NOT NULL,
  `gmt_create` bigint(255) NOT NULL,
  `status` int(255) DEFAULT 0 NOT NULL,
  PRIMARY KEY (`id`)
);