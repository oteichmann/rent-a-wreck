package com.prodyna.pac.rentawreck.backend.common.util;

import java.io.Serializable;
import java.util.List;

public class DatabaseUtilScript implements Serializable {

	private static final long serialVersionUID = 5334816308984311762L;
	
	private List<String> sqlStatements;
	
	public DatabaseUtilScript() {
	}
	
    public List<String> getSqlStatements() {
        return sqlStatements;
    }
    
	public void setSqlStatements(List<String> sqlStatements) {
		this.sqlStatements = sqlStatements;
	}
}
