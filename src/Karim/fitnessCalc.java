package Karim;

import java.util.Map.Entry;

public class fitnessCalc {


public static double totalTime(Bug bug, Entry<Zone, Double> zone,
		Developer developer) {
	double startTime=0.0;
	for(int j=0;j<bug.DB.length;j++){
		if(bug.DB[j].endTime>startTime)
			startTime=bug.DB[j].endTime;
	}
	
	double 
	return 0;
}
}
