package Karim;

import java.util.Map.Entry;

public class fitnessCalc {


public static double totalTime(Bug bug, Entry<Zone, Double> zone,
		Developer developer) {
	//set startTime
	for(int j=0;j<bug.DB.size();j++){
		if(bug.endTime>bug.startTime)
			bug.startTime=bug.DB.get(j).endTime;
	}
	
	//compute total time for competency 
	double tct=bug.getTotalEstimatedEffort()*bug.BZone_Coefficient.get(zone.getKey())/(developer.getDZone_Coefficient().get(zone.getKey()));
	return tct+bug.startTime;
}


 public static double getSimDev(Developer d1, Developer d2){
	 double DDSim=0.0;
	 for (Entry<Zone, Double>  zone:d1.DZone_Coefficient.entrySet())
		 DDSim+=Math.abs(Double.parseDouble(zone.getValue().toString())+d2.getDZone_Coefficient().get(zone.getKey()));
	 
	 return DDSim;
 }
 
 public static double getSimBug(Developer d1,Bug b2){
	 double DBSim=0.0;
	 for (Entry<Zone, Double>  zone:b2.BZone_Coefficient.entrySet())
		 DBSim+=Math.abs(Double.parseDouble(zone.getValue().toString())+d1.DZone_Coefficient.get(zone.getKey()));

	 return DBSim;
 }

}

