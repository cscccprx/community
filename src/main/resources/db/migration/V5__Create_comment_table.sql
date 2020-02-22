CREATE TABLE `community`.`comment`  (
  `id` bigint(255) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(255) NOT NULL,
  `type` int(255) NOT NULL,
  `commentator` bigint(255) NOT NULL,
  `gmt_create` bigint(255),
  `gmt_modified` bigint(255),
  `like_count` bigint(255) default 0,
  PRIMARY KEY (`id`)
);