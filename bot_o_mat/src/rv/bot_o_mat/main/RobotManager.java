/**
 * 
 */
package rv.bot_o_mat.main;

import java.util.ArrayList;


/**
 * Manager class which uses the singleton design pattern. 
 * We are using singleton because we only need one "Manager" instance to work with the Robot objects
 * Manager class is the bridge between the BackEnd code and the FrontEnd GUI code.
 * @author Mario Medel
 *
 */
public class RobotManager {
	/** the single instance of a robot manager, we only need one instance to work with all the robots */
	private static RobotManager singleton;
	/** the current robot, the manager automatically sets a newly added robot to the current robot */ 
	public Bot currentRobot;
	/** the robot list which the user will work with */
	public ArrayList<Bot> robotList;
	/** will be used to contain all of the available tasks */
	public ArrayList<Task> allTasks;
	
	
	
	/**
	 * private constructor to ensure that there is only one instance of the robot manager
	 */
	private RobotManager() {
		robotList = new ArrayList<Bot>();
		allTasks = new ArrayList<Task>();
	}
	/**
	 * returns the current instance of the robot manager. if the robot manager is null then it will create a new instance for the user
	 * this is the only way that the user will be able to access an instance of this class
	 * @return the current instance variable or create a new instance
	 */
	public static RobotManager getInstance() {
		if (singleton == null) {
			singleton = new RobotManager();
			return singleton;
		}
		
		return singleton;
	}
	/**
	 * method will return the current robot instance to the caller
	 * @return the current robot instance
	 */
	public Bot getCurrentBot() { return currentRobot; }
	/**
	 * method which will allow the user to add a Task to the current Robot that was recently adding/selected
	 * 
	 * @param descrip String representation of the description for the Task
	 * @param eta the estimated time in a String needed to complete the task
	 */
	public void addTaskToRobot(String descrip, String eta) 
	{
		int etaInt;
		try {
			etaInt = Integer.parseInt(eta);
			Task t = new Task(descrip, etaInt);
			currentRobot.addTask(descrip, etaInt);
			allTasks.add(t);
		} catch (NumberFormatException e) {
			//do nothing
			return;
		}
		 //further error checking is done inside the addTask method
		
	}
	/**
	 * method will instantiate a robot based on the provided user input
	 * @param bName Name associated with the robot 
	 * @param bType the type of robot which will be created
	 */
	public void addRobot(String bName, String bType) 
	{ 
		Bot newB = new Bot(bName, bType); //further error checking is done by the methods which the Bot constructor will call
		robotList.add(newB);
		allTasks.addAll(newB.getUncompletedTasksList());
		allTasks.addAll(newB.getCompletedTasksList());
		currentRobot = newB;
	}
	public String getRobotName() { return currentRobot.getBotName(); }
	public String getRobotType() { return currentRobot.getBotTypeString(); }
	/**
	 * method will be called by the GUI to clear everything
	 */
	public void clearData() {
		robotList = new ArrayList<Bot>();
		allTasks = new ArrayList<Task>();
		currentRobot = null;
	}
	
	public ArrayList<Bot> getRobotList() { return robotList; }
	/**
	 * returns the tasks as a 2 dimensional array for display in the GUI
	 * each row is for each task, each column is for that tasks info
	 * @return 2 dimensional array
	 */
	public Object[][] getTasksAsArray() {
		
		allTasks = new ArrayList<Task>();
		for (int i = 0; i < robotList.size(); i++) 
		{ 
			
			allTasks.addAll(robotList.get(i).getUncompletedTasksList());
			if (robotList.get(i).getCompletedTasksList().size() != 0) {
				allTasks.addAll(robotList.get(i).getCompletedTasksList());
			}
		}
		String[][] robotTasks = new String[1000][4];
		for (int i = 0; i < allTasks.size(); i++) {
			robotTasks[i][0]  = allTasks.get(i).getRobot().getBotName() + " " + allTasks.get(i).getRobot().getBotTypeString().substring(0, 4);
			robotTasks[i][1] = allTasks.get(i).getDescription();
			robotTasks[i][2] = "" + allTasks.get(i).getEstimatedTime();
			robotTasks[i][3] = "" + allTasks.get(i).isCompleted();
		}
		
		
		return robotTasks;
	}
	
	/**
	 * public method used to load a selected robot and all of its fields for GUI purposes
	 * 
	 * @param robotName the desired product to be loaded
	 */
	public void loadRobot(String robotName) {
		currentRobot = null;
		
		for (Bot b : robotList) {
			if (b.getBotName().equals(robotName)) {
				currentRobot = b;
			}
		}
		
		if (currentRobot == null) {
			throw new IllegalArgumentException("Robot not available.");
		}
	}
	/**
	 * removes the task based on the ID  
	 * @param id provided Id used to remove the task
	 */
	public void deleteTaskById(int id) 
	{ 
		Task t = allTasks.get(id);
		allTasks.remove(id);
		if (currentRobot.getUncompletedTasksList().contains(t)) {
			currentRobot.getUncompletedTasksList().remove(t);
			return;
		}
		
//		currentRobot.getUncompletedTasksList().remove(t);
		if (currentRobot.getCompletedTasksList().contains(t)) {
			currentRobot.getCompletedTasksList().remove(t);
		}
//		currentRobot.getCompletedTasksList().remove(t);
//		for(int i = 0; i < robotList.size(); i++) {
//			if (t.getRobot().equals(robotList.get(i))) { robotList.remove(i); }
//		}
	}
	/**
	 * edits the current robots name to the provided parameter
	 * @param name the new robot name
	 */
	public void editRobot(String name) { currentRobot.setRobotName(name); }
	
}
