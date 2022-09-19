package rv.bot_o_mat.main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

/**
 * Container for the Robots that has the menu options for new
 * tasks.
 * Depending on user actions, other JPanels are loaded for the
 * different ways users interact with the UI.
 * 
 * @author Mario Medel
 */
public class StartGUI extends JFrame {
	
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Bot-O-Mat";
	/** Panel that will contain different views for the application. */
	private JPanel panel;
	/** Constant to identify Robot for CardLayout. */
	private static final String ROBOT_PANEL = "TaskListPanel";
	/** Constant to identify CreateTaskPanel for CardLayout. */
	private static final String CREATE_TASK_PANEL = "CreateTaskPanel";
	/** Robot panel - we only need one instance, so it's final. */
	private final RobotPanel pnlRobot = new RobotPanel();
	/** Add Task Item panel - we only need one instance, so it's final. */
	private final CreateTaskPanel pnlCreateTask = new CreateTaskPanel();
	/** Reference to CardLayout for panel.  Stacks all of the panels. */
	private CardLayout cardLayout;
	/** the singleton instance which the gui will use to interact with all tasks and robots */ 
	private RobotManager manager;
	
	/**
	 * Constructs a {@link StartGUI} object that will contain a {@link JMenuBar} and a
	 * JPanel that will hold different possible views of the data in
	 * the RobotManager.
	 */
	public StartGUI() {
		super();
//		manager = RobotManager.getInstance();
		//Set up general GUI info
		setSize(500, 700);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Create JPanel that will hold rest of GUI information.
		//The JPanel utilizes a CardLayout, which stack several different
		//JPanels.  User actions lead to switching which "Card" is visible.
		panel = new JPanel();
		cardLayout = new CardLayout();
		panel.setLayout(cardLayout);
		panel.add(pnlRobot, ROBOT_PANEL);
		panel.add(pnlCreateTask, CREATE_TASK_PANEL);
		cardLayout.show(panel, ROBOT_PANEL);
		
		//Add panel to the container
		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		
		//Set the GUI visible
		setVisible(true);
		
		
		
	}
	/**
	 * Starts the GUI for the ProductBacklog application.
	 * @param args command line arguments
	 */
	public static void main(String [] args) {
		new StartGUI();
	}
	
	/**
	 * Inner class that creates the look and behavior for the JPanel that 
	 * shows the robots and associated task lists.
	 * 
	 * @author Mario Medel
	 */
	private class RobotPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
//		private JLabel lblLeaderBoard;
//		private JComboBox<String> comboLeaderboardList;
		/** Label for selecting current robot */
		private JLabel lblCurrentRobot;
		/** Combo box for Robot list */
		private JComboBox<String> comboRobotList;
		/** ComboBox for type */
		private JComboBox<String> comboRobotType;
		/** Button to add a robot */
		private JButton btnAddRobot;
		/** Button to edit the selected Robot */
		private JButton btnEditRobot;
		/** Button to delete the selected Robot */
		private JButton btnRefreshRobot;
		
