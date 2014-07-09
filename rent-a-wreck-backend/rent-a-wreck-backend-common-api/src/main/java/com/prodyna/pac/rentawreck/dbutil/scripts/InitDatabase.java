package com.prodyna.pac.rentawreck.dbutil.scripts;

public interface InitDatabase {

	public String[] INIT_DATABASE = new String[] {
			// "DELETE FROM `raw_charters`",
			// "DELETE FROM `raw_licenses`",
			// "DELETE FROM `raw_pilots`",
			// "DELETE FROM `raw_aircraft`",

			"DELETE FROM `raw_users_roles`;", 
			"DELETE FROM `raw_roles`;",
			"DELETE FROM `raw_users`;",
			
			"INSERT INTO `raw_roles` (`uuid`, `name`) VALUES ('a0a333e1-660e-4942-aaf2-292879d8a6b0', 'user');",
			"INSERT INTO `raw_roles` (`uuid`, `name`) VALUES ('45653253-a52d-4fac-92ac-05196039dfa2', 'admin');",
			
			"INSERT INTO `raw_users` (`uuid`, `email`, `first_name`, `last_name`, `password`, `username`) VALUES ('45653253-a52d-4fac-92ac-05196039dfa1', 'admin@rent-a-wreck.com', 'Rent-A-Wreck', 'Administrator', '21232f297a57a5a743894a0e4a801fc3', 'admin');",
			
			"INSERT INTO `raw_users_roles` (`user_uuid`, `role_uuid`) VALUES ('45653253-a52d-4fac-92ac-05196039dfa1', '45653253-a52d-4fac-92ac-05196039dfa2');"

	};

}