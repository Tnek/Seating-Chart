/**
 * Renders the window displaying the list of students in a table.
 * 
 * @author KAAT Solutions
 */
import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel 
{
	private static final long serialVersionUID = 1L;
	public CustomTableModel(Object[][] data, Object[] columns)
	{
		super(data, columns);
	}
	
	/**
	 * Disables ability to edit the table.
	 */
	public boolean isCellEditable(int row, int column) 
	{
		return false;
	}
}