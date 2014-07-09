package com.prodyna.pac.rentawreck.backend.rentable.scheduler;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.rentawreck.backend.rentable.model.Charter;
import com.prodyna.pac.rentawreck.backend.rentable.model.CharterStatus;
import com.prodyna.pac.rentawreck.backend.rentable.service.CharterService;

/**
 * Scheduler that automatically returns overdue charters.
 *
 * @author Oliver Teichmann
 *
 */
@Singleton
public class CharterScheduler {
	
	@Inject
	private Logger logger;
	
	@Inject
	private CharterService charterService;
  
    @Schedule( minute="*/5", persistent=false)
    public void doWork(){
        logger.debug("Hi from charter scheduler!");
        
        List<Charter> overdueCharters = charterService.getOverdueCharters();
        for (Charter charter : overdueCharters) {
        	logger.debug("Auto-Returning charter: " + charter.getUuid()); 
        	charter.setCharterStatus(CharterStatus.RETURNED);
        	charterService.update(charter);
		}
    }
    
}