		/** Button for creating a new task */
		private JButton btnAdd;
		/** Button for editing the selected task in the list */
		private JButton btnEdit;
		/** Button for deleting the selected task in the list */
		private JButton btnDelete;
		/** JTable for displaying the list of tasks */
		private JTable tableTasks;
		/** TableModel for Tasks */
		private TaskTableModel tableModel;
		/** Possible types to list in the combo box */
		private String [] types = {"unipedal", "bipedal", "arachanid", "radial", "aeronautical"};
		
		
		/**
		 * Creates the Robot panel.
		 */
		public RobotPanel() {
			super(new GridBagLayout());
			
			//Set up the JPanel that will hold action buttons
			lblCurrentRobot = new JLabel("Current Robot");
			comboRobotList = new JComboBox<String>();
			comboRobotList.addActionListener(this);
			btnAddRobot = new JButton("Add Robot");
			btnAddRobot.addActionListener(this);
			btnEditRobot = new JButton("Edit Robot");
			btnEditRobot.addActionListener(this);
			btnRefreshRobot = new JButton("Refresh Robot");
			btnRefreshRobot.addActionListener(this);
								
			btnAdd = new JButton("Add Task");
			btnAdd.addActionListener(this);
			btnEdit = new JButton("Edit Task");
			btnEdit.addActionListener(this);
			btnDelete = new JButton("Delete Task");
			btnDelete.addActionListener(this);
			
//			lblLeaderBoard = new JLabel("Leaderboard: ");
//			comboLeaderboardList = new JComboBox<String>();
			
			JPanel pnlRobotSelection = new JPanel();
			pnlRobotSelection.setLayout(new GridLayout(1, 2));
			pnlRobotSelection.add(lblCurrentRobot);
			pnlRobotSelection.add(comboRobotList);
			
			JPanel pnlRobotActions = new JPanel();
			pnlRobotActions.setLayout(new GridLayout(1, 3));
			pnlRobotActions.add(btnAddRobot);
			pnlRobotActions.add(btnEditRobot);
			pnlRobotActions.add(btnRefreshRobot);
			
			JPanel pnlTaskActions = new JPanel();
			pnlTaskActions.setLayout(new GridLayout(1, 3));
			pnlTaskActions.add(btnAdd);
			pnlTaskActions.add(btnEdit);
			pnlTaskActions.add(btnDelete);
						
//			JPanel pnlLeaderb = new JPanel();
//			pnlLeaderb.setLayout(new GridLayout(1,4));
//			pnlLeaderb.add(lblLeaderBoard);
//			pnlLeaderb.add(comboLeaderboardList);
			
			//Set up table
			tableModel = new TaskTableModel();
			tableTasks = new JTable(tableModel);
			tableTasks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tableTasks.setPreferredScrollableViewportSize(new Dimension(500, 500));
			tableTasks.setFillsViewportHeight(true);
			
			JScrollPane listScrollPane = new JScrollPane(tableTasks);
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlRobotSelection, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlRobotActions, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.HORIZONTAL;
			add(pnlTaskActions, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 20;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			c.gridheight = GridBagConstraints.REMAINDER;
			c.gridwidth = GridBagConstraints.REMAINDER;
			add(listScrollPane, c);
			
//			updateRobots();
		}

		/**
		 * Performs an action based on the given ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == comboRobotList) {
				int idx = comboRobotList.getSelectedIndex();
				
				if (idx == -1) {
					updateRobots();
				} else {				
					String robotName = comboRobotList.getItemAt(idx);
					manager.loadRobot(robotName); //TODO
				}
				updateRobots();
			} else if (e.getSource() == btnAdd) {
				//If the add button is clicked switch to the createTaskPanel
				pnlCreateTask.setRobotName("Associated Robot: " + manager.getRobotName()); //TODO We must ensure that this goes for the current robot 
				
				cardLayout.show(panel, CREATE_TASK_PANEL);
			} else if (e.getSource() == btnDelete) {
				//If the delete button is clicked, delete the task
				int row = tableTasks.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(StartGUI.this, "No task selected");
				} else {
					try {
//						int id = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
//						System.out.println(row);
						manager.deleteTaskById(row);
					} catch (NumberFormatException nfe ) {
						JOptionPane.showMessageDialog(StartGUI.this, "Invalid id");
					}
				}
				updateRobots();
			} else if (e.getSource() == btnEdit) {
				//If the edit button is clicked, switch panel
				int row = tableTasks.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(StartGUI.this, "No task selected");
				} 
				//If the add button is clicked switch to the createTaskPanel
				pnlCreateTask.setRobotName(manager.allTasks.get(row).getDescription()); //TODO We must ensure that this goes for the current robot 
				pnlCreateTask.setTaskEtaTime("" + manager.allTasks.get(row).getEstimatedTime());
				cardLayout.show(panel, CREATE_TASK_PANEL);
				updateRobots();
			} else if (e.getSource() == btnAddRobot) {
				try {
					comboRobotType = new JComboBox<String>(types);
					comboRobotType.setSelectedIndex(0);
					String robotType = (String) JOptionPane.showInputDialog(this, comboRobotType);
					String robotName = (String) JOptionPane.showInputDialog(this, "Robot Name?", "Create New Robot", JOptionPane.QUESTION_MESSAGE);
					
					manager.addRobot(robotName, robotType);
					manager.currentRobot.start();
//					RobotManager.getInstance().currentRobot.start();

					updateRobots();
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(StartGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnEditRobot) {
				try {
					String robotName = (String) JOptionPane.showInputDialog(this, "Update Robot Name?", "Edit Robot", JOptionPane.QUESTION_MESSAGE, null, null, "");
					manager.editRobot(robotName);
					updateRobots();
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(StartGUI.this, iae.getMessage());
				}
			} else if (e.getSource() == btnRefreshRobot) {
				try {
					updateRobots();
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(StartGUI.this, iae.getMessage());
				}
			}
			
			//On all paths, update the GUI!
			StartGUI.this.repaint();
			StartGUI.this.validate();
			updateRobots();
		}
		
		/**
		 * Updates the robot and task list.
		 */
		public void updateRobots() {
			tableModel.updateData();
			
			String robotName = RobotManager.getInstance().getRobotName();
			if (robotName == null) {
				robotName = "Create a Robot";
				btnAdd.setEnabled(false);
				btnDelete.setEnabled(false);
				btnEdit.setEnabled(false);
				btnAddRobot.setEnabled(true);
				btnEditRobot.setEnabled(false);
				btnRefreshRobot.setEnabled(false);
			} else {
				btnAdd.setEnabled(true);
				btnDelete.setEnabled(true);
				btnEdit.setEnabled(true);
				btnAddRobot.setEnabled(true);
				btnEditRobot.setEnabled(true);
				btnRefreshRobot.setEnabled(true);
			}
			
			comboRobotList.removeAllItems();
			ArrayList<Bot> robotList = RobotManager.getInstance().getRobotList();
			for (int i = 0; i < robotList.size(); i++) {
				comboRobotList.addItem(robotList.get(i).getBotName());
			}
			
			robotName = manager.getRobotName(); //Get name again
			if (robotName != null) {
				comboRobotList.setSelectedItem(robotName);
//				manager.currentRobot.start();
			} else {
				robotName = "Create a Robot";
			}
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Robot: " + robotName);
			setBorder(border);
			setToolTipText("Robot: " + robotName);
		}
		
		/**
		 * TaskTableModel is the object underlying the JTable object that displays
		 * the list of Tasks to the user.
		 * @author Mario Medel
		 */
		private class TaskTableModel extends AbstractTableModel {
			
			/** ID number used for object serialization. */
			private static final long serialVersionUID = 1L;
			/** Column names for the table */
			private String [] columnNames = {"Robot Name/Type", "Task Description", "Task ETA", "Completed"};
			/** Data stored in the table */
			private Object [][] data;
			
			/**
			 * Constructs the TaskTableModel by requesting the latest information
			 * from the RobotManager.
			 */
			public TaskTableModel() {
				updateData();
			}

			/**
			 * Returns the number of columns in the table.
			 * @return the number of columns in the table.
			 */
			public int getColumnCount() {
				return columnNames.length;
			}

			/**
			 * Returns the number of rows in the table.
			 * @return the number of rows in the table.
			 */
			public int getRowCount() {
				if (data == null) 
					return 0;
				return data.length;
			}
			
			/**
			 * Returns the column name at the given index.
			 * @param col column in the table
			 * @return the column name at the given column.
			 */
			public String getColumnName(int col) {
				return columnNames[col];
			}

			/**
			 * Returns the data at the given {row, col} index.
			 * @param row row in the table
			 * @param col column in the table
			 * @return the data at the given location.
			 */
			public Object getValueAt(int row, int col) {
				if (data == null)
					return null;
				return data[row][col];
			}
			
			/**
			 * Sets the given value to the given {row, col} location.
			 * @param value Object to modify in the data.
			 * @param row location to modify the data.
			 * @param col location to modify the data.
			 */
			public void setValueAt(Object value, int row, int col) {
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}
			
			/**
			 * Updates the given model with Task information from the BacklogManager.
			 */
			private void updateData() { 
				manager = RobotManager.getInstance(); //TODO
				data = manager.getTasksAsArray(); 
			}
		}
	}
	
