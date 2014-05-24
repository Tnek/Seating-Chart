/**
 * Represents a single desk in the classroom
 *
 * @author KAAT Solutions
 */
import java.util.ArrayList;

public class Desk 
{
    private Student student;
    private ArrayList<Student> previous = new ArrayList<Student>();
    private boolean isEditable = true;
    
    /**
     * Initializes a desk with no occupants.
     */
    public Desk()
    {
        this.student = null;
    }

    /**
     * Initializes desk with a student already seated in it
     * 
     * @param student Student to be seated on the desk
     */
    public Desk(Student student) 
    {
    	if (student.getStudentID() != -1) {
    		this.student = student;
    	}
    	this.isEditable = false;
    }
    
    /**
     * Gets the student currently occupying the desk
     * 
     * @return Student currently occupying the desk
     */
    public Student getOccupyingStudent()
    {
    	return this.student;
    }

    /**
     * Checks if the desk is currently occupied.
     *
     * @return Whether the desk has a student in it.
     */
    public boolean isOccupied() 
    {
        return this.student != null;
    }

    /**
     * Occupies the desk with a student.
     *
     * @param student Student to occupy the desk with.
     */
    public void occupy(Student student) 
    {
        this.student = student;
    } 

    /**
     * Adds a student to the list of students which previously occupied this desk
     *  
     * @param student Student to add
     */
    public void addPrevious(Student student) 
    {
    	if (previous.contains(student) == false) {
    		this.previous.add(student);
    	}
    }
   
    /**
     * Completely deletes all traces of the current student
     */
    public void delete(Student student)
    {
    	if (this.student != null && this.student.equals(student)) {
    		this.student = null;
    	}
    	while (previous.contains(student)) {
    		previous.remove(student);
    	}
    }
    
    /**
     * Empties the desk of a student
     *
     * @return The student removed from the desk.
     */
    public Student empty() 
    {
        Student temp = this.student;
        previous.add(this.student);
        this.student = null;
        return temp;
    }

    /**
     * Checks whether a student has previously occupied the desk.
     * 
     * @param student The student to check for.
     * @return Whether the student has previously occupied this desk.
     */
    public boolean previouslyOccupied(Student student) 
    {
        for (Student i : previous) {
            if (student.equals(i)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks whether the student in this desk is forcibly assigned
     * 
     * @return If the desk is editable
     */
    public boolean isEditable()
    {
    	return this.isEditable;
    }
    
    /**
     * If this desk's seating is forcibly assigned, marks that the desk's state
     * can't be edited.
     * 
     * @param state State to set the edit-ability of this desk to
     */
    public void setEditable(boolean state) 
    {
    	this.isEditable = state;
    }
    
    /**
     * Gets the list of students who've previously occupied this desk.
     * 
     * @return ArrayList of the students who've previously occupied the desk.
     */
    public ArrayList<Student> getPreviouslyOccupied() 
    {
    	return this.previous;
    }
    
    /**
     * Returns the student currently seated on this desk as well as any past students.
     */
    public String toString() 
    {
    	return "Current student: " + this.student + "\n" + this.previous.toString();
    }
}
