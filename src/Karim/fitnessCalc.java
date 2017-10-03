package Karim;

import java.util.Map;
import java.util.Map.Entry;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

public class fitnessCalc {


public static double totalTime(Bug bug, Entry<Zone, Double> zone,
		Developer developer) {
	//set startTime
	//System.out.println(bug.DB.size());
	
	for(int j=0;j<bug.DB.size();j++){
		if(bug.endTime>bug.startTime)
			bug.startTime=bug.DB.get(j).endTime;
	}
	
	//System.out.println(bug.BZone_Coefficient.keySet());
	//System.out.println(developer.DZone_Coefficient.keySet());
	
	/*System.out.println(developer.getDZone_Coefficient().keySet());
	Zone dZone=null, bZone=null;
	for(Map.Entry<Zone, Double> dz:developer.DZone_Coefficient.entrySet()){
	System.out.print(dz.getKey().zId+"-----");
		if(dz.getKey().zId==zone.getKey().zId){
			dZone=dz.getKey();
		}
	}
	System.out.println();
	for(Map.Entry<Zone, Double> bz:bug.BZone_Coefficient.entrySet()){
		System.out.print(bz.getKey().zId+"---");
		if(bz.getKey().zId==zone.getKey().zId){
			bZone=bz.getKey();
		}
	}*/
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
 
 public static double getSimBug(Developer d1,Bug b2, Zone z1){
	 double DBSim=0.0;
	 //for (Entry<Zone, Double>  zone:b2.BZone_Coefficient.entrySet())
	 DBSim+=Math.abs(b2.BZone_Coefficient.get(z1))*d1.DZone_Coefficient.get(z1);

	 return DBSim;
 }

}

