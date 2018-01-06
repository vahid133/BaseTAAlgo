package Karim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.moeaframework.algorithm.DBEA;
import org.moeaframework.core.Solution;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.jgrapht.alg.ConnectivityInspector;

import java.util.Iterator;
import java.lang.Object;

import org.apache.commons.math3.distribution.BinomialDistribution;

import com.sun.scenario.effect.DelegateEffect;

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
	static double delayPenaltyCostRate=0.33;
	//
	static Bug[] bugs;
	static HashMap<Integer,Developer> developers;
	public static final int startDevId=1;
	public static final int endDevId=20;
	private static final Class<? extends DefaultEdge> EClass = null;
	public static double currentTimePeriodStartTime=0;
	public static ArrayList<Integer> DevList=new ArrayList<Integer>();
	public static ArrayList<Integer> DevList_forAssignment=new ArrayList<Integer>();
	/*public void setNum_of_Variables(){
		Num_of_variables=Num_of_Bugs*Num_of_Zones;
	}*/
	
	
	public static void initializeDeveloperPool(){
		for(int i=0;i<3;i++){
			for(Integer dev:DevList)
				DevList_forAssignment.add(dev);
		}
	}
	
	public static int getRandomDevId(){
		Random rg=new Random();
		int index=rg.nextInt(DevList.size());
		return DevList.get(index);
	}
	
	
	public static int getDevId(){
		if(DevList_forAssignment.size()>0){
			Random rg=new Random();
			int index=rg.nextInt(DevList_forAssignment.size());
			int devId=DevList_forAssignment.get(index);
			DevList_forAssignment.remove(index);
			return devId;
		}
		else{
			return -1;
		}	
	}
	
	
	public static ArrayList<DefaultEdge> getValidSolution(ArrayList<DefaultEdge> edges, DirectedAcyclicGraph<Bug, DefaultEdge> DAG){
		ArrayList<DefaultEdge> verifiedEadges=new ArrayList<DefaultEdge>();
		DefaultEdge e=new DefaultEdge();
		for(Iterator<DefaultEdge> iterator=edges.iterator();iterator.hasNext();){
			e=(DefaultEdge) iterator.next().clone();
			iterator.remove();
			if(Math.random()<0.50){
				verifiedEadges.add(e);
				update(edges,e,DAG);
			}
		}
		return verifiedEadges;
	}
	
	
	public static void update(ArrayList<DefaultEdge> edges, DefaultEdge e, DirectedAcyclicGraph<Bug,DefaultEdge> dag){
		DefaultEdge e_reverse=dag.getEdge(dag.getEdgeTarget(e), dag.getEdgeSource(e));
		edges.remove(e_reverse);
		ConnectivityInspector<Bug, DefaultEdge> CI=new ConnectivityInspector<Bug, DefaultEdge>(dag);
		for(DefaultEdge ed: edges){
			if(CI.pathExists(dag.getEdgeSource(ed), dag.getEdgeSource(ed))){
				edges.remove(dag.getEdge(dag.getEdgeTarget(ed), dag.getEdgeSource(ed)));
				edges.remove(ed);
			}
		}
		
		
	}
	
	public static DirectedAcyclicGraph<Bug, DefaultEdge> getDAGModel(Bug[] bugs){
		DirectedAcyclicGraph<Bug, DefaultEdge> dag=new DirectedAcyclicGraph<Bug, DefaultEdge>(EClass);
		for(int i=0;i<bugs.length;i++){
			if(bugs[i].DB.size()>0){
				for(Bug b:bugs[i].DB){
					if(!dag.containsEdge(bugs[i],b))
						dag.addEdge(b,bugs[i]);
				}
			}
			else{
				dag.addVertex(bugs[i]);
			}
		}
		return dag;
	}
	
	public static ArrayList<DefaultEdge> getEdges(ArrayList<Bug> tasks){
	
		return new ArrayList<DefaultEdge>();
	}
	

	public static TopologicalOrderIterator<Bug, DefaultEdge> getTopologicalSorted(DirectedAcyclicGraph<Bug, DefaultEdge> dag){
		return new TopologicalOrderIterator<Bug, DefaultEdge>(dag);
	}
}
