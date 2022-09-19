package rv.bot_o_mat.main;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;
import java.util.Random;

/**
 * Robot class which will contain its own lists of tasks which it must complete.
 * The robot has private fields of robot name, robot type, and the total number of completed tasks for this bot object
 * 
 * @author Mario Medel
 *
 */
public class Bot extends Thread {
	/** static final String which will be used in order to display on GUI as well as comparing the string that the user types or the button that the user clicks to creat a robot object */
	private static final String UNI_NAME = "unipedal";
	/** static final String which will be used for GUI and to create a robot object based on a string comparison */ 
	private static final String BI_NAME = "bipedal";
	/** final String for an arachnid bot which will be used to indicated which bot name to display on the GUI */
	private static final String ARCH_NAME = "arachanid";
	/** final String used for helping in instantiating a robot object */
	private static final String RAD_NAME = "radial";
	/** final String used for helping in instantiating a robot object */ 
	private static final String AERO_NAME = "aeronautical";
	/** private String which indicated THIS (in java, this means the current instance) robots name */ 
	private String robotName;
	/** private enumeration type of ROBOT which will indicate the current robot type for this object */ 
	private Robot botType;
	/** String representation of the robot type */ 
	private String botTypeString;
	/** private integer representation of the total completed tasks for THIS robot */ 
	private int totalCompletedTasks;
	/** Queue data structure used to represent the uncompleted tasks which are associated with this robot object */
	private Queue<Task> uncompletedTasks;
	/** Stack data structure used to contain the completed tasks. */
	private Stack<Task> completedTasks;
	/** default task information which will be used to create and add 5 tasks to the Queue of uncompleted tasks */
	private String[][] defaultTasksInfo = {{"do the dishes", "1000"},
											{"sweep the house", "3000"}, {"do the laundry", "10000"},
											{"take out the recylcing", "4000"}, {"make a sammich", "7000"},
											{"mow the lawn", "20000"}, {"rake the leaves", "18000"},
											{"give the dog a bath", "14500"}, {"bake some cookies", "8000"},
											{"wash the car", "20000"}};
	
	/**
	 * package enumeration types to handle the different types of robots that can be created
	 * @author Mario Medel
	 *
	 */
	enum Robot { UNIPEDAL, BIPEDAL, QUADRUPEDAL, ARACHNID, RADIAL, AERONAUTICAL };
	/**
	 * constructor which will be called whenever the user passes a name for the robot and a robot type, both parameters will be strings so that the user won't have to worry about much, I plan on adding a GUI later where user will just be able to click
	 * @param bName the name which will be associated with the robot
	 * @param bType the robot type which the user would like to assign to this robot 
	 */
	public Bot(String bName, String bType) 
	{
		setRobotName(bName);
		setBotType(bType);
		uncompletedTasks = new LinkedList<Task>();
		completedTasks = new Stack<Task>();
		createDefaultTasks();
		totalCompletedTasks = 0;
//		this.start();
	}
	
	/**
	 * our own provided implementation of the run method from the thread extension
	 * we will base the time out time based on the estimated time of completion
	 * multithreading !
	 */
	@Override public void run() {
		try 
		{
			while (!uncompletedTasks.isEmpty()) {
				Task t = uncompletedTasks.peek();
				if (t != null) {
					completedTasks.add(t);
					Thread.sleep(t.getEstimatedTime());
					t.setComplete(true); // might not need boolean for completion but we will see TODO
					
					totalCompletedTasks++; //increment the counter for the total completed tasks
					uncompletedTasks.remove();
				}
			}
		} catch (InterruptedException e) { e.printStackTrace(); }
		
	}
	/**
	 * private method which will set the bot type using a string
	 * the method is case insensitive, therefore; radial is the same as Radial.
	 * 
	 * @param bType String representation of the robot type which the user would like to create
	 */
	private void setBotType(String bType) 
	{
		if (bType == null || "".equals(bType)) { throw new IllegalArgumentException("The bot type cannot be null"); }
		switch(bType.toLowerCase()) { //call toLowerCase method on all strings to make the comparisons case insensitive
			case UNI_NAME:
				this.botType = Robot.UNIPEDAL;
				this.botTypeString = UNI_NAME;
				break;
			case BI_NAME:
				this.botType = Robot.BIPEDAL;
				this.botTypeString = BI_NAME;
				return;
			case ARCH_NAME:
				this.botType = Robot.ARACHNID;
				this.botTypeString = ARCH_NAME;
				return;
			case RAD_NAME:
				this.botType = Robot.RADIAL;
				this.botTypeString = RAD_NAME;
				break;
			case AERO_NAME:
				this.botType = Robot.AERONAUTICAL;
				this.botTypeString = AERO_NAME;
				break;
				default:
					throw new IllegalArgumentException("Invalid bot type provided");
		}
		
		
	}
	/**
	 * error checks to make sure that the bot name is not a null or an empty string
	 * sets the name associated with the robot and can be called by the client to change the robot name
	 * @param bName String representation of the name the user is trying to set
	 */
	public void setRobotName(String bName) 
	{
		if (bName == null || "".equals(bName)) { throw new IllegalArgumentException("Robot name cannot be empty."); }
		this.robotName = bName;
	}
	/**
	 * the following are all standard getter methods used to retrieve the private fields of this object to support ENCAPSULATION
	 * @return the private fields
	 */
	public int getTotalCompletedTasks() {  return this.totalCompletedTasks; }
	public Robot getBotType() { return this.botType; } 
	public String getBotName() { return this.robotName; }
	/**
	 * public method which will be used to instantiate a task object and add it to the list of task objects for this robot object
	 * @param description task description in strings
	 * @param estimatedTime time to complete in milliseconds
	 */
	public void addTask(String description, int estimatedTime) {
		//there is no error checking for the description or the estimated time to reduce code redundancy, error checking made in Task class
		Task newTask = new Task(description, estimatedTime);
		newTask.setRobot(this);
		uncompletedTasks.add(newTask);
//		this.start();
	}
	
	public Queue<Task> getUncompletedTasksList() { return uncompletedTasks; }
	public Stack<Task> getCompletedTasksList() { return completedTasks; }
	/**
	 * private method which will create, at random, 5 tasks for the robot to work with
	 * default tasks were provided to us as per requirements 
	 */
	private void createDefaultTasks() 
	{
		Random rand = new Random();
		for(int i = 0; i < 5; i++) //iterate to create 5 tasks to add
		{
			int j = rand.nextInt(10); //generates an integer from 0-9 for each index the 2d array of default tasks
			String descrip = defaultTasksInfo[j][0];
			String time = defaultTasksInfo[j][1];
			
			this.addTask(descrip, Integer.parseInt(time));
		}
	}
	/**
	 * getter method to return the type of robot as a string
	 * @return the bot type as a 
	 */
	public String getBotTypeString() { return botTypeString; }
	
	@Override
	public int hashCode() {
		return Objects.hash(botTypeString, robotName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bot other = (Bot) obj;
		return Objects.equals(botTypeString, other.botTypeString) && Objects.equals(robotName, other.robotName);
	}
}
