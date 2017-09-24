package Karim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;


public class Test1 {
	public static HashMap<Integer,Developer> developers=new HashMap<Integer,Developer>();
	static HashMap<Integer,Bug> bugs=new HashMap<Integer,Bug>();
	static Solution solution=null;
	public static void main(String[] args) throws IOException{
		int roundNum=5;
		for(int i=1;i<=roundNum;i++){
		Initialization(i);
		NondominatedPopulation[] results = null; 
		results=Assigning(results);
		solution=results[1].get(results[1].size()/2);
		writeResult(i,results);
		afterRoundUpdating(solution);
		}
	}
	
	public static void Initialization( int roundNum) throws IOException{
		HashMap<Integer , Zone> columns=new HashMap<Integer, Zone>();
		
		
		
		
		
		//initialize developers
		System.out.println("enter the developr files");
		Developer developer = null;
		Scanner sc=new Scanner(System.in);
		
		sc=new Scanner(new File(sc.nextLine()));
		int i=0;
		int j=0;
		while(sc.hasNextLine()){
			if(i==0){
				String[] items=sc.nextLine().split("\t",-1);
					for(int k=0;k<items.length;k++){
						if(j!=0){
						Zone zone=new Zone(j, items[k]);
						columns.put(j,zone);
						}
						j++;
					}
			}
			else{
				String[] items=sc.nextLine().split("\t",-1);
				for(int k=0;k<items.length;k++){
					if(j!=0){
						developer.DZone_Coefficient.put(columns.get(j), Double.parseDouble(items[k]));
						//System.out.println(columns.get(j));
						
					}
					else{
						developer=new Developer(0);
						developer.setID(i);
					}
					j++;
				}
			developers.put(developer.getID(), developer);
			}
			i++;
			j=0;
		}
		sc.close();
		/*assign GA_Problem_Parameter DevList*/
		for(Map.Entry<Integer, Developer> dev:developers.entrySet()){
			GA_Problem_Parameter.DevList.add(dev.getKey());
			System.out.println(dev.getValue().DZone_Coefficient.values());
			System.out.println(dev.getValue().DZone_Coefficient.keySet());
		}
		System.out.println(columns.values());
		
		
		
		
		
		
		
		
		
		/*generate bug objects*/
		System.out.println("enter the bugs files");
		Bug bug=null;
		sc=new Scanner(System.in);
		for(File fileEntry:new File(sc.nextLine()).listFiles() ){

			sc=new Scanner(new File(fileEntry.toURI()));
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileEntry.toURI())));
			int lines = 0;
			while (reader.readLine() != null) lines++;
				reader.close();
			int numOfBugs=lines;
			int n=0;
			 i=0;
			 j=0;
			while(sc.hasNextLine() ){
				if( n<(numOfBugs/5)*(roundNum+1) && n>=((numOfBugs/5)*roundNum)+1){
					if(i==0){

						String[] items=sc.nextLine().split("\t",-1);
							for(int k=0;k<items.length;k++){
								if(j!=0){
									Zone zone=new Zone(j, items[k]);
									columns.put(j,zone);
								}
								j++;
							}	
					}
					else{
						String[] items=sc.nextLine().split("\t",-1);
						for(int k=0;k<items.length;k++){
							if(j!=0){
								bug.BZone_Coefficient.put(columns.get(j), Double.parseDouble(items[k]));
							}
							else{
								bug=new Bug(0);
								bug.setID(j);
							}
							j++;
						}
					}
					i++;
					j=0;
					bugs.put(bug.getID(), bug);
				}
				n++;
			}
		
		/*set bug dependencies*/
		System.out.println("enter the bug dependency files");
		Scanner sc1=new Scanner(System.in);
		sc=new Scanner(new File(sc1.next()));
		String[] columns_bug=null;
		i=0;
		while(sc1.hasNextLine()){
			columns_bug=sc1.next().split(",");
			for(int k=0;k<columns_bug.length-1;k++){
				if(k==1 && columns_bug[k]!=""){
				  bugs.get(columns_bug[k-1]).DB.add(bugs.get(k));
				}
				j++;
			}
			sc1.close();
			
		}
		}
		
		
		/* set competency dependencies*/
		
		
		//initialize GA parameters
		
		GA_Problem_Parameter.Num_of_variables=bugs.size();
		for(Entry<Integer, Developer> dev:developers.entrySet())
			GA_Problem_Parameter.DevList.add(dev.getKey());
		//GA_Problem_Parameter.
		
	}
	
	//assigning to developer
	public static NondominatedPopulation[] Assigning(NondominatedPopulation[] results){
		NondominatedPopulation result_Karim=new Executor().withProblemClass(CompetenceMulti2_problem.class).withAlgorithm("NSGAII")
				.withMaxEvaluations(500).withProperty("populationSize",GA_Problem_Parameter.population)
				.withProperty("sbx.rate", GA_Problem_Parameter.sbx_rate).withProperty("sbx.distributionIndex", GA_Problem_Parameter.sbx_distribution_index)
				.withProperty("pm.rate", GA_Problem_Parameter.pm_rate).withProperty("pm.distributionIndex", GA_Problem_Parameter.pm_distribution_index)
				.run();
		results[0]=result_Karim;
		
	    NondominatedPopulation result_me=new Executor().withProblemClass(InformationDifussion.class).withAlgorithm("NSGAII")
				.withMaxEvaluations(500).withProperty("populationSize",GA_Problem_Parameter.population)
				.withProperty("sbx.rate", GA_Problem_Parameter.sbx_rate).withProperty("sbx.distributionIndex", GA_Problem_Parameter.sbx_distribution_index)
				.withProperty("pm.rate", GA_Problem_Parameter.pm_rate).withProperty("pm.distributionIndex", GA_Problem_Parameter.pm_distribution_index)
				.run();
	    results[1]=result_me;
	    
	    return results;
	    
	}
	
	public static void writeResult(int roundNum, NondominatedPopulation[] result) throws FileNotFoundException{
		//write results to CSV for each round
		PrintWriter pw=new PrintWriter(new File("solutions_Karim_round"+roundNum));
		StringBuilder sb=new StringBuilder();
		for(Solution solution:result[0]){
			sb.append(solution.getObjective(0)+","+solution.getObjective(1));
			sb.setLength(sb.length()-1);
			sb.append("\n");
		}
		pw.write(sb.toString());
		pw.close();
		
		pw=new PrintWriter(new File("solutions_Me_round"+roundNum));
		sb.setLength(0);
		for(Solution solution:result[1]){
			sb.append(solution.getObjective(0)+","+solution.getObjective(1));
			sb.setLength(sb.length()-1);
			sb.append("\n");
		}
		pw.write(sb.toString());
		pw.close();
		
	}
	
	public static void afterRoundUpdating(Solution solution){
		//update developers' zone
		int variableNum=0;
		for(Map.Entry<Integer, Bug> bug:bugs.entrySet()){
			for(Map.Entry<Zone, Double> zone:bug.getValue().BZone_Coefficient.entrySet()){
				updateDeveloperSkills(solution.getVariable(variableNum).toString(),zone);
				variableNum++;
			}
		}
		//remove 2 top developers
		
	}
	public static void removeDevelopers(){
		int devId=-1;
		double devScore=-1.0;
		//select dev with the max of sum of competencies 
		for(Map.Entry<Integer, Developer> dev:developers.entrySet()){
			if(devCompetenciesMeasurement(dev.getValue())>devScore){
				devId=dev.getKey();
				devScore=devCompetenciesMeasurement(dev.getValue());
			}
		}
		try{
			
		GA_Problem_Parameter.DevList.remove(devId);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	public static double devCompetenciesMeasurement(Developer dev){
		double CumulativeSkillLevel=0.0;
		for(Map.Entry<Zone,Double> zone:dev.getDZone_Coefficient().entrySet())
			CumulativeSkillLevel+=zone.getValue();
		return CumulativeSkillLevel;
	}
	
	public static void updateDeveloperSkills(String dev, Map.Entry<Zone, Double> zone){
		for(Map.Entry<Zone, Double> devZone:developers.get(Integer.parseInt(dev)).DZone_Coefficient.entrySet()){
			if(devZone.getKey().zId==zone.getKey().zId)
				developers.get(Integer.parseInt(dev)).DZone_Coefficient.put(devZone.getKey(),devZone.getValue()+zone.getValue());
		}
	}
	
}
