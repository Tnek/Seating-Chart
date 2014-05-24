/**
 * Represents a student in the classroom.
 *
 * @author KAAT Solutions
 */
import java.util.ArrayList;

public class Student
{
	public static enum RACE { White, Asian, AfricanAmerican, Latino, PacificIslander, Other };
	public static enum GENDER { Male, Female, Other };

	private int studentID;
	private String firstname, lastname;
	private ArrayList<Student> avoiding = new ArrayList<Student>();
	private ArrayList<Student> previousNeighbors = new ArrayList<Student>();
	private GENDER gender;
	private RACE race;

	/**
	 * Initializes student. 
	 * 
	 * @param studentID Student ID of the student.
	 * @param firstname First name of the student.
	 * @param lastname Last name of the student.
	 * @param gender Gender of the student.
	 * @param race Race of the student.
	 */
	public Student(int studentID, String firstname, String lastname, GENDER gender, RACE race)
	{
		this.firstname = firstname;
		this.lastname = lastname;
		this.studentID = studentID;
		this.gender = gender;
		this.race = race;
	}

	/**
	 * Initializes a student given the student ID.
	 *
	 * @param studentID Student ID of the student
	 */
	public Student(int studentID) 
	{
		this(studentID, "", "", GENDER.Other, RACE.Other);
	}
	
	/**
	 * Initializes a student given the first and last name
	 * 
	 * @param firstname First name of the student
	 * @param lastname Last name of the student
	 */
	public Student(String firstname, String lastname) 
	{
		this(-1, firstname, lastname, GENDER.Other, RACE.Other);
	}
	
	/**
	 * Initializes an empty student.
	 */
	public Student() 
	{
		this("", "");
	}

	/**
	 * Marks this student to never be seated with another student.
	 * 
	 * @param student
	 *            Student object of student to avoid
	 */
	public void avoid(Student student) 
	{
		avoiding.add(student);
	}

	/**
	 * Marks this student to never be seated with another student.
	 * 
	 * @param firstname
	 *            First name of the student to avoid.
	 * @param lastname
	 *            Last name of the student to avoid.
	 */
	public void avoid(String firstname, String lastname) 
	{
		avoid(new Student(firstname, lastname));
	}

