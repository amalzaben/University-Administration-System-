package application;



public class DNode {
	//============Data fields===================
	private DNode next,prev;
	private Object element;
	//============Constructors=======================
	public DNode(Object element) {
		super();
		this.element = element;
	}
	//=========== setters and getters===============
	
	public DNode getNext() {
		return next;
	}

	public void setNext(DNode next) {
		this.next = next;
	}
	public DNode getPrev() {
		return prev;
	}
	public void setPrev(DNode prev) {
		this.prev = prev;
	}
	public Object getElement() {
		return element;
	}
	public void setElement(Object element) {
		this.element = element;
	}
	

}
