import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CircularLinkedList implements ListIterator{
	Node head;
	Node pointerNode;
	Node lastNode;
	Node tail;
	boolean illegalState;
	int index = 0;
	public void addMusic(String filePath) {
		Node newNode = new Node(filePath, null , null);
		Node current = head;
		
		if (head == null) {
			//this new node will become the head

			newNode.previous = null;
			newNode.next = null;
			head = newNode;
			return;
		}
		else {
			//traverse across list to tail node
			while (current.next != null) {
				current = current.next;
			}
						
			current.next = newNode;
			newNode.previous = current;
		}
	}
	
	public ListIterator listIterator() {
		pointerNode = new Node(null, head, head.next);
		return this;
	}

	public boolean hasNext() {
		return pointerNode.next != null;
	}

	public Object next() {	

//		if (pointerNode == null) {
//			pointerNode = new Node(null, head, head.next);
//			return pointerNode.previous.value;
//		} 
		
		if(hasNext() == false) {
			throw new NoSuchElementException();
		}
		
		pointerNode.previous = pointerNode.next;
		pointerNode.next = pointerNode.next.next;
		lastNode = pointerNode.previous;
		
		index++;
		illegalState = false;
		return lastNode.value;

	}

	public boolean hasPrevious() {
		return pointerNode.previous != null;
	}
	
	public Object previous() {
		if(hasPrevious() == false) {
			throw new NoSuchElementException();
		}
		pointerNode.next = pointerNode.previous;
		pointerNode.previous = pointerNode.previous.previous;
		lastNode = pointerNode.next;
		index--;
		illegalState = false;
		return lastNode.value;

	}

	public int nextIndex() {
		return index + 1;

	}

	public int previousIndex() {	
		return index;

	}

	public void remove() {
		if (pointerNode == null || illegalState == true) {
			throw new IllegalStateException();
		} else {
			lastNode.previous.next = lastNode.next;
			lastNode.next.previous = lastNode.previous;			
			index--;	
		}
		illegalState = true;
		
	}

	public void set(Object e) {
		if(pointerNode == null || illegalState == true) {
			throw new IllegalStateException();
		}
		else {
			lastNode.value = (String) e;
		}
		illegalState = true;
	}

	public void add(Object e) {
		Node newNode = new Node((String) e, pointerNode.previous, pointerNode.next);
		pointerNode.previous.next = newNode;
		pointerNode.next.previous = newNode;
		pointerNode.previous = newNode;
		index++;
		illegalState = true;
		
	}
	
	public void loop() {
		while(hasNext()) {
			tail = pointerNode.next;
		}
		tail.next = head;
		head.previous = tail;
	}
	
}