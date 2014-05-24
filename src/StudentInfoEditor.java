/**
 * Form for editing student information.
 * 
 * @author KAAT Solutions
 */
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class StudentInfoEditor extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private Student student;
	private StudentsListTab window;
	private JTextField studentIDField, firstNameField, lastNameField, assignedSeatField;
	private JComboBox raceSelection, genderSelection;
	private JScrollPane scrollbar;
	private JTable avoidingTable;
	private JTabbedPane tabs;
	
	/**
	 * Creates a small window for editing student information
	 * 
	 * @param window Parent window
	 * @param student Student to edit.
	 */
	public StudentInfoEditor(StudentsListTab window, Student student)
	{
		this.window = window;
		this.student = student;
		this.setTitle(student.getFirstName() + " " + student.getLastName());
		
		this.tabs = new JTabbedPane();
		
		this.avoidingTable = makeTable(student);
		this.scrollbar = new JScrollPane(this.avoidingTable);
		
		
		/*
		 * Separation Tab
		 */
		JPanel buttonsPane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton newStudentButton = new JButton("New");
		newStudentButton.setActionCommand("New Student");
		newStudentButton.addActionListener(this);
		buttonsPane.add(newStudentButton);

		JButton delStudentButton = new JButton("Delete");
		delStudentButton.setActionCommand("Delete Student");
		delStudentButton.addActionListener(this);
		buttonsPane.add(delStudentButton);
	
		JPanel separationPanel = new JPanel(new BorderLayout());
		separationPanel.add(scrollbar, BorderLayout.CENTER);
		separationPanel.add(buttonsPane, BorderLayout.PAGE_END);
		
		/*
		 * Information Editing Tab contents
		 */
		
		/*
		 * Name and student ID text fields for setting those of a student.
		 */
		JPanel studentIDSetter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel studentIDLabel = new JLabel("Student ID: ");
		this.studentIDField = new JTextField(Integer.toString(student.getStudentID()));
		this.studentIDField.setColumns(5);
		studentIDSetter.add(studentIDLabel);
		studentIDSetter.add(studentIDField);
		
		JPanel firstNameSetter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel firstNameLabel = new JLabel("First: ");
		this.firstNameField = new JTextField(student.getFirstName());
		this.firstNameField.setColumns(8);
		firstNameSetter.add(firstNameLabel);
		firstNameSetter.add(firstNameField);
		
		JPanel lastNameSetter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lastNameLabel = new JLabel("Last: ");
		this.lastNameField = new JTextField(student.getLastName());
		this.lastNameField.setColumns(8);
		lastNameSetter.add(lastNameLabel);
		lastNameSetter.add(lastNameField);
		
		/*
		 * Box to force assigned seating
		 */
		JPanel assignedSeatSetter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel assignedSeatLabel = new JLabel("Assigned Seat");
		this.assignedSeatField = new JTextField();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < this.window.currentClass.getQuadrants().get(i).getSize(); j++) {
				Desk desk = this.window.currentClass.getQuadrants().get(i).getDesk(j);
				if (desk.isEditable() == false && desk.getOccupyingStudent().equals(student)) {
					switch (i) {
						case 0:
							this.assignedSeatField = new JTextField(Integer.toString(j+1));
							break;
						case 1:
							this.assignedSeatField = new JTextField(Integer.toString(j+11));
							break;
						case 2:
							this.assignedSeatField = new JTextField(Integer.toString(j+23));
							break;
					}
				}
			}
		}
		this.assignedSeatField.setColumns(2);
		assignedSeatSetter.add(assignedSeatLabel);
		assignedSeatSetter.add(assignedSeatField);
		
		/*
		 * Box to set the race of a student.
		 */
		JPanel raceSetter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel raceNameLabel = new JLabel("Race: ");
		this.raceSelection = new JComboBox<String>(new String[] { "White", "Asian", "AfricanAmerican", "Latino", "PacificIslander", "Other"});
		this.raceSelection.setSelectedItem(student.getRace().toString());
		raceSetter.add(raceNameLabel);
		raceSetter.add(raceSelection);
		
		/*
		 * Box to set the Gender of a student
		 */
		JPanel genderSetter = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel genderLabel = new JLabel("Gender: ");
		this.genderSelection = new JComboBox<String>(new String[] { "Male", "Female", "Other" });
		this.genderSelection.setSelectedItem(student.getGender().toString());
		genderSetter.add(genderLabel);
		genderSetter.add(genderSelection);
		
		/*
		 * Bottom button pane with save/cancel buttons
		 */
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton saveChanges = new JButton("Save");
		saveChanges.addActionListener(this);
		saveChanges.setActionCommand("Save");
		
		JButton cancelChanges = new JButton("Cancel");
		cancelChanges.addActionListener(this);
		cancelChanges.setActionCommand("Cancel");
		
		buttonPane.add(saveChanges);
		buttonPane.add(cancelChanges);
		
		JPanel comboboxPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		comboboxPane.add(genderSetter);
		JPanel informationPanel = new JPanel();
		informationPanel.setLayout(new GridLayout(5, 2));
		informationPanel.add(studentIDSetter);
		informationPanel.add(assignedSeatSetter);
		informationPanel.add(firstNameSetter);
		informationPanel.add(lastNameSetter);
		informationPanel.add(genderSetter);
		informationPanel.add(raceSetter);
		informationPanel.add(new JPanel());
		informationPanel.add(buttonPane);
		
		tabs.addTab("Information", null, informationPanel);;
		tabs.addTab("Separation", null, separationPanel);
	
		this.add(tabs, BorderLayout.CENTER);
		
		this.setResizable(false);
		this.setSize(400, 320);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Renders the JTable of the students being avoided given a student.
	 * 
	 * @param student Student to render the avoiding list of
	 * @return JTable of the students being avoided.
	 */
	public JTable makeTable(Student student) 
	{
		String[] columns = { "Student ID", "First Name", "Last Name"};
		ArrayList<Student> avoidingList = student.getAvoiding();
		String[][] names = new String[avoidingList.size()][];
		
		for (int i = 0; i <  avoidingList.size(); i++) {
			Student avoiding = avoidingList.get(i);
			for (Student j : window.currentClass.getStudents()) {
				if (j.getStudentID() == avoiding.getStudentID()) {
					avoiding = j;
				}
			}
			names[i] = new String[] {Integer.toString(avoiding.getStudentID()), avoiding.getFirstName(), avoiding.getLastName()};
		}
		
		
		JTable avoidingTable = new JTable(new CustomTableModel(names, columns));
		
		/*
		 * Shrinking size of table
		 */
		avoidingTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		for (int i = 0; i < 3; i++) {
			avoidingTable.getColumnModel().getColumn(i).setMaxWidth(250);
		}
		
		/*
		 * Appearance Options
		 */
		avoidingTable.setDefaultRenderer(Object.class, new CustomTableRenderer());
		avoidingTable.setShowGrid(false);
		
		return avoidingTable;
	}
	
	/**
	 * Hack to fix refreshing issues with deleting from the avoiding
	 * list by creating a new window. Not my best work.
	 */
	private void refreshHack() 
	{
		
		StudentInfoEditor refreshTable = new StudentInfoEditor(this.window, this.student);
		refreshTable.getTabs().setSelectedIndex(this.getTabs().getSelectedIndex());
		refreshTable.setLocationRelativeTo(this);
		this.setVisible(false);
		this.dispose();
		
	}
	/**
	 * Handles buttons:
	 * 	- Adding/deleting students to be removed/added for the avoiding list
	 * 	- Canceling/saving changes
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{

		String actioncommand = e.getActionCommand();
		// TODO: New student button.
		if (actioncommand.equals("New Student")) {
			ArrayList<Student> options = new ArrayList<Student>();
			for (Student student : this.window.currentClass.getStudents()) {
				options.add(student);
			}
			options.remove(this.student);
			this.student.avoid((Student) JOptionPane.showInputDialog(this,
					"Select the student to avoid", "Student Avoiding",
					JOptionPane.PLAIN_MESSAGE, null, options.toArray(), null));
			refreshHack();
			
		} else if (actioncommand.equals("Delete Student")) {
			ArrayList<Student> toremove = new ArrayList<Student>();
			for (int selectedRow : avoidingTable.getSelectedRows()) {
				for (Student possibleStudents: this.student.getAvoiding()) {
					if (Integer.toString(possibleStudents.getStudentID()).equals(((String)avoidingTable.getModel().getValueAt(selectedRow,0)))) {
						toremove.add(possibleStudents);
					}
				}
			}
			for (Student removedstudent : toremove) {
				this.student.getAvoiding().remove(removedstudent);
			}
			refreshHack();
			
		} else if (actioncommand.equals("Cancel")) {
			this.setVisible(false);
			this.dispose();
		} else if (actioncommand.equals("Save")) {
			this.student.setStudentID(Integer.parseInt(this.studentIDField.getText()));
			this.student.setFirstName(this.firstNameField.getText());
			this.student.setLastName(this.lastNameField.getText());
			this.student.setGender((String) this.genderSelection.getSelectedItem());
			this.student.setRace((String) this.raceSelection.getSelectedItem());
			
			/*
			 * Handle assigned seating
			 */
			if (this.assignedSeatField.getText().matches("-?\\d+")) {
				int assignedSeat = Integer.parseInt(this.assignedSeatField.getText()) - 1;
				ClassRoom classRoom = window.getCurrentClass();
				
				ArrayList<Section> sections = classRoom.getQuadrants();
				for (Section section : sections) {
					for (int i = 0; i < section.getSize(); i++) {
						Desk desk = section.getDesk(i);
						if (desk.getOccupyingStudent() != null && desk.getOccupyingStudent() == this.student){
							desk.empty();
							desk.setEditable(true);
						}
					}
				}
				
				if (assignedSeat < 0 || assignedSeat >= 32) {
					JOptionPane.showMessageDialog(this, "Invalid Seat Number. \nLeave it blank if you don't want the student to be assigned a seat.");
				} else if (assignedSeat < 10) {
					Desk desk = sections.get(0).getDesk(assignedSeat);
					desk.setEditable(false);
					desk.occupy(this.student);
				} else if (assignedSeat < 22) {
					Desk desk = sections.get(1).getDesk(assignedSeat - 10);
					desk.setEditable(false);
					desk.occupy(this.student);
				} else if (assignedSeat < 32) {
					Desk desk = sections.get(2).getDesk(assignedSeat - 22);
					desk.setEditable(false);
					desk.occupy(this.student);
				}
			} else {
				ArrayList<Section> sections = this.window.getCurrentClass().getQuadrants();
				for (Section section : sections) {
					for (int i = 0; i < section.getSize(); i++) {
						Desk desk = section.getDesk(i);
						if (desk.getOccupyingStudent() != null && desk.getOccupyingStudent().equals(this.student) && desk.isEditable() == false) {
							desk.empty();
							desk.setEditable(true);
						}
					}
				}
			}
			for (Section section : window.currentClass.getQuadrants()) {
				for (int i = 0; i < section.getSize(); i++) {
					if (section.getDesk(i).isEditable() == true) {
						section.getDesk(i).empty();
					}
				}
			}
			this.setEnabled(false);
			this.dispose();
		}
		this.window.refresh();
	}
	
	/**
	 * Gets the tab object for the separation and information panels-- hack for fixing a refreshing bug
	 * 
	 * @return JTabbedPane containing the separation and information panels
	 */
	public JTabbedPane getTabs() {
		return this.tabs;
	}
}
