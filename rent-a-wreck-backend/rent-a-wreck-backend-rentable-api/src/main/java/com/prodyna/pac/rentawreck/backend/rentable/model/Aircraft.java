package com.prodyna.pac.rentawreck.backend.rentable.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author oteichmann
 * 
 */
@Entity
@Table(name = "raw_aircrafts")
public class Aircraft extends Rentable {

	private static final long serialVersionUID = 1L;

}
