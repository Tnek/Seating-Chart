/**
 * Graphically renders the arrangement of the seats in the seating chart.
 *  
 * @author KAAT Solutions
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class SeatingChart extends JPanel 
{
	private static final long serialVersionUID = 1L;
	private ClassRoom classroom;
	
	/**
	 * Creates the base panel
	 * 
	 * @param classroom Classroom to generate the seating chart of
	 */
	public SeatingChart(ClassRoom classroom)
	{
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.classroom = classroom;
		this.setBackground(Color.decode("#F8F8F8"));
	}
	
	/**
	 * Paints the seat
	 */
	@Override 
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		ArrayList<Section> sections = this.classroom.getQuadrants();
		Section q1 = sections.get(0);
		Section q2 = sections.get(1);
		Section q3 = sections.get(2);
		/*
		 * Sets the thickness of the desks
		 */
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(3));
		int[] dimensions = {(int) this.getSize().getWidth(), (int) this.getSize().getHeight() };
		int squareDimensions = Math.min(dimensions[0],  dimensions[1]) / 8;
		
		int xOffset = 0, yOffset = -squareDimensions/2;
		/*
		 * Draws outer loop of desks
		 */
		
		for (int i = 1; i < 8; i++) {
			g2d.setColor(Color.decode("#E8E8E8"));
			g2d.fill(new Rectangle2D.Double((squareDimensions) + xOffset, squareDimensions*i + yOffset, squareDimensions, squareDimensions));
			g2d.fill(new Rectangle2D.Double((squareDimensions * 9) + xOffset, squareDimensions*i + yOffset, squareDimensions, squareDimensions));
			g2d.setColor(Color.decode("#C4C4C4"));
			g2d.draw(new Rectangle2D.Double((squareDimensions) - 1 + xOffset, squareDimensions*i + yOffset, squareDimensions, squareDimensions));
			
			g2d.draw(new Rectangle2D.Double((squareDimensions * 9) + xOffset, squareDimensions*i + yOffset, squareDimensions, squareDimensions));
			
		}
		for (int i = 1; i < 10; i++) {
			if (i != 5) {
				g2d.setColor(Color.decode("#E8E8E8"));
				g2d.fill(new Rectangle2D.Double((squareDimensions*i) + xOffset, squareDimensions*7 + yOffset, squareDimensions, squareDimensions));
				g2d.setColor(Color.decode("#C4C4C4"));
				g2d.draw(new Rectangle2D.Double((squareDimensions*i) + xOffset, squareDimensions*7 + yOffset, squareDimensions, squareDimensions));
			}
		}
		
		/*
		 * Draw inner loop of desks
		 */
		 
		for (int i = 0; i < 4; i++) {
			g2d.setColor(Color.decode("#E8E8E8"));
			g2d.fill(new Rectangle2D.Double((squareDimensions*(i+3.5)) + xOffset, squareDimensions*5 + yOffset, squareDimensions, squareDimensions));
			g2d.setColor(Color.decode("#C4C4C4"));
			
			g2d.draw(new Rectangle2D.Double((squareDimensions*(i+3.5)) + xOffset, squareDimensions*5 + yOffset, squareDimensions, squareDimensions));
		}
		
		for (int i = 0; i < 4; i++) {
			g2d.setColor(Color.decode("#E8E8E8"));
			g2d.fill(new Rectangle2D.Double((squareDimensions * 4) + xOffset, squareDimensions*(i+1) + yOffset, squareDimensions, squareDimensions));
			g2d.fill(new Rectangle2D.Double((squareDimensions * 6) + xOffset, squareDimensions*(i+1) + yOffset, squareDimensions, squareDimensions));
			g2d.setColor(Color.decode("#C4C4C4"));
			
			g2d.draw(new Rectangle2D.Double((squareDimensions * 4) + xOffset, squareDimensions*(i+1) + yOffset, squareDimensions, squareDimensions));
			g2d.draw(new Rectangle2D.Double((squareDimensions * 6) + xOffset, squareDimensions*(i+1) + yOffset, squareDimensions, squareDimensions));
		}
		g2d.setColor(Color.BLACK);
		
		/*
		 * Populating the seats based on generated seating data
		 * TODO: Line wrapping of long names with g2d.getFontMetrics().stringWidth
		 */
		
		int line = 15;
		
		/*
		 * Fills quadrant 1
		 */
		for (int i = 1; i <= q1.getSize(); i++) {
			Student student = q1.getDesk(i - 1).getOccupyingStudent();
			/*
			 * Add numerical labels
			 */
			g2d.setFont(new Font("default", Font.BOLD, 9));
			if (i < 8) {
				g2d.drawString(Integer.toString(i), squareDimensions + 5 + xOffset, squareDimensions * (i+1) - 5 + yOffset);
			} else {
				g2d.drawString(Integer.toString(i), squareDimensions* (i - 6) + 5 + xOffset, squareDimensions*8 - 5 + yOffset);
			}
			g2d.setFont(new Font("default", Font.PLAIN, 12));
			if (student != null) {
				if (i < 8) {
					g2d.drawString(student.getLastName()+",", squareDimensions + 5 + xOffset, squareDimensions * i + (squareDimensions / 2) + yOffset);
					g2d.drawString(student.getFirstName(), squareDimensions + 5 + xOffset, squareDimensions * i + (squareDimensions / 2) + line + yOffset);
				} else {
					g2d.drawString(student.getLastName()+",", squareDimensions* (i - 6) + 5 + xOffset, squareDimensions*7 + (squareDimensions / 2) + yOffset);
					g2d.drawString(student.getFirstName(), squareDimensions*(i - 6) + 5 + xOffset, squareDimensions*7 + (squareDimensions / 2) + line + yOffset);
				}
			}
		}
		
		/*
		 * Fills in quadrant 2
		 */
		for (int i = 1; i <= q2.getSize(); i++) {
			Student student = q2.getDesk(i - 1).getOccupyingStudent();
			/*
			 * Add numerical labels
			 */
			g2d.setFont(new Font("default", Font.BOLD, 9));
			if (i <= 4) {
				g2d.drawString(Integer.toString(i+10), squareDimensions*4 + 5 + xOffset, squareDimensions * (i+1) - 5 + yOffset);
			} else if (i <= 8) {
				g2d.drawString(Integer.toString(i+10), squareDimensions*(i - 5) + (squareDimensions*4) - (squareDimensions/2) + 5 + xOffset, squareDimensions * 6 - 5 + yOffset);
			} else if (i <= 12) {
				g2d.drawString(Integer.toString(i+10), squareDimensions*6 + 5 + xOffset, squareDimensions * (14 - i) - 5 + yOffset);
			}
			g2d.setFont(new Font("default", Font.PLAIN, 12));
			if (student != null) {
				if (i <= 4) {
					g2d.drawString(student.getLastName()+",", squareDimensions*4 + 5 + xOffset, squareDimensions * i + (squareDimensions / 2) + yOffset);
					g2d.drawString(student.getFirstName(), squareDimensions*4 + 5 + xOffset, squareDimensions * i + line + (squareDimensions / 2) + yOffset);
				} else if (i <= 8) {
					g2d.drawString(student.getLastName()+",", squareDimensions*(i - 5) + (squareDimensions*4) - (squareDimensions/2) + 5 + xOffset, squareDimensions * 5 + (squareDimensions / 2) + yOffset);
					g2d.drawString(student.getFirstName(), squareDimensions*(i - 5) + (squareDimensions*4) - (squareDimensions/2) + 5 + xOffset, squareDimensions * 5 + line + (squareDimensions / 2) + yOffset);
					
				} else if (i <= 12) {
					g2d.drawString(student.getLastName()+",", squareDimensions*6 + 5 + xOffset, squareDimensions * (13 - i) + (squareDimensions / 2) + yOffset);
					g2d.drawString(student.getFirstName(), squareDimensions*6 + 5 + xOffset, squareDimensions * (13 - i) + line + (squareDimensions / 2) + yOffset);
				}
			}
		}
		
		/*
		 * Fills quadrant 3
		 */
		for (int i = 1; i <= q3.getSize(); i++) {
			Student student = q3.getDesk(i - 1).getOccupyingStudent();
			/*
			 * Add numerical labels
			 */
			g2d.setFont(new Font("default", Font.BOLD, 9));
			if (i < 8) {
				g2d.drawString(Integer.toString(i+22), squareDimensions*9 + 5 + xOffset, squareDimensions * (i+1) - 5 + yOffset);
			} else {
				g2d.drawString(Integer.toString(i+22), squareDimensions* (16 - i) + 5 + xOffset, squareDimensions*8 - 5 + yOffset);
			}
			g2d.setFont(new Font("default", Font.PLAIN, 12));
			if (student != null) {
				if (i < 8) {
					g2d.drawString(student.getLastName()+",", squareDimensions*9 + 5 + xOffset, squareDimensions * i + (squareDimensions / 2) + yOffset);
					g2d.drawString(student.getFirstName(), squareDimensions*9 + 5 + xOffset, squareDimensions * i + (squareDimensions / 2) + line + yOffset);
				} else {
					g2d.drawString(student.getLastName()+",", squareDimensions* (16-i) + 5 + xOffset, squareDimensions*7 + (squareDimensions / 2) + yOffset);
					g2d.drawString(student.getFirstName(), squareDimensions*(16-i) + 5 + xOffset, squareDimensions*7 + (squareDimensions / 2) + line + yOffset);
				}
			}
		}
		g2d.setStroke(oldStroke);
	}
	/**
	 * Sets a new classroom to render
	 * 
	 * @param newClass New classroom
	 */
	public void setClassRoom(ClassRoom newClass) 
	{
		this.classroom = newClass;
	}
	
	/**
	 * Gets the classroom currently being rendered
	 * 
	 * @return Current classroom
	 */
	public ClassRoom getClassRoom()
	{
		return this.classroom;
	}
}
