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

-- Exportiere Daten aus Tabelle rent_a_wreck.raw_roles: ~2 rows (ungefähr)
/*!40000 ALTER TABLE `raw_roles` DISABLE KEYS */;
INSERT IGNORE INTO `raw_roles` (`uuid`, `name`) VALUES
	('45653253-a52d-4fac-92ac-05196039dfa2', 'admin'),
	('a0a333e1-660e-4942-aaf2-292879d8a6b0', 'user');
/*!40000 ALTER TABLE `raw_roles` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle rent_a_wreck.raw_users: ~3 rows (ungefähr)
/*!40000 ALTER TABLE `raw_users` DISABLE KEYS */;
INSERT IGNORE INTO `raw_users` (`uuid`, `email`, `first_name`, `last_name`, `password`, `username`) VALUES
	('45653253-a52d-4fac-92ac-05196039dfa1', 'admin@rent-a-wreck.com', 'Rent-A-Wreck', 'Administrator', '21232f297a57a5a743894a0e4a801fc3', 'admin');
/*!40000 ALTER TABLE `raw_users` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle rent_a_wreck.raw_users_roles: ~2 rows (ungefähr)
/*!40000 ALTER TABLE `raw_users_roles` DISABLE KEYS */;
INSERT IGNORE INTO `raw_users_roles` (`user_uuid`, `role_uuid`) VALUES
	('45653253-a52d-4fac-92ac-05196039dfa1', '45653253-a52d-4fac-92ac-05196039dfa2');
/*!40000 ALTER TABLE `raw_users_roles` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
