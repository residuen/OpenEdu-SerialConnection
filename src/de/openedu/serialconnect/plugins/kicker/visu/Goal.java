package de.openedu.serialconnect.plugins.kicker.visu;

public class Goal {
	
	private double seconds = 0;
	
	private int team1 = 0;
	private int team2 = 0;

	public double getSeconds() {
		return seconds;
	}

	public void setSeconds(double seconds) {
		this.seconds = seconds;
	}

	public int getTeam1() {
		return team1;
	}

	public void setTeam1(int team1) {
		this.team1 = team1;
	}
	
	public int getTeam2() {
		return team2;
	}

	public void setTeam2(int team2) {
		this.team2 = team2;
	}

	@Override
	public String toString()
	{
		return "[team1="+team1+",team2="+team2+",seconds="+seconds+"]";
//		return ("[team1="+team1+",team2="+team2+",seconds="+seconds+"]").replace("[", "").replace("]", "");
	}

}
