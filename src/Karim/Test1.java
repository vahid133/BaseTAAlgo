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
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;


public class Test1 {
	public static HashMap<Integer,Developer> developers=new HashMap<Integer,Developer>();
	static HashMap<Integer,Bug> bugs=new HashMap<Integer,Bug>();
	static Solution solution=null;
	public static void main(String[] args) throws IOException{
		int roundNum=5;
		for(int i=1;i<=roundNum;i++){
		Initialization(i);
		NondominatedPopulation[] results = new NondominatedPopulation[2]; 
		results=Assigning(results);
		solution=results[1].get(results[1].size()/2);
		writeResult(i,results);
		afterRoundUpdating(solution);
		}
	}
	
	public static void Initialization( int roundNum) throws IOException,NoSuchElementException{
		HashMap<Integer , Zone> columns=new HashMap<Integer, Zone>();
		
		Project project=new Project();
		
		
		
		//initialize developers
		System.out.println("enter the developrs file");
		Developer developer = null;
		Scanner sc=new Scanner(System.in);
		sc=new Scanner(new File(sc.nextLine()));
		System.out.println("enter the devlopers wage file");
		Scanner scan=new Scanner(System.in);
		scan=new Scanner(new File(scan.nextLine()));
		int i=0;
		int j=0;
		while(sc.hasNextLine() && scan.hasNextLine()){
			if(i==0){
				String[] items=sc.nextLine().split("\t",-1);
				scan.nextLine();
					for(int k=0;k<items.length;k++){
						if(j!=0){
						Zone zone=new Zone(j, items[k]);
						project.zones.put(j, zone);
						columns.put(j,zone);
						}
						j++;
					}
			}
			else{
				String[] items=sc.nextLine().split("\t",-1);
				String[] wage_items=scan.nextLine().split("\t",-1);
				for(int k=0;k<items.length;k++){
					if(j!=0){
						//developer.DZone_Coefficient.put(columns.get(j), Double.parseDouble(items[k]));
						//System.out.println(columns.get(j));
						developer.DZone_Coefficient.put(project.zones.get(j), Double.parseDouble(items[k]));
						developer.DZone_Wage.put(project.zones.get(j), Double.parseDouble(wage_items[k])*Double.parseDouble(wage_items[wage_items.length-1]));
						System.out.println(Double.parseDouble(wage_items[k]));
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
		int n=0;
		Scanner sc1=null;
		for(File fileEntry:new File(sc.nextLine()).listFiles()){
			System.out.println(fileEntry);
			sc1=new Scanner(new File(fileEntry.toURI()));
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileEntry.toURI())));
			int lines = 0;
			while (reader.readLine() != null) lines++;
			reader.close();
			int numOfBugs=lines;
			i=0;
			j=0;
			int t=0;
			n=0;
			while(sc1.hasNextLine() ){
				if( n<(numOfBugs/5)*(roundNum) && n>=((numOfBugs/5)*(roundNum-1))){
					
					if(i==0){
						String[] items=sc1.nextLine().split("\t",-1);
							for(int k=0;k<items.length;k++){
								if(j>2){
									Zone zone=new Zone(j, items[k]);
									columns.put(j,zone);
								}
								j++;
							}	
					}
					else{
						String[] items=sc1.nextLine().split("\t",-1);
						for(int k=0;k<items.length;k++){
							if(j>2){
								//bug.BZone_Coefficient.put(columns.get(j), Double.parseDouble(items[k]));
								bug.BZone_Coefficient.put(project.zones.get(j-2),Double.parseDouble(items[k]));
							}
							else if(j==0){
								bug=new Bug(0);
								bug.setID(Integer.parseInt(items[k]));
							}
							else if(j==2){
								bug.setTotalEstimatedEffort(Double.parseDouble(items[k]));
							}
							j++;
						}
					bugs.put(bug.getID(), bug);
					System.out.println(bug.ID+"----"+bug.BZone_Coefficient.values());
					}
					i++;
					j=0;
				}
				else{
					sc1.nextLine();
				}
				n++;
			}
		n=0;
		}
		
					
		/*set bug dependencies*/
		System.out.println("enter the bug dependency files");
		sc=new Scanner(System.in);
		String[] columns_bug=null;
		for(File fileEntry:new File(sc.nextLine()).listFiles()){
			
			sc1=new Scanner(new File(fileEntry.toURI()));

			//System.out.println(fileEntry.toURI());

			i=0;
			while(sc1.hasNextLine()){
				if(i>0){
					columns_bug=sc1.nextLine().split(",");
					//for(int h=0;h<columns_bug.length;h++)
						//System.out.println(columns_bug[h]);
					for(int k=0;k<columns_bug.length-1;k++){
						/*need to be check the existence of the item in bugs set-----------------------------------------------*/
						if(k==1 && columns_bug[k].toString().length()>1 && columns_bug[k]!=null ){
							//System.out.println(columns_bug[k-1]);
							try{
							System.out.println(bugs.get(columns_bug[k-1]).ID);
							bugs.get(columns_bug[k-1]).DB.add(bugs.get(k));
							}
							catch(NullPointerException e){
								
							}
						}
				
					}
					j++;
				}
				else{

					sc1.nextLine();
				}
				i++;
			}
		}
		
		sc1.close();
		sc.close();
		/* set competency dependencies*/
		
		
		//initialize GA parameters
		
		GA_Problem_Parameter.Num_of_variables=bugs.size();
		for(Entry<Integer, Developer> dev:developers.entrySet()){
			GA_Problem_Parameter.DevList.add(dev.getKey());
			System.out.println(dev.getKey());
		}
		GA_Problem_Parameter.developers=developers;
		
		int b_index=0;
		GA_Problem_Parameter.bugs=new Bug[bugs.size()];
		for(Entry<Integer, Bug> b2:bugs.entrySet()){
			GA_Problem_Parameter.bugs[b_index]=b2.getValue();
			b_index++;
		}
		
		//GA_Problem_Parameter
		GA_Problem_Parameter.Num_of_Bugs=bugs.size();
		GA_Problem_Parameter.Num_of_Active_Developers=developers.size();
		GA_Problem_Parameter.Num_of_functions=2;
		GA_Problem_Parameter.Num_of_variables=0;
		for(Entry<Integer, Bug>  b:bugs.entrySet()){
			for(Map.Entry<Zone, Double>  zone:b.getValue().BZone_Coefficient.entrySet()){
				GA_Problem_Parameter.Num_of_variables++;
			}
			}
		GA_Problem_Parameter.population=100;
		
	}
	
	//assigning to developer
	public static NondominatedPopulation[] Assigning(NondominatedPopulation[] results){
		NondominatedPopulation result_Karim=new Executor().withProblemClass(CompetenceMulti2_problem.class).withAlgorithm("NSGAII")
				.withMaxEvaluations(100).withProperty("populationSize",GA_Problem_Parameter.population)
				.withProperty("sbx.rate", GA_Problem_Parameter.sbx_rate).withProperty("sbx.distributionIndex", GA_Problem_Parameter.sbx_distribution_index)
				.withProperty("pm.rate", GA_Problem_Parameter.pm_rate).withProperty("pm.distributionIndex", GA_Problem_Parameter.pm_distribution_index)
				.run();
		results[0]=result_Karim;

		System.out.println("finished first one");
	    NondominatedPopulation result_me=new Executor().withProblemClass(InformationDifussion.class).withAlgorithm("NSGAII")
				.withMaxEvaluations(100).withProperty("populationSize",GA_Problem_Parameter.population)
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
