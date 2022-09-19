package rv.bot_o_mat.main;

/**
 * task class which will be used by the robot (possibly a manager class if enought time to implement a manager class) to create specific tasks 
 * @author Mario Medel
 *
 */
public class Task {
	/** String representation of the description for the tasks class */
	private String description;
	/** integer representation in for estimated time of completation in units of milliseconds */
	private int estimatedTime;
	/** boolean field which will help determine if a task is completed based on the time */
	private boolean isCompleted;
	/** indicates the robot which this task belongs to */ 
	private Bot myRobot; 
	/**
	 * public constructor used to instantiate a task object to be completed by a task
	 * @param descrip String representation of the task
	 * @param time estimated time to complete the task in milliseconds
	 */
	public Task(String descrip, int time) {
		setDescription(descrip);
		setEstimatedTime(time);
		isCompleted = false;
	}
	/**
	 * setter method for the task description 
	 * @throws IllegalArugmentException if the task description is null or an empty string
	 * @param descrip the desired description which the user would like to set
	 */
	public void setDescription(String descrip) {
		if(descrip == null || "".equals(descrip)) { throw new IllegalArgumentException("Tasks name may not be null or empty string"); }
		description = descrip;
	}
	
	public String getDescription() { return description; }
	
	/**
	 * setter method which will set the estimated time in milliseconds to complete this task
	 * @param estimatedT time until completion in milliseconds
	 * @throws IllegalArgumentException if the estimated time is negative
	 */
	public void setEstimatedTime(int estimatedT) {
		if (estimatedT < 0 ) { throw new IllegalArgumentException("Estimated time can only be non-negative"); }
		estimatedTime = estimatedT;
	}
	
	public int getEstimatedTime() { return estimatedTime; } 
	
	public void setComplete(boolean isC) { this.isCompleted = isC; } 
	public boolean isCompleted() { return isCompleted; } 
	/**
	 * setter method which will be called by the manager to set this task to an associated robot
	 * @param b robot which pertains to this task
	 */
	public void setRobot(Bot b) {
		if (b == null) { throw new IllegalArgumentException("Bot cannot be null."); }
		
		myRobot = b;
	}
	/**
	 * getter method for the associated robot which this task belongs to
	 * @return the associated robot
	 */
	public Bot getRobot() { return myRobot; } 

}

