package application;


public class SLL {
	
	//==========data fields======================
	private SNode first,last;
	private int count;
	//============ constructors===================
	public SLL() {
		
	}
	//=========== setters and getters===============
	public SNode getFirst() {
		return first;
	}
	public void setFirst(SNode first) {
		this.first = first;
	}
	public SNode getLast() {
		return last;
	}
	public void setLast(SNode last) {
		this.last = last;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	//=============================================
	public Object getFirstElement() {// a method to retrieve the first element in list
		if(count==0)                 // if there is no elements in the list return null
			return null;
		return first.getElement();   // if the list has elements return the element where first points 
	}
	//==============================================
	public Object getLastElement() { // a method to retrieve the last element in list
		if(count==0)                 // if there is no elements in the list return null
			return null;
		return last.getElement();    // if the list has elements return the element where last points 
	}
	//===============================================
	public void addFirst(Object element) { // a method to add SNode at the head of list
		SNode newNode=new SNode(element);  // create new SNode and assign element with its constructor
		 if (first == null) {                  // Check if the list is empty
		        first = newNode;                  // Set first to the new node
		        last = newNode;                   // Set last to the new node as well
		        last.setNext(first);              // Point the last node to the first (itself)
		    } else {
		        newNode.setNext(first);           // Set newNode's next to the current first
		        first = newNode;                  // Move first pointer to newNode
		        last.setNext(first);               // Maintain the circular link
		    }
		    count++;     
		
	}
	//===============================================
	public void addLast(Object element) {  // a method to add SNode at the tail of list
		SNode newNode=new SNode(element);  // create new SNode and assign element with its constructor
		if (last == null) {                   // Check if the list is empty
	        first = newNode;                  // Set first to the new node
	        last = newNode;                   // Set last to the new node
	        last.setNext(first);              // Point the last node to the first (itself)
	    } else {
	        last.setNext(newNode);            // Set the current last node's next to the new node
	        last = newNode;                   // Move last pointer to new node
	        last.setNext(first);              // Maintain the circular link
	    }
	    count++; 
	}
	//===============================================
	public void add (Object element,int index) { // a method to add SNode at specific index of the list
		if(index<0 || index>count)               // if the index is out of list throw exception
			throw new IllegalArgumentException("Insertation out of list");
		if(index==0)                             // if index is 0  insert node at first of list
			addFirst(element);
		else if (index==count)                   // if index equals count  insert node at the last of list
			addLast(element);
		else {                                   // otherwise  
			SNode newNode=new SNode(element);    // create new SNode and assign element with its constructor 
			SNode current=first;                 // assign a pinter at the head of list
			for(int i=0;i<index-1;i++)           // look for index , stop at index-1
				current=current.getNext();
			newNode.setNext(current.getNext());  //add node 
			current.setNext(newNode);
			count++;                             // add1 to count 
		}
	
	}
	
	//============= search for duplicates============
	public boolean searchforDuplicates(Object element) {
		if(first!=null) {
			if(((Student)first.getElement()).equals((Student)element))
				return true;
			if(((Student)last.getElement()).equals((Student)element))
				return true;
			SNode ptr=first.getNext();
			SNode previous=first;
			for(int i=0;i<count-2;i++){
				if(!((Student) ptr.getElement()).equals((Student)element)){
					previous=ptr;
					ptr=ptr.getNext();
				}
				else{
					return true;
				}
			}
		}
		return false;
	}
	//================= sortedAdd===============
	
	public void sortedAdd(Object element) {
    	if(!searchforDuplicates(element)) {
	        if (element == null) {
	            throw new IllegalArgumentException("Element cannot be null");
	        }

	        Student newStudent = (Student) element;

	        if (count == 0) { // If list is empty, add first
	            addFirst(newStudent);
	        } else if (((Student) first.getElement()).compareTo(newStudent) < 0) { // Compare with first element
	            addFirst(newStudent);
	        } else if (((Student) last.getElement()).compareTo(newStudent) > 0) { // Compare with last element
	            addLast(newStudent);
	        } else { // In the middle
	            SNode previous = first;
	            SNode curr = first.getNext();
	            SNode newNode = new SNode(newStudent);

	            for (int i=0;i<count-2;i++) {
	                if (((Student) curr.getElement()).compareTo(newStudent) < 0) {
	                    previous.setNext(newNode);
	                    newNode.setNext(curr);
	                 // Increment the count after successful addition
	                    break;
	                }
	                previous = curr;
	                curr = curr.getNext(); // Move to the next node
	            }
	            count++;
	          }
    	}
    	else {
    		throw new IllegalArgumentException("this student already exist !!");
    	}
	}
	
	//===================== search =========================
	
	public SNode search(int id) { 
		if(first!=null) { 
			if(((Student)first.getElement()).getId()==id)
				return first;
			else if(((Student)last.getElement()).getId()==id)
				return last;
			SNode ptr=first.getNext();
			SNode previous=first;
			for(int i=0;i<count-2;i++){
				if(((Student) ptr.getElement()).getId()==id){
					return ptr;
				}
				else{
					previous=ptr;
					ptr=ptr.getNext();
				}
			}
		}
		throw new NullPointerException("This Major Does Not Exsist !! , If You Want To Add It To System Go To Add New Tab!!");
	}

	//===============================================
	public boolean removeFirst() {  // a method to remove SNode from the head of list
		if(count==0)                // if list is empty return false
			return false;
		if(count==1)                // if list has only one element let first and last be null 
			first=last=null;
		else {                      // otherwise
			SNode temp = first;     //let temp point at the first node of list
			first=first.getNext();  // move first pointer to the next node
			last.setNext(first);    
			temp.setNext(null);     // remove temp(head of list)
		}
		count--;                    // subtract 1 from count
		return true;                // removing was done successfully return true
	}
	//=================================================
	public boolean removeLast() {   // a method to remove SNode from the tail of list
		if (count==0)               // if list is empty return false
			return false;
		else if(count==1)                // if list has only one element let first and last be null 
			first=last=null;
		else {                         // otherwise
			SNode current=first;       //let temp point at the first node of list
			while (current.getNext() != last) {
	            current = current.getNext();
	        }
	        
	        current.setNext(first);      // maintain circular link
	        last = current;              // update last to the new last node
	    }              
		count--;                         // subtract 1 from count
		return true;                    // removing was done successfully return true
	}
	//=================================================
	public boolean remove(int index) { // a method to remove SNode from a specific index at list
		if(index<0||index>count)           
			return false;
		else if (index==0)
			return removeFirst();
		else if(index==count)
			return removeLast();
		else {
			SNode current=first;
			for(int i=1;i<index-1;i++)
				current=current.getNext();
			SNode temp=current.getNext();
			current.setNext(temp.getNext());
			temp.setNext(null);
			count--;
			return true;
			
		}
	}
	//=================================================
	public boolean remove(Object element) {
	    if (first != null) {
	
	        if (first.getElement().equals(element)) {
	            return removeFirst();
	        }

	        if (last.getElement().equals(element)) {
	            return removeLast();
	        }

	      
	        SNode curr = first.getNext();
	        SNode prev = first;  

	        while (curr != first) {  
	            if (curr.getElement().equals(element)) {
	                prev.setNext(curr.getNext()); 
	                curr.setNext(null);  
	                count--;  
	                return true;
	            }
	            prev = curr;  
	            curr = curr.getNext();  
	        }
	    }
	    return false;  // Element not found
	}

	
	

}
