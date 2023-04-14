package com.example.demo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
	public static boolean maintenance = false;
	@Autowired
	private Livrerepository livrerepository;

	@Scheduled(cron = "0 0 4 1 * *") //First day of the Month at 04:00:00
    public void scheduleTask_changeonemonth()
    {
		this.maintenance=true; //begining maintenance 
		
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "dd-MM-yyyy HH:mm:ss.SSS");
        
        String date_begining_maintenance = dateFormat.format(new Date());
        System.out.println("Maintenance (+1 Month) Starts at : " + date_begining_maintenance);
        
        List<Livre> all_livres = livrerepository.findAll();
        Iterator<Livre> pointeur = all_livres.iterator();
        Livre var_livre;
        
        while(pointeur.hasNext()) {
        	var_livre = pointeur.next();
        	var_livre.change_number_time_month();
        	livrerepository.save(var_livre);
        	
        }
        
        String date_end_maintenance = dateFormat.format(new Date());
        System.out.println("Maintenance (+1 Month) Ends at : " + date_end_maintenance);
        
       this.maintenance=false; //end maintenance
    }
}
