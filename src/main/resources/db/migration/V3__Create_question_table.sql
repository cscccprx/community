CREATE TABLE `community`.`question`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `title` varchar(50),
  `description` text,
  `gmt_create` bigint(255),
  `gmt_modified` bigint(255),
  `creator` int(255),
  `comment_count` int(255) default 0,
  `view_count` int(255) default 0,
  `like_count` int(255) default 0,
  `tag` varchar(256),
  PRIMARY KEY (`id`)
);