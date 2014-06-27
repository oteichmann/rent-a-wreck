package com.prodyna.pac.rentawreck.backend.rentable.scheduler;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class CharterScheduler {
  
    @Schedule(second="*", minute="1",hour="*", persistent=false)
    public void doWork(){
        System.out.println( "Hi from the EJB timer example!" );
    }
    
}