package chapter2;

import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.variable.EncodingUtils;

public class RunSchafferProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			NondominatedPopulation result=new Executor().withAlgorithm("NSGAII")
					.withProblemClass(SchafferProblem.class).withMaxEvaluations(1000)
					.run();
			for(org.moeaframework.core.Solution solution:result){
				System.out.printf("%.5f => %.5f, %.5f\n",
						EncodingUtils.getReal(solution.getVariable(0)),
						solution.getObjective(0),
						solution.getObjective(1));
			}
		

	}

}
