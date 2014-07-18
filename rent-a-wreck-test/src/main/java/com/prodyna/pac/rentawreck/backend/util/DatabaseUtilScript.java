package com.prodyna.pac.rentawreck.backend.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseUtilScript implements Serializable {

	private static final long serialVersionUID = 5334816308984311762L;
	
	private List<String> sqlStatements = new ArrayList<String>();
	
	public DatabaseUtilScript() {
	}
	
    public List<String> getSqlStatements() {
        return sqlStatements;
    }
    
	public void addSqlStatements(String... sqlStatements) {
		this.sqlStatements.addAll(Arrays.asList(sqlStatements));
	}
}
