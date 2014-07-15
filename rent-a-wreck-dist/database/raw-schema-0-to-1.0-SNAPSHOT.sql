-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               5.5.32 - MySQL Community Server (GPL)
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Exportiere Datenbank Struktur für rent_a_wreck
CREATE DATABASE IF NOT EXISTS `rent_a_wreck` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `rent_a_wreck`;


-- Exportiere Struktur von Tabelle rent_a_wreck.raw_aircrafts
CREATE TABLE IF NOT EXISTS `raw_aircrafts` (
  `uuid` varchar(255) NOT NULL,
  `id` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt


-- Exportiere Struktur von Tabelle rent_a_wreck.raw_charters
CREATE TABLE IF NOT EXISTS `raw_charters` (
  `uuid` varchar(255) NOT NULL,
  `charter_end` datetime NOT NULL,
  `charter_start` datetime NOT NULL,
  `charter_status` varchar(255) NOT NULL,
  `aircraft` varchar(255) NOT NULL,
  `pilot` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_smkg52un8uqsdtqdd1f0kve46` (`aircraft`),
  KEY `FK_bax4tks064x9tgyt96omqjm9a` (`pilot`),
  CONSTRAINT `FK_bax4tks064x9tgyt96omqjm9a` FOREIGN KEY (`pilot`) REFERENCES `raw_pilots` (`uuid`),
  CONSTRAINT `FK_smkg52un8uqsdtqdd1f0kve46` FOREIGN KEY (`aircraft`) REFERENCES `raw_aircrafts` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt


-- Exportiere Struktur von Tabelle rent_a_wreck.raw_licenses
CREATE TABLE IF NOT EXISTS `raw_licenses` (
  `uuid` varchar(255) NOT NULL,
  `aircraft_type` int(11) NOT NULL,
  `valid_till` datetime NOT NULL,
  `pilot_uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uuid`),
  KEY `FK_ebixi1pv35p1hb2321gwefm8k` (`pilot_uuid`),
  CONSTRAINT `FK_ebixi1pv35p1hb2321gwefm8k` FOREIGN KEY (`pilot_uuid`) REFERENCES `raw_pilots` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt


-- Exportiere Struktur von Tabelle rent_a_wreck.raw_pilots
CREATE TABLE IF NOT EXISTS `raw_pilots` (
  `uuid` varchar(255) NOT NULL,
  `user_uuid` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UK_jgv3o8ye7grdo06tvedpmeh0r` (`user_uuid`),
  CONSTRAINT `FK_jgv3o8ye7grdo06tvedpmeh0r` FOREIGN KEY (`user_uuid`) REFERENCES `raw_users` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt


-- Exportiere Struktur von Tabelle rent_a_wreck.raw_roles
CREATE TABLE IF NOT EXISTS `raw_roles` (
  `uuid` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt


-- Exportiere Struktur von Tabelle rent_a_wreck.raw_users
CREATE TABLE IF NOT EXISTS `raw_users` (
  `uuid` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE KEY `UK_rdhh15yc4i8ts51uxgkgfqt1r` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt


-- Exportiere Struktur von Tabelle rent_a_wreck.raw_users_roles
CREATE TABLE IF NOT EXISTS `raw_users_roles` (
  `user_uuid` varchar(255) NOT NULL,
  `role_uuid` varchar(255) NOT NULL,
  KEY `FK_fvoe3xtinc6nh4t4stap9pd1x` (`role_uuid`),
  KEY `FK_iulvx3nx8d0f8ou168nmmq19n` (`user_uuid`),
  CONSTRAINT `FK_fvoe3xtinc6nh4t4stap9pd1x` FOREIGN KEY (`role_uuid`) REFERENCES `raw_roles` (`uuid`),
  CONSTRAINT `FK_iulvx3nx8d0f8ou168nmmq19n` FOREIGN KEY (`user_uuid`) REFERENCES `raw_users` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
