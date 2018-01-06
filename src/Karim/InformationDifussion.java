package Karim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.text.GapContent;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

import com.sun.org.apache.xpath.internal.ExpressionNode;
import com.sun.org.apache.xpath.internal.operations.Variable;

public class InformationDifussion extends AbstractProblem{
	Bug[] bugs=GA_Problem_Parameter.bugs;
	HashMap<Integer,Developer> developers=GA_Problem_Parameter.developers;
	DirectedAcyclicGraph<Bug, DefaultEdge> DEP;
	ArrayList<Zone> genes=new ArrayList<Zone>();
	public InformationDifussion(){
		super(GA_Problem_Parameter.Num_of_variables,GA_Problem_Parameter.Num_of_functions);
		//this.bugs=bugs;
		//this.developers= new ArrayList<Developer>(Arrays.asList(developers));
	}
	
	
	@Override
	public Solution newSolution(){
		/*
		 		
		int j=0;
		//change the code
		for( int i=0;i<GA_Problem_Parameter.Num_of_variables;i++){
				int randDevId=GA_Problem_Parameter.getRandomDevId();
				
				solution.setVariable(j,EncodingUtils.newInt(randDevId, randDevId));
				
				j++;
			}
		
		*/
		TopologicalOrderIterator<Bug,DefaultEdge> tso=GA_Problem_Parameter.getTopologicalSorted(DEP);
		int j=0;
		while(tso.hasNext()){
			Bug b=tso.next();
			b.setZoneDEP();
			TopologicalOrderIterator<Zone,DefaultEdge> tso_zones=new TopologicalOrderIterator<Zone, DefaultEdge>(b.Zone_DEP);
			while(tso_zones.hasNext()){
				genes.add(tso_zones.next());
			}
		}
		//chnged num of variables for the soultuion
		Solution solution=new Solution(genes.size(),GA_Problem_Parameter.Num_of_functions);
		for(Zone z:genes){
			int randDevId=GA_Problem_Parameter.getRandomDevId();
			solution.setVariable(j,EncodingUtils.newInt(randDevId, randDevId));
		}
		return solution;
	}
		
	
	@Override 	
	public void evaluate(Solution solution){
		double[] x = EncodingUtils.getReal(solution);
		double f1 = 0.0;
		double f1_1=0.0;
		double f1_2=0.0;
		double f2=0.0;
		double f2_1 = 0.0;
		double f2_2 = 0.0;
		
		int numOfVar=0; 
		for (int i = 0; i < GA_Problem_Parameter.Num_of_Bugs; i++) {
			 for(Map.Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet()){
				f1_1+=fitnessCalc.completionTime(bugs[i],zone, developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))))
						*developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))).getDZone_Wage().get(zone.getKey());
						numOfVar++;
				f1_2+=fitnessCalc.getDelayTime(bugs[i], zone, developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))))*GA_Problem_Parameter.delayPenaltyCostRate;		
				developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))).developerNextAvailableHour+=fitnessCalc.completionTime(bugs[i],zone, developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))));
			 }
			 f1=f1_1+f1_2;
		 }
		
		
		//compute the infomration difuusion
		numOfVar=0;
		 ArrayList<Developer> devs=new ArrayList<Developer>();
		 for (int i = 0; i < GA_Problem_Parameter.Num_of_Bugs; i++) {
			 for(Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet()){
				 //f2_2 +=fitnessCalc.getSimBug( developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))), bugs[i],zone.getKey());
				 devs.add(developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))));
				 numOfVar++;
			 }
			 f2_1+=fitnessCalc.getDataFlow(bugs[i], devs);
		 }
		
		
		
		//compute team similarity
		 numOfVar=0;
		 devs.clear();
		 for (int i = 0; i < GA_Problem_Parameter.Num_of_Bugs; i++) {
			 for(Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet()){
				 //f2_2 +=fitnessCalc.getSimBug( developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))), bugs[i],zone.getKey());
				 devs.add(developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))));
				 numOfVar++;
			 }
			 f2_2+=fitnessCalc.getTZoneSim(bugs[i].BZone_Coefficient, devs);
		 }
		 f2=f2_1+f2_2;
		 
		solution.setObjective(0, f1);
		solution.setObjective(1, f2);
		
		 }

	public void generateDAG(){
		DEP=GA_Problem_Parameter.getDAGModel(bugs);
	}
}
