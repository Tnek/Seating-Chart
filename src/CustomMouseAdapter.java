/**
 * Special Mouse Adapter for the creation of Student Info Editor windows when
 * an entry on the table is double-clicked.
 */
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;

public class CustomMouseAdapter extends MouseAdapter 
{
	private StudentsListTab studentsList;
	private JTable table;
	
	/**
	 * Initializes the mouse adapter with a table and a parent window
	 * 
	 * @param table Table of students.
	 * @param studentsList Parent window
	 */
	public CustomMouseAdapter(JTable table, StudentsListTab studentsList) 
	{
		this.studentsList = studentsList;
		this.table = table;
	}
	
	/**
	 * Opens a new student editor when a student on the table is clicked.
	 */
	public void mousePressed(MouseEvent e) 
	{
		if (e.getClickCount() == 2) {
			Student selectedStudent = studentsList.getCurrentClass()
					.findStudent(Integer.parseInt((String) table.getModel().getValueAt(
							this.table.getSelectedRow(), 0)));
			new StudentInfoEditor(studentsList, selectedStudent);
		}
	}
}