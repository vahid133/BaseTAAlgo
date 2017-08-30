package Karim;

public class Developer 
{
	
	private int competenceProfileCount;
	private int ID;
	private double competenceProfile[];
	private int developerNextAvailableHour;
	private int totalAssignedBugs;
	
	public int getTotalAssignedBugs() {
		return totalAssignedBugs;
	}

	public void setTotalAssignedBugs(int totalAssignedBugs) 
	{
		this.totalAssignedBugs = totalAssignedBugs;
	}

	
	public int getDeveloperNextAvailableHour() {
		return developerNextAvailableHour;
	}

	public void setDeveloperNextAvailableHour(int developerNextAvailableHour) {
		this.developerNextAvailableHour = developerNextAvailableHour;
	}

	public Developer(int competenceCount)
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
}
