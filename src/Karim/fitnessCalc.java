package Karim;

import java.util.Map;
import java.util.Map.Entry;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class fitnessCalc {


public static double totalTime(Bug bug, Entry<Zone, Double> zone,
		Developer developer) {
	//set startTime
	
	for(int j=0;j<bug.DB.size();j++){
		if(bug.endTime>bug.startTime)
			bug.startTime=bug.DB.get(j).endTime;
	}
	//compute total time for competency 
	double tct=bug.getTotalEstimatedEffort()*bug.BZone_Coefficient.get(zone.getKey())/((developer.getDZone_Coefficient().get(zone.getKey()))+1);
	return tct+bug.startTime;
}


 public static double getSimDev(Developer d1, Developer d2){
	 double DDSim_intersection=0.0;
	 double DDSim_union=0.0;
	 for (Entry<Zone, Double>  zone:d1.DZone_Coefficient.entrySet()){
		 DDSim_intersection+=Math.min(Double.parseDouble(zone.getValue().toString()),d2.getDZone_Coefficient().get(zone.getKey()));
		 DDSim_union+=Math.min(Double.parseDouble(zone.getValue().toString()),d2.getDZone_Coefficient().get(zone.getKey()));
	 }
		 
	 return 1/(DDSim_intersection/DDSim_union);
 }
 
 public static double getSimBug(Developer d1,Bug b2, Zone z1){
	 double DBSim=0.0;
	 //for (Entry<Zone, Double>  zone:b2.BZone_Coefficient.entrySet())
	 DBSim+=Math.min(b2.BZone_Coefficient.get(z1),d1.DZone_Coefficient.get(z1));

	 return DBSim;
 }

 public static void setBugEndTime(Bug bug){
	 for(int j=0;j<bug.DB.size();j++){
			if(bug.endTime>bug.startTime)
				bug.startTime=bug.DB.get(j).endTime;
		}
 }
}

