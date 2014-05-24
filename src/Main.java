/**
 * Main window of the application
 * 
 * @author KAAT Solutions
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private MenuBar menubar;
	private ClassList classlist;
	private JTabbedPane currentClassWindow;
	
	public static void main(String args[]) 
	{
		/*
		 * Use nimbus look and feel if possible.
		 */
	    try {
	    	for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
	    		 if ("Nimbus".equals(info.getName())) {
	    			 UIManager.setLookAndFeel(info.getClassName());
	    			 break;
	    		 }
	    	}
	    } catch (Exception e) {
	    	try {
	    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    	} catch (Exception e2) {
	    		e2.printStackTrace();
	    	}
	    	e.printStackTrace();
	    }
	    
		Main main = new Main();
		main.setTitle("Seating Chart");
		main.render();
		main.setSize(1024, 768 - 64);
		main.setLocationRelativeTo(null);
		main.setVisible(true);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Renders the main window
	 */
	public void render()
	{
		this.menubar = new MenuBar(this); 
		this.add(menubar, BorderLayout.PAGE_START);
		if (this.classlist == null) {
			this.classlist = new ClassList(this);
		}
		
		this.add(classlist, BorderLayout.LINE_START);
		currentClassWindow = classlist.getCurrentClassWindow();
		this.add(currentClassWindow);
	}
	
	/**
	 * Changes the class currently viewed.
	 * 
	 * @param classroom New classroom
	 */
	public void changeClass(ClassRoom classroom)
	{
		classlist.setCurrentClass(classroom);
		int selectedindex = currentClassWindow.getSelectedIndex();
		
		this.remove(currentClassWindow);
		this.currentClassWindow = classlist.getCurrentClassWindow();
		currentClassWindow.setSelectedIndex(selectedindex);
		
		this.add(currentClassWindow);
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * Handles selected options in the menu bar.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		String actioncommand = e.getActionCommand();
		if (actioncommand.equals("open")) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.showOpenDialog(this);
			File file = fc.getSelectedFile();
			if (file != null) {
				this.changeClass(new ClassRoom(file));
			}
			
		} else if (actioncommand.equals("save")) {
			ClassRoom classroom = this.classlist.getClassRoom();
			if (classroom.getSaveDirectory() == null) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showOpenDialog(this);
				File file = fc.getSelectedFile();
				if (file != null) {
					this.classlist.getClassRoom().save(file);
				}
			} else {
				classroom.save(new File(classroom.getSaveDirectory()));
			}
		
		} else if (actioncommand.equals("saveas")) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.showOpenDialog(this);
			File file = fc.getSelectedFile();
			if (file != null) {
				this.classlist.getClassRoom().save(file);
			}
			
		} else if (actioncommand.equals("About")) {
			JFrame aboutFrame = new JFrame("About"); 
			String about = "<html><div align=center>(c) 2014 By Kent Ma, Trishul Nagenalli, Abby Beeler, and Aradhana Vyas<br>Email: KAATSolutions@gmail.com</div></html>";
			JLabel aboutLabel = new JLabel(about);
			JLabel aboutLogo = new JLabel(new ImageIcon(getClass().getResource(
					"img/logo.png")));
			aboutFrame.add(aboutLogo, BorderLayout.CENTER);
			aboutFrame.add(aboutLabel, BorderLayout.PAGE_END);

			aboutFrame.pack();
			aboutFrame.setLocationRelativeTo(null);
			aboutFrame.setVisible(true);
			aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
	}
}
