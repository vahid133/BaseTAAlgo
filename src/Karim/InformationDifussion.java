package Karim;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

import com.sun.org.apache.xpath.internal.ExpressionNode;
import com.sun.org.apache.xpath.internal.operations.Variable;

public class InformationDifussion extends AbstractProblem{
	Bug[] bugs=GA_Problem_Parameter.bugs;
	HashMap<Integer,Developer> developers=GA_Problem_Parameter.developers;
	public InformationDifussion(){
		super(GA_Problem_Parameter.Num_of_variables,GA_Problem_Parameter.Num_of_functions);
		//this.bugs=bugs;
		//this.developers= new ArrayList<Developer>(Arrays.asList(developers));
	}
	
	
	@Override
	public Solution newSolution(){
		Solution solution=new Solution(GA_Problem_Parameter.Num_of_variables,GA_Problem_Parameter.Num_of_functions);
		int j=0;
		for( int i=0;i<GA_Problem_Parameter.Num_of_variables;i++){
			/*for(Map.Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet()){
				RealVariable rv=new RealVariable(GA_Problem_Parameter.startDevId,GA_Problem_Parameter.startDevId);
				rv.setValue(GA_Problem_Parameter.getDevId());*/
				int randDevId=GA_Problem_Parameter.getRandomDevId();
				
				solution.setVariable(j,EncodingUtils.newInt(randDevId, randDevId));
				
				j++;
			}
			//}
		return solution;
	}
		
	
	@Override 	
	public void evaluate(Solution solution){
		double[] x = EncodingUtils.getReal(solution);
		double f1 = 0.0;
		double f2=0.0;
		double f2_1 = 0.0;
		double f2_2 = 0.0;
		
		for (int i = 0; i < GA_Problem_Parameter.Num_of_Bugs - 1; i++) {
			 for(Map.Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet()){
				f1+=fitnessCalc.totalTime(bugs[i],zone, developers.get(EncodingUtils.getInt(solution.getVariable(i))));
			}
			bugs[i].endTime=f1+bugs[i].startTime;
		 }
		
		//compute zone dissimilarity
		int numOfVar=0;
		 for (int i = 0; i < GA_Problem_Parameter.Num_of_Bugs; i++) {
			 if(i>0)
				 numOfVar+=bugs[i].BZone_Coefficient.size();
			 for(int j=0;j<bugs[i].BZone_Coefficient.size();j++){
					for(int k=j+1;k<bugs[i].BZone_Coefficient.size();k++)
						f2_1+=fitnessCalc.getSimDev(developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar+j))),
												  developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar+k))));
					
			 }
		 }
		//compute team similarity
		 numOfVar=0;
		 for (int i = 0; i < GA_Problem_Parameter.Num_of_Bugs; i++) {
			 for(Entry<Zone, Double>  zone:bugs[i].BZone_Coefficient.entrySet())
				 f2_2 +=fitnessCalc.getSimBug( developers.get(EncodingUtils.getInt(solution.getVariable(numOfVar))), bugs[i],zone.getKey());
			
		 }
		 f2=f2_1+f2_2;
		 
		solution.setObjective(0, f1);
		solution.setObjective(1, f2);
		
		 }

}
