package Karim;

import java.util.ArrayList;
import java.util.Random;

public class GA_Problem_Parameter {
	static int Num_of_variables;
	static int Num_of_functions;
	static int Num_of_Active_Developers;
	static int Num_of_Bugs;
	static int Num_of_Zones;
	//set GA parameters
	static int population;
	static double sbx_rate;
	static double sbx_distribution_index;
	static double pm_rate;
	static double pm_distribution_index;
	
	public static final int startDevId=1;
	public static ArrayList<Integer> DevList=new ArrayList<Integer>();
	
	public void setNum_of_Variables(){
		Num_of_variables=Num_of_Bugs*Num_of_Zones;
	}
	
	public static int getRandomDevId(){
		Random rg=new Random();
		int index=rg.nextInt(DevList.size());
		return DevList.get(index);
	}
	
	public static int getDevId(){
		if(DevList.size()>0){
			Random rg=new Random();
			int index=rg.nextInt(DevList.size());
			int devId=DevList.get(index);
			DevList.remove(index);
			return devId;
		}
		else{
			return -1;
		}
		
	}

}
