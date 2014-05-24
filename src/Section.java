/**
 * Object representing a quadrant or section of the classroom. Might be
 * redundant with a normal array, but its too embedded in the system, and will
 * be very time consuming to replace.
 * 
 * @author KAAT Solutions
 */
class Section 
{
	private int size;
	private Desk desks[];
	private int setup = 0;

	/**
	 * Creates a new section of desks given the size.
	 * 
	 * @param size
	 *            Size of the desks
	 */
	public Section(int size) 
	{
		this.size = size;
		desks = new Desk[size];
		for (int i = 0; i < size; i++) {
			desks[i] = new Desk();
		}
	}

	/**
	 * Returns the size of the section
	 * 
	 * @return Number of desks in the section
	 */
	public int getSize() 
	{
		return size;
	}

	/**
	 * Appends a desk to the section
	 * 
	 * @param desk
	 */
	public void addDesk(Desk desk) 
	{
		desks[setup++] = desk;
	}

	/**
	 * Fetches a desk at an index
	 * 
	 * @param index
	 *            Index of Section to get the desk
	 * @return desk Desk at the index specified
	 */
	public Desk getDesk(int index) 
	{
		return desks[index];
	}
	
	/**
	 * Removes student from the desks in this section if the student is 
	 * currently being seated in this section.
	 * @param student Student to remove
	 */
	public void removeStudent(Student student) 
	{
		for (Desk desk : desks) {
			if (desk.isEditable() == true && desk.getOccupyingStudent() != null && desk.getOccupyingStudent().equals(student)) {
				desk.empty();
			}
		}
	}
}