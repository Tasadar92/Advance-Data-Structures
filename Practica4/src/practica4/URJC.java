package practica4;

import java.util.ArrayList;
import java.util.Iterator;

import material.maps.Entry;
import material.maps.HashTableMapLP;
import material.maps.Map;

public class URJC{
	private HashTableMapLP<Degree, ArrayList<Student>> map = new HashTableMapLP<>();
	
	public void insertDegree(Degree degree) {
		map.put(degree,new ArrayList<Student>());
	}
	
	public void insertStudent(Student student, Degree degree) {
		Iterable<Entry<Degree, ArrayList<Student>>> l1;
		ArrayList<Student> l2 = new ArrayList<>();
		
		l1 = map.entries();
		
		for(Entry<Degree, ArrayList<Student>> e: l1){
			if(e.getKey().equals(degree)){
				l2 = e.getValue();
				
				if(l2.size() == 0){
					l2.add(student);
					map.put(degree, l2);
				}else{
					
					for(Student st: l2){
						if(!st.equals(student)){
							l2.add(student);
							map.put(degree, l2);
							break;
						}
					}
				}
			}
		}

	}
	
	/**
	 * Recovers all the students enrolled in a given degree
	 * @param degree the degree to search
	 * @return an iterable collections with the students
	 */
	public Iterable<Student> students(Degree degree) {
		Iterable<Entry<Degree, ArrayList<Student>>> l;
		Iterable<Student> l2 = null;
		
		l =  map.entries();
		
		for(Entry<Degree, ArrayList<Student>> e: l){
			if(e.getKey().equals(degree))
				l2 = e.getValue();
		}
		return l2;
	}
	
	/**
	 * Recovers all the degrees currently in the university
	 * @return an iterable collection with all the degrees
	 */
	public Iterable<Degree> degrees() {	
		Iterable<Entry<Degree, ArrayList<Student>>> l1;
		ArrayList<Degree> l2 = new ArrayList<>();
		
		l1 = map.entries();
		
		for(Entry<Degree, ArrayList<Student>> d: l1){
			l2.add(d.getKey());
		}
		return l2;
	}

	/**
	 * Recover the degree in which a student is enrolled
	 * @param student the student to check the degree
	 * @return the degree of the student
	 */
	public Degree degree(Student student) { //Fallo por sobreescritura de claves
		Iterable<Entry<Degree, ArrayList<Student>>> l;
		Degree deg = null;
		
		l = map.entries();
		
		for(Entry<Degree, ArrayList<Student>> e: l){
				if(e.getValue() != null && e.getValue().contains(student))
					deg = e.getKey();
		}
				
		return deg;
	}
	
	/**
	 * Recovers the students with a gred larger or equal than
	 * the given one
	 * @param minGrade the minimum grade to be considered a good student
	 * @return an iterable collection with the best students
	 */
	public Iterable<Student> bestStudents(float minGrade) {
		Iterable<Entry<Degree, ArrayList<Student>>> l1;
		Iterable<Student> l2;

		ArrayList<Student> l3 = new ArrayList<>();
		
		l1 = map.entries();
		
		for(Entry<Degree, ArrayList<Student>> e: l1){
			l2 = e.getValue();
			
			for(Student st: l2){
				if(st.getNota() >= minGrade)
					l3.add(st);
			}
		}
		return l3;
		
	}
	
	/**
	 * Recovers a degree based on its id
	 * @param idDegree the id of the degree
	 */
	public Degree degreeInfo(String idDegree) {
		Iterable<Entry<Degree, ArrayList<Student>>> l1;
		Degree deg = null;
		
		l1 = map.entries();
		
		for(Entry<Degree, ArrayList<Student>> e: l1){
			if(e.getKey().getId().equals(idDegree))
			deg = e.getKey();
		}
		return deg;
	}
	
	/**
	 * Recovers a student based on its record number
	 * @param studentRecord the record of the student
	 */
	public Student studentInfo(String studentRecord) {
		Iterable<Entry<Degree, ArrayList<Student>>> l1;
		Iterable<Student> l2;
		Student student = null;
		
		l1 = map.entries();
		
		for(Entry<Degree, ArrayList<Student>> e: l1){
			l2 = e.getValue();
			
			for(Student st: l2){
				if(st.getRecord().equals(studentRecord))
					student = st;
			}
		}
		return student;
	}
}
