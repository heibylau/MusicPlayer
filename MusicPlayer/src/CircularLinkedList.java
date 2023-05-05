import java.util.NoSuchElementException;

public class CircularLinkedList {
	Node head;
	Node pointerNode;
	Node lastNode;
	Node tail;
	boolean isLooped;
	
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
						
			tail = current.next = newNode;
			newNode.previous = current;
		}
	}


	public boolean hasNext() {
		if (pointerNode == null) {
			pointerNode = new Node(null, head, head.next);
		}
		if (isLooped == true) {
			return true;
		} else {
			return pointerNode.next != null;
		}
	}

	public Object next() {	
		if(hasNext() == false) {
			throw new NoSuchElementException();
		}
		Object value = null;
		try {
			pointerNode.previous = pointerNode.next;
			pointerNode.next = pointerNode.next.next;
			lastNode = pointerNode.previous;
			value = lastNode.previous.value;
		} catch (Exception e) {
			System.out.println("End of list");
		}
		
		return value;

	}

	public boolean hasPrevious() {
		if (pointerNode == null) {
			pointerNode = new Node(null, head, head.next);
		}
		return pointerNode.previous != null;
	}
	
	public Object previous() {
		if(hasPrevious() == false) {
			throw new NoSuchElementException();
		}
		Object value = null;
		try {
			pointerNode.next = pointerNode.previous;
			pointerNode.previous = pointerNode.previous.previous;
			lastNode = pointerNode.next;
			value = lastNode.previous.value;
		} catch (Exception e) {
			System.out.println("End of list");
		}
		return value;

	}
	
	public void loop() {
		tail.next = head;
		head.previous = tail;
		isLooped = true;
	}
	
	public void unLoop() {
		tail.next = null;
		head.previous = null;
		isLooped = false;
	}
	
}