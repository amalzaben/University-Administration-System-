package application;

public class SNode {
	
	//============Data fields===================
	private SNode next;
	private Object element;
	//============Constructors=======================
	public SNode(Object element) {
		super();
		this.element = element;
	}
	//============Getters and Setters=========================
	public SNode getNext() {
		return next;
	}
	public void setNext(SNode next) {
		this.next = next;
	}
	public Object getElement() {
		return element;
	}
	public void setElement(Object element) {
		this.element = element;
	}
	
}
