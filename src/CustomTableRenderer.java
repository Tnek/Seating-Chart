/**
 * Custom Table Renderer. 
 * Renders table with rows of alternating colors, and the user clicking on a 
 * row selects the entire row.
 * 
 * @author KAAT Solutions
 */
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class CustomTableRenderer implements TableCellRenderer 
{
	private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	public CustomTableRenderer() 
	{
		super();
	}

	/**
	 * Causes the table cells to alternate in color, with a selected row being 
	 * highlighted blue.
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) 
	{
		Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);
		if (isSelected) {
			c.setBackground(Color.decode("#87CEFA"));
		} else if (row % 2 == 0) {
			c.setBackground(Color.WHITE);
		} else {
			c.setBackground(Color.decode("#E5E4E2"));
		}
		return c;
	}
}