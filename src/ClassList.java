/**
 * Renders a majority of the Graphical User Interface:
 * 	- The class selection panel
 * 	- The tabs
 * 
 * @author KAAT Solutions
 */
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ClassList extends JPanel implements ActionListener, ItemListener
{
	private static final long serialVersionUID = 1L;
	private ClassRoom currentClass;
	private Main main;
	private String classesDirectory;
	private JComboBox classSelection;
	
	/**
	 * Creates a new classroom, and renders a class list of it.
	 */
	public ClassList(Main main) 
	{
		this(new ClassRoom());
		this.main = main;
	}

	/**
	 * Renders a class list given a classroom
	 * @param classRoom classroom to work off of 
	 */
	public ClassList(ClassRoom classRoom)
	{
		this.currentClass = classRoom;
		this.refresh();
	}
	
	/**
	 * Sets the current classroom to viewed
	 * @param classroom Classroom
	 */
	public void setCurrentClass(ClassRoom classroom) 
	{
		this.currentClass = classroom;		
	}
	
	/**
	 * Updates the ClassList following changes
	 */
	@SuppressWarnings("unchecked")
	public void refresh()
	{
		this.removeAll();
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JPanel topPanel = new JPanel(new GridLayout(5, 1));
		
		this.classSelection = new JComboBox(getClassList());
		
		classSelection.setEditable(false);
		classSelection.addItemListener(this);
		classSelection.putClientProperty("JComponent.sizeVariant", "mini");
		
		topPanel.add(classSelection);
		topPanel.add(new JPanel());
		JButton saveButton = new JButton("Save");
		topPanel.add(saveButton);
		saveButton.setActionCommand("Save Class");
		saveButton.addActionListener(this);
		
		JButton addClassButton = new JButton("New");
		topPanel.add(addClassButton);
		addClassButton.setActionCommand("New Class");
		addClassButton.addActionListener(this);

		JButton removeClassButton = new JButton("Delete");
		topPanel.add(removeClassButton);
		removeClassButton.setActionCommand("Delete Class");
		removeClassButton.addActionListener(this);
		topPanel.setBorder(BorderFactory.createTitledBorder("Class Rooms"));
		this.add(topPanel);
		this.revalidate();
	}

	/**
	 * Opens the list of classes from the save file.
	 * 
	 * @return String array of the names of the classes.
	 */
	public ClassRoom[] getClassList() 
	{
		this.classesDirectory = System.getProperty("user.home") + System.getProperty("file.separator") + "SeatingChart";
		return getClassList(classesDirectory);
	}
	
	/**
	 * Opens the list of classes from the save file
	 * 
	 * @param directory Directory where all of the classes are stored
	 * @return String array of the names of the classes.
	 */
	public ClassRoom[] getClassList(String directory)
	{
		
		File saveFiles = new File(directory);
		if (saveFiles.exists() == false) {
			File hack = new File(saveFiles.getAbsolutePath() + System.getProperty("file.separator") + "-");
			hack.mkdirs();
			File hackstudentscsv = new File(hack.getAbsolutePath() + System.getProperty("file.separator") + "Students.csv");
			try { hackstudentscsv.createNewFile(); } catch (Exception e) { e.printStackTrace(); }
		}
		String[] ls = saveFiles.list();
		
		/*
		 * Forcing our hack to be the first one on the list of classrooms
		 */
		ClassRoom[] classrooms = new ClassRoom[ls.length];
		classrooms[0] = new ClassRoom(directory + System.getProperty("file.separator") + "-");
		
		int index = 1;
		for (int i = 0; i < ls.length; i++) {
			if (!ls[i].equals("-")) {
				classrooms[index++] = new ClassRoom(directory + System.getProperty("file.separator") + ls[i]);
			}
		}
		
		return classrooms;
	}

	/**
	 * Handles the combo box to select different classrooms.
	 */
	public void itemStateChanged(ItemEvent e) 
	{
		JComboBox source = (JComboBox) e.getItemSelectable();
		ClassRoom selected = (ClassRoom) source.getSelectedItem();
		main.changeClass(selected);
	}

	/**
	 * Handles creation and deletion of classrooms.
	 */
	public void actionPerformed(ActionEvent e) 
	{
		String actioncommand = e.getActionCommand();
		
		if (actioncommand.equals("New Class")) {
			ClassRoom newclassroom = new ClassRoom();
			String name = (String) JOptionPane.showInputDialog(main, "Enter the new Classroom's name");
			if (name != null) {
				File file = new File(this.classesDirectory + System.getProperty("file.separator")  + name);
				if (!file.exists()) {
					file.mkdir();
				} else {
					JOptionPane.showMessageDialog(main, "Classroom already exists! Please use another name");
				}
				newclassroom.save(file);
				main.changeClass(newclassroom);
				this.refresh();
			}
			
		} else if (actioncommand.equals("Delete Class")) {
			/*
			 * Hack to have an empty entry by default.
			 */
			if (!this.classSelection.getSelectedItem().toString().equals("-")) {
				File deletedClass = new File(((ClassRoom) this.classSelection.getSelectedItem()).getSaveDirectory());
				for (File file : deletedClass.listFiles()) {
					file.delete();
				}
				deletedClass.delete();
			}
			this.refresh();
		} else if (actioncommand.equals("Save Class")) {
			this.save();
		}
	}

	/**
	 * Creates the tab pane of the current Class room
	 * 
	 * @return A JTabbedPane with the seating chart and students list
	 */
	public JTabbedPane getCurrentClassWindow()
	{
		JTabbedPane window = new JTabbedPane();
		
		window.addTab("Seating Chart",
				new ImageIcon(getClass().getResource("img/deskicon.png")),
				new SeatingChartTab(currentClass));

		window.addTab("Students",
				new ImageIcon(getClass().getResource("img/studenticon.png")),
				new StudentsListTab(currentClass));

		return window;
	}

	/**
	 * Gets the currently selected classrooms.
	 * 
	 * @return ClassRoom object of the currently selected classroom
	 */
	public ClassRoom getClassRoom() 
	{
		return this.currentClass;
	}
	
	/**
	 * Gets the directory where the list of classes is saved.
	 * 
	 * @return Directory of the list of classes
	 */
	public String getClassesDirectory() 
	{
		return this.classesDirectory;
	}
	
	/**
	 * Saves all changes to a classroom from the class list.
	 */
	public void save()
	{
		ClassRoom classroom = this.getClassRoom();
		if (classroom.getSaveDirectory() == null) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.showOpenDialog(this);
			File file = fc.getSelectedFile();
			if (file != null) {
				classroom.save(file);
			}
		} else {
			classroom.save(new File(classroom.getSaveDirectory()));
		}
	}
}
