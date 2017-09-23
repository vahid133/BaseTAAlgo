package Karim;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;


public class Test1 {

	public static void main(String[] args) throws FileNotFoundException{
		int roundNum=5;
		for(int i=1;i<=roundNum;i++){
		Initialization(i);
		NondominatedPopulation[] results = null; 
		results=Assigning(results);
		writeResult(i,results);
		afterRoundUpdating();
		}
	}
	
	public static void Initialization( int roundNum) throws FileNotFoundException{
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
		sc.close();
		
		//initialize bugs: consider dependent bugs
		
		/*generate bug objects*/
		HashMap<Integer,Bug> bugs=new HashMap<Integer,Bug>();
		Bug bug=null;
		sc=new Scanner(System.in);
		for(File fileEntry:new File(sc.next()).listFiles() ){
			sc=new Scanner(fileEntry);
			LineNumberReader lnr=new LineNumberReader(new FileReader(fileEntry));
			int numOfBugs=lnr.getLineNumber();
			int n=numOfBugs*roundNum;
			while(sc.hasNextLine() && n>=numOfBugs/5 && n<2*(numOfBugs/5) ){
				n++;
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
	
	public static void afterRoundUpdating(){
		//update developers' zone
		
	}
}
