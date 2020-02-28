ALTER TABLE `community`.`comment`
ADD COLUMN `comment_count` Int default 0 AFTER `like_count`;