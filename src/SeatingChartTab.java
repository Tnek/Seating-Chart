/**
 * Holds the seating chart, as well as buttons for working with the seating chart
 * 
 * @author KAAT Solutions
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SeatingChartTab extends JPanel implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	public SeatingChart seatingchart;
	
	/**
	 * Creates the seating chart tab
	 * 
	 * @param currentClass Classroom to create the seating chart out of.
	 */
	public SeatingChartTab(ClassRoom currentClass) 
	{
		this.setLayout(new BorderLayout());
		this.seatingchart = new SeatingChart(currentClass);
		this.setClassRoom(currentClass);
		this.add(this.seatingchart);
		JPanel buttonsPane = new JPanel();
		buttonsPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton generateSeatingButton = new JButton("New Chart");
		generateSeatingButton.setActionCommand("Generate");
		generateSeatingButton.addActionListener(this);
		buttonsPane.add(generateSeatingButton);
		
		this.add(buttonsPane, BorderLayout.PAGE_END);
	}
	
	/**
	 * Sets a new classroom to be rendered for the seating chart.
	 * 
	 * @param classroom Classroom to be rendered for the seating chart.
	 */
	public void setClassRoom(ClassRoom classroom)
	{
		this.remove(this.seatingchart);
		this.seatingchart = new SeatingChart(classroom);
		this.add(this.seatingchart);
		validate();
		repaint();
	}
	
	/**
	 * Handles the button to generate a new seating chart
	 */
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand().equals("Generate")) {
			this.seatingchart.getClassRoom().generateSeatingChart();
			this.seatingchart.validate();
			this.seatingchart.repaint();
		}
	}
}
