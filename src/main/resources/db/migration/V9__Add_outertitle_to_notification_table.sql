ALTER TABLE `community`.`notification`
ADD COLUMN `NOTIFIER_NAME` varchar(255) AFTER `status`,
ADD COLUMN `OUTER_TITLE` varchar(255) AFTER `NOTIFIER_NAME`;