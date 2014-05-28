/**
 * Simulates the Classroom.
 *
 * @author KAAT Solutions
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class ClassRoom 
{
	private String name;
	private ArrayList<Student> students;
	private ArrayList<Section> quadrants;
	private String saveDirectory;

	/**
	 * Creates a new class room.
	 */
	public ClassRoom() 
	{
		this.students = new ArrayList<Student>();
		newQuadrants();
	}

	/**
	 * Opens the save file of a saved class room.
	 * 
	 * @param saveFilesDirectory
	 *            Directory of the save files of the class room
	 */
	public ClassRoom(String classRoomDirectory) 
	{
		String[] tokens = classRoomDirectory.split("\\" + System.getProperty("file.separator"));
		this.name = tokens[tokens.length - 1];
		
		students = new ArrayList<Student>();
		
		this.saveDirectory = classRoomDirectory;
		readStudentListFromFile(classRoomDirectory + System.getProperty("file.separator") + "Students.csv");
		initializeQuadrantsFromFile(classRoomDirectory + System.getProperty("file.separator") + "Desks.csv");
	}

	/**
	 * Creates fresh, new quadrants
	 */
	public void newQuadrants() 
	{
		this.quadrants = new ArrayList<Section>();
		this.quadrants.add(new Section(10));
		this.quadrants.add(new Section(12));
		this.quadrants.add(new Section(10));
	}

	/**
	 * Opens the save file of a saved class room.
	 * 
	 * @param File
	 *            File object having opening the save file directory
	 */
	public ClassRoom(File file) 
	{
		students = new ArrayList<Student>();
		
		this.saveDirectory = file.getAbsolutePath();
		readStudentList(new File(file, "Students.csv"));
		initializeQuadrantsFromFile(new File(file, "Desks.csv"));
	}

	/**
	 * Initializes the Desks in the classroom with save file data of a previous
	 * seating arrangement.
	 * 
	 * See Desks.csv.format for how this save file is structured.
	 * 
	 * @param desksDirectory
	 *            Directory of the save file of the desks
	 */
	private void initializeQuadrantsFromFile(String deskDirectory) 
	{
		initializeQuadrantsFromFile(new File(deskDirectory));
	}

	/**
	 * Initializes the Desks in the classroom with save file data of a previous
	 * seating arrangement.
	 * 
	 * See Desks.csv.format for how this save file is structured.
	 * 
	 * @param deskFile
	 *            File object of the save file of the desks
	 */
	private void initializeQuadrantsFromFile(File deskFile) 
	{
		if (deskFile.exists() == false) {
			newQuadrants();
		} else {
			quadrants = new ArrayList<Section>();
			BufferedReader FILE;

			try {
				String currentLine;
				FILE = new BufferedReader(new FileReader(deskFile));
				while ((currentLine = FILE.readLine()) != null) {
					int numdesks = Integer.parseInt(currentLine);
					Section quadrant = new Section(numdesks);

					/*
					 * Parse desks in the section
					 */
					while (numdesks-- > 0) {
						currentLine = FILE.readLine();
						String[] deskHeader = currentLine.split(",");

						/*
						 * Loads the student currently sitting in the desk
						 */
						int studentID = Integer.parseInt(deskHeader[0]);
						Desk desk = new Desk();
						for (Student student : this.students) {
							if (student.getStudentID() == studentID) {
								desk = new Desk(student);
							}
						}

						/*
						 * Loads list of students who've previously sat in the
						 * desk
						 */
						for (int i = 0; i < Integer.parseInt(deskHeader[1]); i++) {
							currentLine = FILE.readLine();
							int previousID = Integer.parseInt(currentLine);
							for (Student student : students) {
								if (student.getStudentID() == previousID) {
									desk.addPrevious(student);
								} else {
									desk.addPrevious(new Student(previousID));
								}
							}
						}

						/*
						 * Checks if currently assigned student is REQUIRED to
						 * sit there.
						 */
						desk.setEditable(Integer.parseInt(deskHeader[2]) != 1);
						quadrant.addDesk(desk);
					}
					quadrants.add(quadrant);
				}
				FILE.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Opens the save file of students.
	 * 
	 * See Students.csv.format for how this save file is structured.
	 * 
	 * @param studentListDirectory
	 *            Directory to open
	 */
	private void readStudentListFromFile(String studentListDirectory) 
	{
		readStudentList(new File(studentListDirectory));
	}
	
	/**
	 * Opens save file of students.
	 * 
	 * See Students.csv.format for how this save file is structured.
	 * 
	 * @param file
	 *            File object of save file
	 */
	private void readStudentList(File file) 
	{
		BufferedReader FILE;
		try {
			String currentLine;
			FILE = new BufferedReader(new FileReader(file));

			while ((currentLine = FILE.readLine()) != null) {
				String[] arg = currentLine.split(",");
				Student newStudent = new Student(Integer.parseInt(arg[0]));
				newStudent.setFirstName(arg[1]);
				newStudent.setLastName(arg[2]);
				newStudent.setGender(arg[3]);
				newStudent.setRace(arg[4]);

				for (int i = 0; i < Integer.parseInt(arg[5]); i++) {
					currentLine = FILE.readLine();
					newStudent.avoid(new Student(Integer.parseInt(currentLine)));
				}
				students.add(newStudent);
			}
			FILE.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Student student : students) {
			for (int i = 0; i < student.getAvoiding().size(); i++) {
				for (Student checkingstudent : students) {
					if (student.getAvoiding().get(i).equals(checkingstudent)) {
						student.getAvoiding().set(i, checkingstudent);
					}
				}
			}
		}
		
	}

	/**
	 * Saves the save files to a directory.
	 * See Students.csv.format and Desks.csv.format for how this save file is 
	 * structured.
	 * 
	 * @param outputDirectory Directory to save to.
	 */
	public void save(File outputDirectory)
	{
		/*
		 * Formats data for student file
		 */
		String studentOut = "";
		for (Student student : this.students) {
			ArrayList<Student> avoidingList = student.getAvoiding();
			String studentHeader = String.format("%d,%s,%s,%s,%s,%d\n",
					student.getStudentID(), student.getFirstName(),
					student.getLastName(), student.getGender(),
					student.getRace(), avoidingList.size());
			
			studentOut += studentHeader;
			for (Student avoidingStudent : avoidingList) {
				studentOut += (Integer.toString(avoidingStudent.getStudentID()) + "\n");
			}
		}
		
		/*
		 * Formats data for desks file
		 */
		String deskOut = "";
		for (Section section : quadrants) {
			deskOut += Integer.toString(section.getSize()) + "\n";
			for (int i = 0; i < section.getSize(); i++) {
				Desk desk = section.getDesk(i);
				ArrayList<Student> previouslyOccupiedStudents = desk.getPreviouslyOccupied();
				ArrayList<Integer> studentIDList = new ArrayList<Integer>();
				
				// Remove fake entries
				while (previouslyOccupiedStudents.remove(null))
					;
				
				for (Student previousStudent : previouslyOccupiedStudents) {
					if (!studentIDList.contains(previousStudent.getStudentID())) {
						studentIDList.add(previousStudent.getStudentID());
					}
				}

				if (desk.getOccupyingStudent() == null) {
					deskOut += String.format("%d,%d,%d\n", -1, studentIDList.size(), desk.isEditable() ? 0 : 1);
				} else {
					deskOut += String.format("%d,%d,%d\n", desk.getOccupyingStudent().getStudentID(), studentIDList.size(),desk.isEditable() ? 0 : 1);
				}
				for (int studentID: studentIDList) {
					deskOut += Integer.toString(studentID)+ "\n";
				}
			}
		}
		
		BufferedWriter out = null;
		try {
			File studentsSave = new File(outputDirectory.getAbsolutePath()+"/"+"Students.csv");
			studentsSave.createNewFile();
			out = new BufferedWriter(new FileWriter(studentsSave));
			out.write(studentOut);
			out.close();
			
			File desksSave = new File(outputDirectory.getAbsolutePath()+"/"+"Desks.csv");
			desksSave.createNewFile();
			out = new BufferedWriter(new FileWriter(desksSave));
			out.write(deskOut);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Retrieves a student from the student list for this classroom with the
	 * correct student ID.
	 * 
	 * @param studentID
	 *            Student ID to search for
	 * @return Student object with matching student if found, null if not found
	 */
	public Student findStudent(int studentID) 
	{
		for (Student i : students) {
			if (i.getStudentID() == studentID) {
				return i;
			}
		}
		return null;
	}

	/**
	 * Shuffles an array in-place. TODO: Implement a legit shuffling algorithm.
	 * For now, Collections.shuffle is sufficient.
	 * 
	 * @param array
	 *            Array to shuffle
	 */
	private void shuffle(ArrayList<Student> array) 
	{
		Collections.shuffle(array);
	}

	/**
	 * Generates a seating chart by occupying all desks in the classroom
	 */
	public void generateSeatingChart() 
	{
		ArrayList<Student> studentsToBeSeated = new ArrayList<Student>();
		studentsToBeSeated.addAll(students);
		
		/*
		 * Removes all students who are forcibly assigned to be seated 
		 * somewhere from the list of students to be seated.
		 */
		for (Section section : this.quadrants) {
			for (int i = 0; i < section.getSize(); i++) {
				Desk selectedDesk = section.getDesk(i);
				if (selectedDesk.isOccupied()) {
					if (selectedDesk.isEditable() == false) {
						studentsToBeSeated.remove(selectedDesk.getOccupyingStudent());
					} else {
						/*
						 * While we're at it, also checks the seating chart 
						 * for all students who were seated with each other 
						 * previously to record that they have.
						 */
						Student student = selectedDesk.getOccupyingStudent();
						if (i != 0) {
							student.satNextTo(section.getDesk(i-1).getOccupyingStudent());
						}
						if (i < section.getSize() - 1) {
							student.satNextTo(section.getDesk(i + 1).getOccupyingStudent());
						}
					}
				}
				if (selectedDesk.isEditable() == true) {
					selectedDesk.empty();
				}
			}
		}
		
		shuffle(studentsToBeSeated);

		for (Section section : this.quadrants) {
			for (int i = 0; i < section.getSize(); i++) {
				
				Desk nextDesk = section.getDesk(i);
				
				/*
				 * Desk has already been forcibly assigned, so skipping.
				 */
				if (nextDesk.isEditable() == false) {
					continue;
				}
				
				if (studentsToBeSeated.size() > 0) {
					
					/*
					 * No students needed to be compared with (first one in 
					 * that section to be seated), so can just be seated.
					 */
					if (i == 0) {
						nextDesk.occupy(studentsToBeSeated.remove(0));
					} else {
						boolean foundSeat = false;
						Student previousStudent = section.getDesk(i-1).getOccupyingStudent();
						
						/*
						 * Goes through each sensitivity. Sensitivity 0 will 
						 * always return 0, so at worst case scenario, it'll 
						 * forcibly place someone who may or may not be 
						 * suitable to be seated there.
						 *
						 * Cycles the list of students to be assigned at each 
						 * sensitivity until a suitable one is found if 
						 * possible.
						 */
						for (int sensitivity = 4; foundSeat == false; sensitivity--) {
							int count = studentsToBeSeated.size(); 
							
							while (count --> 0) {
								if (previousStudent.canSitWith(studentsToBeSeated.get(0), sensitivity) == true) {
									nextDesk.occupy(studentsToBeSeated.remove(0));
									foundSeat = true;
									break;
								} else {
									studentsToBeSeated.add(studentsToBeSeated.remove(0));
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Gets list of sections.
	 * 
	 * @return Array of the 3 sections of the classroom
	 */
	public ArrayList<Section> getQuadrants() 
	{
		return quadrants;
	}

	/**
	 * Get list of students
	 * 
	 * @return ArrayList of the students in the class
	 */
	public ArrayList<Student> getStudents() 
	{
		return students;
	}
	
	/**
	 * Gets the main directory of the classroom save files
	 * 
	 * @return Directory of the classroom save files.
	 */
	public String getSaveDirectory() 
	{
		return this.saveDirectory;
	}
	
	/**
	 * Sets the new name of the classroom
	 * @param name New name to set to
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	
	/**
	 * Gets the name of the classroom
	 * 
	 * @return the name of the classroom
	 */
	public String toString()
	{
		return this.name;
	}
	
	/**
	 * Compares two classroom objects by name.
	 * 
	 * @param classroom Other classroom object to compare with
	 * @return Whether their names match
	 */
	public boolean equals(ClassRoom classroom) 
	{
		return this.name.equals(classroom.toString());
	}
}