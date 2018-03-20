package practica4;

/**
*
* @author danie
*/

public class Student {
	private String name;
	private String record;
	private float nota;
	
	public Student(String studentRecord, String name, String lastName, float avgGrade) {
		this.record = studentRecord;
		this.name = name;
		this.nota = avgGrade;
	}

	public String getName() {
		return name;
	}

	public String getRecord() {
		return record;
	}

	public float getNota() {
		return nota;
	}

}
