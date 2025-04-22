package application;

public class Student implements Comparable<Student>{
	//========== data fields =================
	private static int defaultid=1250000;//student id
	private final int id;
	private String name;
    private double TG; //tawjihi grade
    private double PTG; //placement test grade
    private String choosenMajor;
    private double addmissionMark;
    private boolean accepted;
    //============== constructors============
	public Student( String name, double tG, double pTG, String choosenMajor) { // args constructor
		super();
		this.id=defaultid++;
		setName(name);
		setTG(tG);
		setPTG(pTG);
		this.choosenMajor = choosenMajor;
	}
	//=========== getters and setters==================
	
	public int getId() {
		return id;
	}	
	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	public double getAddmissionMark() {
		return addmissionMark;
	}
	public void setAddmissionMark(double addmissionMark) {
		this.addmissionMark = addmissionMark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name.matches("[a-zA-Z ]+")) {
		   this.name = name;
		}
		else
			throw new IllegalArgumentException("Student Name Should only Contain Letters");
	}
	public double getTG() {
		return TG;
	}
	public void setTG(double tG) {
		if(100>tG&&tG>=50) {
			TG = tG;
		}
		else
			throw new IllegalArgumentException("Tawgihi Grade Should Be Between Numbers 50 and 100 !!");

	}
	public double getPTG() {
		return PTG;
	}
	public void setPTG(double pTG) {
		if(100>pTG&&pTG>=50) {
			PTG = pTG;
		}
		else
			throw new IllegalArgumentException("Placement Test Grade Should Be Between Numbers 50 and 100 !!");
	}
	public String getChoosenMajor() {
		return choosenMajor;
	}
	public void setChoosenMajor(String choosenMajor) {
		this.choosenMajor = choosenMajor;
	}
	@Override
	public int compareTo(Student o) {
		return Double.compare(this.addmissionMark, o.getAddmissionMark());
	}
	public String toString() {
		return "Student ID: " + id + ", Name: " + name + ", TG: " + TG + ", PTG: " + PTG + ", Major: " + choosenMajor;
	}
	
    
}
