package Karim;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

public class CompetenceMulti2_problem extends AbstractProblem {
	
	Bug[] bugs;
	Developer[] developers;
	public CompetenceMulti2_problem(Bug[] bugs ,Developer[] developers){
		super(GA_Problem_Parameter.Num_of_variables,GA_Problem_Parameter.Num_of_functions);
		this.bugs=bugs;
		this.developers=developers;
	}
	
	
	@Override
	public Solution newSolution(){
		Solution solution=new Solution(GA_Problem_Parameter.Num_of_variables,GA_Problem_Parameter.Num_of_functions);
		for( int i=0;i<GA_Problem_Parameter.Num_of_variables;i++){
			solution.setVariable(i, new RealVariable(GA_Problem_Parameter.startDevId,GA_Problem_Parameter.Num_of_Active_Developers));
		}
		return solution;
	}
		
	
	@Override 	
	public void evaluate(Solution solution){
		double[] x = EncodingUtils.getReal(solution);
		double f1 = 0.0;
		double f2 = 0.0;
		
		for (int i = 0; i < GA_Problem_Parameter.Num_of_variables - 1; i++) {
		
			for(int j=0;j<bugs.length;j++){
				
			}
		 }
		
		 for (int i = 0; i < numberOfVariables; i++) {
		 f2 += Math.pow(Math.abs(x[i]), 0.8) +
		 5.0 * Math.sin(Math.pow(x[i], 3.0));
		 }
		
		 solution.setObjective(0, f1);
		solution.setObjective(1, f2);
		 }
		
	
}

