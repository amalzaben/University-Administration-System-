package application;

public class Statistics {
	private DLL majors;
	private double TotalAccepted;
	private double TotalNotAccepted;
	private double AcceptenceRate;
	private double total;
	public Statistics(DLL majors) {
		super();
		this.majors = majors;
		TotalAccepted=countAccepted();
		TotalNotAccepted=countNotAccepted();
		AcceptenceRate=countAcceptenceRate();
		total=TotalNotAccepted+TotalAccepted;
	}
	public double getTotalAccepted() {
		return TotalAccepted;
	}
	public double getTotalNotAccepted() {
		return TotalNotAccepted;
	}
	public double getAcceptenceRate() {
		return AcceptenceRate;
	}
	public double getTotal() {
		return total;
	}
	//=========== calculations =============
	private double countAccepted() {//o(n)
		int sum=0;
		if(majors.getCount()!=1) {
			DNode temp=majors.getFirst();
			for(int i=0;i<majors.getCount();i++) {
				sum+=((Major)temp.getElement()).getAcceptedStudentsCount();
				temp=temp.getNext();
			}
		}
		return sum;
	}
	private double countNotAccepted() {//o(n)
		int sum=0;
		if(majors.getCount()!=1) {
			DNode temp=majors.getFirst();
			for(int i=0;i<majors.getCount();i++) {
				sum+=((Major)temp.getElement()).getNotAcceptedStudentsCount();
				temp=temp.getNext();
			}
		}
		return sum;
	}
	private double countAcceptenceRate() {//o(1)
		double rate=0;
		if(TotalNotAccepted+TotalAccepted!=0) {
		    return rate=TotalAccepted/(TotalNotAccepted+TotalAccepted);
		}
		else
			return 0;
	}
	

}
