/**
 * Renders Menu Bar.
 * 
 * @author KAAT Solutions
 */
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class MenuBar extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	private Main mainwindow;
	
	/**
	 * Renders the menu bar
	 * @param mainwindow Parent window.
	 */
	public MenuBar(Main mainwindow) 
	{
		this.mainwindow = mainwindow;
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		JMenuItem open = new JMenuItem("Open File");
		open.addActionListener(mainwindow);
		open.setActionCommand("open");
		open.setMnemonic(KeyEvent.VK_O);
		fileMenu.add(open);
		
		fileMenu.add(new JSeparator());
		
		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(mainwindow);
		save.setActionCommand("save");
		save.setMnemonic(KeyEvent.VK_S);
		fileMenu.add(save);
		JMenuItem saveas = new JMenuItem("Save As");
		saveas.addActionListener(mainwindow);
		saveas.setActionCommand("saveas");
		saveas.setMnemonic(KeyEvent.VK_A);
		fileMenu.add(saveas);
		
		fileMenu.add(new JSeparator());

		JMenu helpMenu = new JMenu("Help");
		JMenuItem about = new JMenuItem("About");
		about.setActionCommand("About");
		about.addActionListener(mainwindow);
		helpMenu.add(about);

		this.add(fileMenu);
		this.add(helpMenu);
	}

	/**
	 * Gets the parent window of the menu bar
	 * 
	 * @return Parent window of the menu bar
	 */
	public Main getMainwindow() 
	{
		return mainwindow;
	}
}
