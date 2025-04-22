package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

public class Major implements Comparable<Major>{
	
	//========== data fields=====================
	private String name;
	private int acc_grade; //acceptance grade
	private double taw_grade; // tawjihi weight
	private double ptest_grade; // placement test weight
	private double acceptedStudentsCount;
	private double notAcceptedStudentsCount;
	private double acceptenceRate;
	private SLL students;
	//============ constructors======================
	public Major() { //no args constructor
		
	}
	
	public Major(String name, int acc_grade, double taw_grade, double ptest_gtade) {
		super();
		students=new SLL();
		this.name=name;
		setAcc_grade(acc_grade);
		this.taw_grade = taw_grade;
		setPtest_grade(ptest_gtade);
	}

	//========= getters and setters========================
	public SLL getStudents() {
		return students;
	}

	public void setStudents(SLL students) {
		this.students = students;
	}
	
	public String getName() {
		return this.name;
	}
	public double getAcceptenceRate() {
		if((acceptedStudentsCount+notAcceptedStudentsCount)==0){
		  return 0;
	    }
		else
		  return (acceptedStudentsCount/(acceptedStudentsCount+notAcceptedStudentsCount));
	}
	public double getAcceptedStudentsCount() {
		return acceptedStudentsCount;
	}
    
	public void setAcceptedStudentsCount(double acceptedStudents) {
		this.acceptedStudentsCount = acceptedStudents;
	}

	public void setNotAcceptedStudentsCount(double notAcceptedStudents) {
		this.notAcceptedStudentsCount = notAcceptedStudents;
	}

	public void setAcceptenceRate(double acceptenceRate) {
		this.acceptenceRate = acceptenceRate;
	}

	public double getNotAcceptedStudentsCount() {
		return notAcceptedStudentsCount;
	}

	public void setName(String value) {
			this.name = name;
			
    }
	public int getAcc_grade() {
		return acc_grade;
	}
	public void setAcc_grade(int acc_grade) {
		if(100>acc_grade&&acc_grade>60) {
			this.acc_grade = acc_grade;
		}
		else
			throw new IllegalArgumentException("Acceptence Grade Should Be Between Numbers 60 and 100 !!");
	}
	public double getTaw_grade() {
		return taw_grade;
	}
	public void setTaw_grade(double taw_grade) {
		this.taw_grade = taw_grade;
	}
	public double getPtest_grade() {
		return ptest_grade;
	}
	public void setPtest_grade(double ptest_grade) {
		if(this.taw_grade+ptest_grade==1) {
			this.ptest_grade = ptest_grade;
		}
		else
			throw new IllegalArgumentException(" Summation Of Tawjihi Weight And Placement Test Weight Should Be 1.0 !! ");
	}
	@Override
	public int compareTo(Major o) {
		return this.getName().toLowerCase().compareTo(o.getName().toLowerCase());
	}
	public boolean equals(Object o) {
		if(this.getName().equalsIgnoreCase((((Major) o).getName())))
			return true;
		return false;
		
	}

}
