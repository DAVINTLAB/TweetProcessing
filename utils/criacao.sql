
CREATE DATABASE `databaseName` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `databaseName`;

CREATE TABLE `entities` (
  `id_entities` bigint(20) NOT NULL AUTO_INCREMENT,
  `hashtags` text,
  `lula2017_id_str` text,
  PRIMARY KEY (`id_entities`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
CREATE TABLE `places` (
  `id_place` char(50) NOT NULL,
  `country` text,
  `country_code` text,
  `full_name` text,
  `name_place` text,
  `coordinates` text,
  PRIMARY KEY (`id_place`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `users` (
  `id_str` bigint(20) NOT NULL,
  `screen_name` text,
  `followers_count` int(11) DEFAULT NULL,
  `friends_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_str`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `tweet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_str` varchar(100) CHARACTER SET utf8mb4 NOT NULL,
  `text` text CHARACTER SET utf8mb4,
  `created_at` text,
  `favorite_count` int(11) DEFAULT NULL,
  `lang` varchar(10) DEFAULT NULL,
  `retweeted` tinyint(1) DEFAULT NULL,
  `retweet_count` int(11) DEFAULT NULL,
  `users_id_str` bigint(20) NOT NULL,
  `places_id_place` char(50) DEFAULT NULL,
  `tweet_original` varchar(100) DEFAULT NULL,
  `created_at_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_str_UNIQUE` (`id_str`),
  KEY `fk_tweet_places_idx` (`places_id_place`),
  KEY `fk_tweet_users_idx` (`users_id_str`),
  KEY `created_at_datetime` (`created_at_datetime`),
  KEY `users_id_str` (`users_id_str`),
  CONSTRAINT `fk_tweet_places` FOREIGN KEY (`places_id_place`) REFERENCES `places` (`id_place`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tweet_users` FOREIGN KEY (`users_id_str`) REFERENCES `users` (`id_str`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;