/**
 * Renders list of Students for the user to edit.
 * 
 * @author KAAT Solutions
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Tab with the list of Students
 * 
 * @author KAAT Solutions
 */
public class StudentsListTab extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	public ClassRoom currentClass;
	private JTable storedtable;
	private JScrollPane studentTable;
	
	/**
	 * Initializes student information viewer/editor of a classroom.
	 * 
	 * @param currentClass Classroom to display
	 */
	public StudentsListTab(ClassRoom currentClass) 
	{
		this.setLayout(new BorderLayout());
		this.currentClass = currentClass;
		this.refresh();
	}
	
	/**
	 * Generates a JTable of a class room
	 * 
	 * @param currentClass classroom to generate the JTable of
	 * @return Generated JTable of the given classroom
	 */
	public JTable makeTable(ClassRoom currentClass)
	{
		/*
		 * Creating the data entries of the table
		 */
		String[] columns = {"Student ID", "First Name", "Last Name", ""};
		ArrayList<String[]> names = new ArrayList<String[]>();
		for (int i = 0; i < currentClass.getStudents().size(); i++) {
			Student j = currentClass.getStudents().get(i);
			names.add(new String[] { Integer.toString(j.getStudentID()), j.getFirstName(), j.getLastName() });
		}
		String[][] data = new String[currentClass.getStudents().size()][];
		for (int i = 0; i < names.size(); i++) {
			data[i] = names.get(i);
		}
		
		JTable table = new JTable(new CustomTableModel(data, columns));
		
		/*
		 * Appearance settings for the table
		 */
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setDefaultRenderer(Object.class, new CustomTableRenderer());
		/*
		 * Resizing the table columns to be a nice size.
		 */
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(100);

		for (int i = 1; i < 3; i++) {
			table.getColumnModel().getColumn(i).setMinWidth(100);
			table.getColumnModel().getColumn(i).setMaxWidth(350);

		}
		table.setShowGrid(false);
		table.setRowSelectionAllowed(true);
		
		/*
		 * Event when student entries are clicked.
		 */
		table.addMouseListener(new CustomMouseAdapter(table, this));
		
		table.getTableHeader().setReorderingAllowed(false);
		return table;
	}
	
	/**
	 * Re-renders the table for new changes
	 */
	public void refresh() 
	{
		this.removeAll();
		this.storedtable = makeTable(this.currentClass);
		studentTable = new JScrollPane(storedtable);
		this.add(studentTable, BorderLayout.CENTER);
		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton newStudentButton = new JButton("New Student");
		newStudentButton.setActionCommand("New Student");
		newStudentButton.addActionListener(this);
		buttonsPane.add(newStudentButton);

		JButton delStudentButton = new JButton("Delete Student");
		delStudentButton.setActionCommand("Delete Student");
		delStudentButton.addActionListener(this);
		buttonsPane.add(delStudentButton);

		this.add(buttonsPane, BorderLayout.PAGE_END);
		this.revalidate();
	}
	
	/**
	 * Gets the classroom currently displayed on this tab
	 * 
	 * @return ClassRoom object of the current class
	 */
	public ClassRoom getCurrentClass()
	{
		return this.currentClass;
	}

	/**
	 * Handles creation/deletion of students
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String actioncommand = e.getActionCommand();
		if (actioncommand.equals("New Student")) {
			Student student = new Student();
			new StudentInfoEditor(this, student);
			currentClass.getStudents().add(student);
		} else if (actioncommand.equals("Delete Student")) {
			ArrayList<Student> toremove = new ArrayList<Student>();
			for (int i : storedtable.getSelectedRows()) {
				for (Student student : currentClass.getStudents()) {
					if (Integer.toString(student.getStudentID()).equals(((String)storedtable.getModel().getValueAt(i,0)))) {
						toremove.add(student);
					}
				}
			}
			for (Student student : toremove) {
				currentClass.getStudents().remove(student);
				for (Section section : currentClass.getQuadrants()) {
					for (int i = 0; i < section.getSize(); i++) {
						Desk desk = section.getDesk(i);
						if (desk.isEditable() == false && desk.getOccupyingStudent().equals(student)) {
							desk.setEditable(true);
						}
						section.getDesk(i).delete(student);
					}
				}
				this.currentClass.getStudents().remove(student);
			}
		}
		this.refresh();
	}
}
