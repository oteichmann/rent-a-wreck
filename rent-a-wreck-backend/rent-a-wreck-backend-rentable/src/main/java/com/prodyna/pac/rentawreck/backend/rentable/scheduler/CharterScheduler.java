package com.prodyna.pac.rentawreck.backend.rentable.scheduler;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

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
	private Logger log;
	
	@Inject
	private CharterService charterService;
  
    @Schedule( minute="*/5", persistent=false)
    public void doWork(){
        log.severe( "Hi from charter scheduler!" );
        
        List<Charter> overdueCharters = charterService.getOverdueCharters();
        for (Charter charter : overdueCharters) {
        	charter.setCharterStatus(CharterStatus.RETURNED);
        	charterService.update(charter);
		}
    }
    
}