	/**
	 * Checks if this student is avoiding another student.
	 * 
	 * @param student
	 *            Student object of other student to check
	 * @return Whether the student is avoiding the other student
	 */
	public boolean isAvoiding(Student student) 
	{
		for (Student i : avoiding) {
			if (i.equals(student)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if this student is avoiding another student.
	 * 
	 * @param firstname
	 *            First name of the other student to check
	 * @param lastname
	 *            Last name of the other student to check
	 * @return Whether the student is avoiding the other student
	 */
	public boolean isAvoiding(String firstname, String lastname)
	{
		return isAvoiding(new Student(firstname, lastname));
	}
	
	/**
	 * Gets the list of students to avoid for this student.
	 * 
	 * @return An arraylist of students avoided.
	 */
	public ArrayList<Student> getAvoiding()
	{
		return this.avoiding;
	}
	
	/**
	 * Checks if this student has previously sat with another student.
	 * @param student Student object of the student to check
	 * @return Whether the student has previously sat with the other student
	 */
	public boolean hasPreviouslySatNextTo(Student student)
	{
		for (Student i : previousNeighbors) {
			if (student.equals(i)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Marks a student as having sat next to this student previously.
	 * @param student
	 */
	public void satNextTo(Student student) 
	{
		if (student != null && !this.hasPreviouslySatNextTo(student)) {  // Avoiding redundant entries
			this.previousNeighbors.add(student);
		}
	}
	
	/**
	 * Checks if this student can sit with another one with varying degrees of
	 * sensitivity for the check. 
	 * <p>
	 * Potentially four things will be checked: 
	 * <ul>
	 * <li>Check 1 - Student has previously sat with</li> 
	 * <li>Check 2 - Student should be avoiding other student</li> 
	 * <li>Check 3 - Student is a different gender</li>
	 * <li>Check 4 - Student is a different race</li>
	 * </ul>
	 * 
	 * @param student
	 *            Student to check
	 * @param sensitivity
	 *            Sensitivity for checking whether the students can be seated.
	 *            This is an integer value from 0 to 4. Checks 1 to n are
	 *            performed based on the value of sensitivity, with check 0
	 *            resulting in no checks being performed.
	 * @return Whether the student can sit with another one.
	 */
	public boolean canSitWith(Student student, int sensitivity) 
	{
		switch (sensitivity) {
			case 4:
				return this.race != student.getRace()
					&& this.gender != student.getGender()
					&& !this.isAvoiding(student)
					&& !this.hasPreviouslySatNextTo(student);
			case 3:
				return this.gender != student.getGender()
					&& !this.isAvoiding(student)
					&& !this.hasPreviouslySatNextTo(student);
			case 2:
				return !this.hasPreviouslySatNextTo(student)
					&& !this.isAvoiding(student);
			case 1:
				return !this.isAvoiding(student);
			default:
				return true;
		}
	}
	
	/**
	 * Sets the student ID
	 * 
	 * @param studentID
	 *            New studentID
	 */
	public void setStudentID(int studentID) 
	{
		this.studentID= studentID;
	}

	/**
	 * Gets the student ID
	 * 
	 * @return student ID of the student
	 */
	public int getStudentID() 
	{
		return this.studentID;
	}
	/**
	 * Sets the first name.
	 * 
	 * @param firstname
	 *            New first name
	 */
	public void setFirstName(String firstname) 
	{
		this.firstname = firstname;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return String of the First name
	 */
	public String getFirstName() 
	{
		return this.firstname;
	}

	/**
	 * Sets the last name.
	 * 
	 * @param lastname
	 *            New last name
	 */
	public void setLastName(String lastname)
	{
		this.lastname = lastname;
	}

	/**
	 * Gets the first name.
	 * 
	 * @return String of the First name
	 */
	public String getLastName()
	{
		return this.lastname;
	}

	/**
	 * Sets the gender.
	 * 
	 * @param gender
	 *            New gender
	 */
	public void setGender(GENDER gender) 
	{
		this.gender = gender;
	}
	
	/**
	 * Sets the gender given a string.
	 * 
	 * @param gender String of the new gender
	 */
	public void setGender(String gender) 
	{
		if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("Male")) {
			this.gender = Student.GENDER.Male;
		} else if (gender.equalsIgnoreCase("F") || gender.equalsIgnoreCase("Female")) {
			this.gender = Student.GENDER.Female;
		} else {
			this.gender = Student.GENDER.Other;
		}

	}
	
	/**
	 * Gets the gender.
	 * 
	 * @return The gender.
	 */
	public GENDER getGender() 
	{
		return this.gender;
	}

	/**
	 * Sets the race.
	 * 
	 * @param race
	 *            New race
	 */
	public void setRace(RACE race)
	{
		this.race = race;
	}
	
	/**
	 * Sets the race given a string.
	 * @param race String of the race.
	 */
	public void setRace(String race) 
	{
		if (race.equals("White")) {
			this.race = RACE.White;
		} else if (race.equals("Asian")) {
			this.race = RACE.Asian;
		} else if (race.equals("AfricanAmerican")) {
			this.race = RACE.AfricanAmerican;
		} else if (race.equals("Latino")) {
			this.race = RACE.Latino;
		} else if (race.equals("PacificIslander")) {
			this.race = RACE.PacificIslander;
		} else {
			this.race = RACE.Other;
		}	
	}

	/**
	 * Gets the race.
	 * 
	 * @return The race.
	 */
	public RACE getRace() 
	{
		return this.race;
	}

	/**
	 * Compares equality with another student object
	 * 
	 * @param other
	 *            Other student object to compare to
	 * @return Whether the objects are equal
	 */
	public boolean equals(Student other) 
	{
		return (this.getStudentID() >= 0 || other.getStudentID() >= 0) && this.getStudentID() == other.getStudentID();
	}

	/**
	 * Returns string of Student ID + First Name + Last Name separated by spaces.
	 */
	public String toString() 
	{
		return this.studentID +  " "+ this.firstname + " " + this.lastname;
	}
}
