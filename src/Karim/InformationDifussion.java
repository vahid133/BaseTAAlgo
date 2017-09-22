package Karim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

import com.sun.org.apache.xpath.internal.operations.Variable;

public class InformationDifussion extends AbstractProblem{
	Bug[] bugs;
	ArrayList<Developer> developers;
	public InformationDifussion(Bug[] bugs ,Developer[] developers){
		super(GA_Problem_Parameter.Num_of_variables,GA_Problem_Parameter.Num_of_functions);
		this.bugs=bugs;
		this.developers= new ArrayList<Developer>(Arrays.asList(developers));
	}
	
	
	@Override
	public Solution newSolution(){
		Solution solution=new Solution(GA_Problem_Parameter.Num_of_variables,GA_Problem_Parameter.Num_of_functions);
		int j=0;
		for( int i=0;i<GA_Problem_Parameter.Num_of_variables;i++){
			for(Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet()){
				RealVariable rv=new RealVariable(GA_Problem_Parameter.startDevId,GA_Problem_Parameter.startDevId);
				rv.setValue(GA_Problem_Parameter.getDevId());
				solution.setVariable(j,rv);
				j++;
			}
			}
		return solution;
	}
		
	
	@Override 	
	public void evaluate(Solution solution){
		double[] x = EncodingUtils.getReal(solution);
		double f1 = 0.0;
		double f2=0.0;
		double f2_1 = 0.0;
		double f2_2 = 0.0;
		
		for (int i = 0; i < GA_Problem_Parameter.Num_of_variables - 1; i++) {
			 for(Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet()){
				f1+=fitnessCalc.totalTime(bugs[i],zone, developers.get(Integer.parseInt(solution.getVariable(i).toString())));
			}
			bugs[i].endTime=f1+bugs[i].startTime;
		 }
		
		//compute zone dissimilarity
		 for (int i = 0; i < GA_Problem_Parameter.Num_of_variables-1; i++) {
			 int j=0;
			 for(Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet()){
					for(int k=j;k<bugs[i].BZone_Coefficient.size();k++)
						f2_1+=fitnessCalc.getSimDev(developers.get(Integer.parseInt(solution.getVariable(j).toString())),
												  developers.get(Integer.parseInt(solution.getVariable(k).toString())));
			j++;
			 }
		 }
		//compute team similarity
		 for (int i = 0; i < GA_Problem_Parameter.Num_of_variables-1; i++) {
			 
			 for(Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet()){
				 f2_2 +=fitnessCalc.getSimBug( developers.get(Integer.parseInt(solution.getVariable(i).toString())), bugs[i]);
			
			 }
		 f2=f2_1+f2_2;
		 }
		 
		 
		solution.setObjective(0, f1);
		solution.setObjective(1, f2);
		 }

}
