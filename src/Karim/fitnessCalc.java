package Karim;

import java.util.Map.Entry;

public class fitnessCalc {


public static double totalTime(Bug bug, Entry<Zone, Double> zone,
		Developer developer) {
	//set startTime
	for(int j=0;j<bug.DB.length;j++){
		if(bug.DB[j].endTime>bug.startTime)
			bug.startTime=bug.DB[j].endTime;
	}
	
	//compute total time for competency 
	double tct=bug.getTotalEstimatedEffort()*bug.BZone_Coefficient.get(zone.getKey())/(developer.getDZone_Coefficient().get(zone.getKey()));
	return tct+bug.startTime;
}
}
