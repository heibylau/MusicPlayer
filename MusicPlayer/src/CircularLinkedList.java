import java.util.ListIterator;
import java.util.NoSuchElementException;

public class CircularLinkedList {
	Node head;
	Node pointerNode;
	Node lastNode;
	Node tail;
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


	public boolean hasNext() {
		if (pointerNode == null) {
			pointerNode = new Node(null, head, head.next);
		}
		return pointerNode.next != null;
	}

	public Object next() {	
		
		if(hasNext() == false) {
			throw new NoSuchElementException();
		}
		
		pointerNode.previous = pointerNode.next;
		pointerNode.next = pointerNode.next.next;
		lastNode = pointerNode.previous;
		return lastNode.previous.value;

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
		return lastNode.previous.value;

	}
	
	public void loop() {
		while(hasNext()) {
			tail = pointerNode.next;
		}
		tail.next = head;
		head.previous = tail;
	}
	
}