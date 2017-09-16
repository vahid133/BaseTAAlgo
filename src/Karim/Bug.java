package Karim;

import java.util.HashMap;

public class Bug 
{
	Bug[] DB;
	int competenceProfileCount;
	int ID;
	double competenceProfile[];
	int manualDeveloperID;
	
	double totalEstimatedEffort;

	HashMap< Zone, Double> BZone_Coefficient=new HashMap<Zone,Double>();
	double startTime;
	double endTime;
	int algorithmicDeveloperAssignmentID=0;
	
	public int getAlgorithmicDeveloperAssignmentID() {
		return algorithmicDeveloperAssignmentID;
	}

	public void setAlgorithmicDeveloperAssignmentID(
			int algorithmicDeveloperAssignmentID) {
		this.algorithmicDeveloperAssignmentID = algorithmicDeveloperAssignmentID;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}

	public double getStartTime() {
		return startTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public Bug(int competenceCount)
	{
		this.competenceProfileCount=competenceCount;
		competenceProfile=new double[competenceCount];
	}
	
	public int getCompetenceProfileCount() {
		return competenceProfileCount;
	}
	public void setCompetenceProfileCount(int competenceProfileCount) {
		this.competenceProfileCount = competenceProfileCount;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	public double[] getCompetenceProfile() {
		return competenceProfile;
	}
	public void setCompetenceProfile(double[] competenceProfile) {
		this.competenceProfile = competenceProfile;
	}
	
	public int getManualDeveloperID() {
		return manualDeveloperID;
	}

	public void setManualDeveloperID(int manualDeveloperID) {
		this.manualDeveloperID = manualDeveloperID;
	}
	
	public double getTotalEstimatedEffort() {
		return totalEstimatedEffort;
	}

	public void setTotalEstimatedEffort(double totalEstimatedEffort) {
		this.totalEstimatedEffort = totalEstimatedEffort;
	}
}
