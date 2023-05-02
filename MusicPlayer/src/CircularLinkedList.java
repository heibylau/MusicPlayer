import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CircularLinkedList {
	Node head;
	Node pointerNode;
	Node lastNode;
	public void addMusic(String filePath) {
		
		Node current = head;

		if (head == null) {
			//this new node will become the head
			Node newNode = new Node(filePath, null , null);
			newNode.previous = null;
			newNode.next = null;
			head = newNode;
			return;
		}
//		else {
//			//traverse across list to tail node
//			while (current.next != null) {
//				current = current.next;
//			}
//						
//			current.next = newNode;
//			newNode.previous = current;
//		}
		Node newNode = new Node(filePath, null , null);
		Node last = head.previous;
		newNode.next = head;
		head.previous = newNode;
		newNode.previous = last;
		last.next = newNode;
	}
	
	public boolean hasNext() {
		return pointerNode.next != null;
	}
	
	public Object next() {	

		if (pointerNode == null) {
			pointerNode = new Node(null, head, head.next);
			return pointerNode.previous.value;
		} 
		
		if(hasNext() == false) {
			throw new NoSuchElementException();
		}
		
		pointerNode.previous = pointerNode.next;
		pointerNode.next = pointerNode.next.next;
		lastNode = pointerNode.previous;
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
		return lastNode.value;

	}
	
}