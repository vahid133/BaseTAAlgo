package Karim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import org.moeaframework.core.NondominatedPopulation;


public class Test1 {

	public static void main(String[] args) throws FileNotFoundException{
		Initialization();
		Assigning();
		writeResult();
		afterRoundPurning();
		
		
		
	}
	
	public static void Initialization() throws FileNotFoundException{
		HashMap<String , Zone> columns=new HashMap<String, Zone>();
		
		//initialize developers
		HashMap<Integer,Developer> developers=new HashMap<Integer,Developer>();
		Developer developer = null;
		Scanner sc=new Scanner(System.in);
		sc=new Scanner(new File(sc.nextLine()));
		int i=0;
		int j=0;
		while(sc.hasNextLine()){
			if(i!=0){
					for(String s:sc.next().split(" ")){
						if(j!=0){
						columns.put(Integer.toString(j),new Zone(j, s));
						}
						j++;
					}
				
			}
			else{
				for(String s:sc.next().split(" ")){
					if(j!=0){
						developer.DZone_Coefficient.put(columns.get(s), Double.parseDouble(s));
					}
					else{
						developer=new Developer(0);
						developer.setID(Integer.parseInt(s));
					}
					j++;
				}
				i++;
				j=0;
			}
			developers.put(developer.getID(), developer);
		}
		
		//initialize bugs: consider dependent bugs
		
		/*generate bug objects*/
		HashMap<Integer,Bug> bugs=new HashMap<Integer,Bug>();
		Bug bug=null;
		sc=new Scanner(System.in);
		sc=new Scanner(new File(sc.nextLine()));
		while(sc.hasNextLine()){
			if(i!=0){
					for(String s:sc.next().split(" ")){
						if(j!=0){
						columns.put(Integer.toString(j),new Zone(j, s));
						}
						j++;
					}
				
			}
			else{
				for(String s:sc.next().split(" ")){
					if(j!=0){
						bug.BZone_Coefficient.put(columns.get(s), Double.parseDouble(s));
					}
					else{
						bug=new Bug(0);
						bug.setID(Integer.parseInt(s));
					}
					j++;
				}
				i++;
				j=0;
			}
			bugs.put(bug.getID(), bug);
		}
		
		/*set bug dependencies*/
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
			
		}
		
		/* set competency dependencies*/
		
		
		//initialize GA parameters
		
		GA_Problem_Parameter.Num_of_variables=bugs.size();
		for(Entry<Integer, Developer> dev:developers.entrySet())
			GA_Problem_Parameter.DevList.add(dev.getKey());
		//GA_Problem_Parameter.
		
	}
	
	//assigning to developer
	public static void Assigning(){
		NondominatedPopulation result_Karim=new Executor().withProblemClass(CompetenceMulti2_problem.class).withAlgorithm("NSGAII")
				.withMaxEvaluations(500).withProperty("populationSize",GA_Problem_Parameter.population)
				.withProperty("sbx.rate", GA_Problem_Parameter.sbx_rate).withProperty("sbx.distributionIndex", GA_Problem_Parameter.sbx_distribution_index)
				.withProperty("pm.rate", GA_Problem_Parameter.pm_rate).withProperty("pm.distributionIndex", GA_Problem_Parameter.pm_distribution_index)
				.run();
		
		
	    NondominatedPopulation result_me=new Executor().withProblemClass(InformationDifussion.class).withAlgorithm("NSGAII")
				.withMaxEvaluations(500).withProperty("populationSize",GA_Problem_Parameter.population)
				.withProperty("sbx.rate", GA_Problem_Parameter.sbx_rate).withProperty("sbx.distributionIndex", GA_Problem_Parameter.sbx_distribution_index)
				.withProperty("pm.rate", GA_Problem_Parameter.pm_rate).withProperty("pm.distributionIndex", GA_Problem_Parameter.pm_distribution_index)
				.run();
	    
	    
	}
	
	public static void writeResult(){
		//write results to CSV for each round
	}
	
	public static void afterRoundPurning(){
		//
	}
}