	/**
	 * Inner class that creates the look and behavior for the JPanel that 
	 * allows for creation of a new task item.
	 * 
	 * @author Mario Medel
	 */
	private class CreateTaskPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		
		/** Label - Task Description */
		private JLabel lblDescription;
		/** JTextField - Robot */
		private JTextField txtDescription;
		/** Label for identifying title text field */
		private JLabel lblETA;
		/** Text field for entering title information */
		private JTextField txtETA;		
		/** Button to add a task item */
		private JButton btnAdd;
		/** Button for canceling add action */
		private JButton btnCancel;	
		/**
		 * Creates the JPanel for adding new task to the 
		 * backlog for the current product.
		 */
		public CreateTaskPanel() {
			super(new GridBagLayout());  
			
			//Construct widgets
			lblDescription = new JLabel("Description");
			txtDescription = new JTextField(15);
			
			lblETA = new JLabel("Time cost (units of MilliSeconds)");
			txtETA = new JTextField(15);

			btnAdd = new JButton("Add Task to Current Robot");
			btnCancel = new JButton("Cancel");
			
			//Adds action listeners
			btnAdd.addActionListener(this);
			btnCancel.addActionListener(this);
			
			GridBagConstraints c = new GridBagConstraints();
			
			//Row 0 - Description
			JPanel row0 = new JPanel();
			row0.setLayout(new GridLayout(1, 2));
			row0.add(lblDescription);
			row0.add(txtDescription);
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			c.gridheight = 1;
			c.gridwidth = 1;
			add(row0, c);
			
			//Row 1 - ETA
			JPanel row1 = new JPanel();
			row1.setLayout(new GridLayout(1, 2));
			row1.add(lblETA);
			row1.add(txtETA);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			c.gridheight = 1;
			c.gridwidth = 1;
			add(row1, c);
						
			//Row - Buttons
			JPanel row5 = new JPanel();
			row5.setLayout(new GridLayout(1, 2));
			row5.add(btnAdd);
			row5.add(btnCancel);
			
			c.gridx = 0;
			c.gridy = 8;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			c.gridheight = GridBagConstraints.REMAINDER;
			c.gridwidth = GridBagConstraints.REMAINDER;
			add(row5, c);
		}
		
		/**
		 * Sets the robots name
		 * @param robotName name of the product to display
		 */
		public void setRobotName(String robotName) { txtDescription.setText(robotName); }
		public void setTaskEtaTime(String taskEta) { txtETA.setText(taskEta); }
		/**
		 * Performs an action based on the given ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			boolean done = true; //Assume done unless error
			if (e.getSource() == btnAdd) {
//				//Add task to the list
				String description = txtDescription.getText();
				String eta = txtETA.getText();
				//Get instance of manager and add task
				try {
					manager.addTaskToRobot(description, eta);
				} catch (IllegalArgumentException exp) {
					done = false;
					JOptionPane.showMessageDialog(StartGUI.this, "Invalid task information.");
				}
			}
			

				
			 
			if (done) {
				//All buttons lead to back task list
				cardLayout.show(panel, ROBOT_PANEL);
				pnlRobot.updateRobots();
				StartGUI.this.repaint();
				StartGUI.this.validate();
				//Reset fields
				txtDescription.setText("");
				txtETA.setText("");
//				comboType.setSelectedIndex(0);
			}
		}
	}
}

