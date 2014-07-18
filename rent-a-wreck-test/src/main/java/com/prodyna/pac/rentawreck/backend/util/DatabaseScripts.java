package com.prodyna.pac.rentawreck.backend.util;

public interface DatabaseScripts {

	public String[] CREATE_AIRCRAFTS = new String[] {
			"INSERT INTO `raw_aircrafts` (`uuid`, `id`, `type`) VALUES ('7f3fb3ee-0495-49d6-a9fd-3e6825ef6bd4', 'MTK-P 123', 'AIRBUS');",
			"INSERT INTO `raw_aircrafts` (`uuid`, `id`, `type`) VALUES ('946b2c7d-fc21-4926-a9a3-ccedc1cc63b4', 'MTK-P 234', 'BOEING');",
			"INSERT INTO `raw_aircrafts` (`uuid`, `id`, `type`) VALUES ('f9f663f4-2719-4386-90b7-8a7fee7a04ee', 'MTK-P 345', 'CESSNA');" };
	
	public String[] CREATE_CHARTERS = new String[] {
			"INSERT INTO `raw_charters` (`uuid`, `charter_end`, `charter_start`, `charter_status`, `aircraft`, `pilot`) VALUES ('6974f404-2e3e-4d8c-ae2a-3fc479d80130', '2014-07-18 23:59:59', '2014-07-17 00:00:00', 'RESERVED', '7f3fb3ee-0495-49d6-a9fd-3e6825ef6bd4', '7b8eff6f-b779-4a97-ae1d-6609c39a3105');" };
	
	public String[] CREATE_LICENSES = new String[] {
			"INSERT INTO `raw_licenses` (`uuid`, `aircraft_type`, `valid_till`, `pilot_uuid`) VALUES ('0f26aeb6-2e9c-4669-9a59-936009b276bc', 0, '2020-12-31 23:59:59', '7bfdde40-e8d7-4b62-86d6-6e28e503a3a4');",
			"INSERT INTO `raw_licenses` (`uuid`, `aircraft_type`, `valid_till`, `pilot_uuid`) VALUES ('a6681cb5-d7ac-4931-8062-7f001a428a36', 1, '2020-12-31 23:59:59', '7b8eff6f-b779-4a97-ae1d-6609c39a3105');" };
	
	public String[] CREATE_PILOTS = new String[] {
			"INSERT INTO `raw_pilots` (`uuid`, `user_uuid`) VALUES ('7b8eff6f-b779-4a97-ae1d-6609c39a3105', '45653253-a52d-4fac-92ac-05196039dfa1');",
			"INSERT INTO `raw_pilots` (`uuid`, `user_uuid`) VALUES ('7bfdde40-e8d7-4b62-86d6-6e28e503a3a4', 'e0d145c9-328a-4fec-8190-7bfaef8dbe14');" };

	public String[] CREATE_ROLES_AND_USERS = new String[] {
			"INSERT INTO `raw_roles` (`uuid`, `name`) VALUES ('a0a333e1-660e-4942-aaf2-292879d8a6b0', 'user');",
			"INSERT INTO `raw_roles` (`uuid`, `name`) VALUES ('45653253-a52d-4fac-92ac-05196039dfa2', 'admin');",

			"INSERT INTO `raw_users` (`uuid`, `email`, `first_name`, `last_name`, `password`, `username`) VALUES ('45653253-a52d-4fac-92ac-05196039dfa1', 'admin@rent-a-wreck.com', 'Rent-A-Wreck', 'Administrator', '21232f297a57a5a743894a0e4a801fc3', 'admin');",
			"INSERT INTO `raw_users` (`uuid`, `email`, `first_name`, `last_name`, `password`, `username`) VALUES ('e0d145c9-328a-4fec-8190-7bfaef8dbe14', 'test@raw.com', 'Test', 'User', '098f6bcd4621d373cade4e832627b4f6', 'test');",

			"INSERT INTO `raw_users_roles` (`user_uuid`, `role_uuid`) VALUES ('45653253-a52d-4fac-92ac-05196039dfa1', '45653253-a52d-4fac-92ac-05196039dfa2');",
			"INSERT INTO `raw_users_roles` (`user_uuid`, `role_uuid`) VALUES ('e0d145c9-328a-4fec-8190-7bfaef8dbe14', 'a0a333e1-660e-4942-aaf2-292879d8a6b0');" };
	
	public String[] DELETE_AIRCRAFTS = new String[] { 
			"DELETE FROM `raw_aircrafts`;" };

	public String[] DELETE_CHARTERS = new String[] { 
			"DELETE FROM `raw_charters`;" };
	
	public String[] DELETE_LICENSES = new String[] { 
			"DELETE FROM `raw_licenses`;" };
	
	public String[] DELETE_PILOTS = new String[] { 
			"DELETE FROM `raw_pilots`;" };

	public String[] DELETE_ROLES_AND_USERS = new String[] { 
			"DELETE FROM `raw_users_roles`;",
			"DELETE FROM `raw_roles`;", 
			"DELETE FROM `raw_users`;" };
	
}
