ALTER TABLE `community`.`comment`
ADD COLUMN `content` varchar(1024) AFTER `like_count`;