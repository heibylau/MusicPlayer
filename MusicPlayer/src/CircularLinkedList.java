public class CircularLinkedList {
	Node head;
	public void add(String filePath) {
		if (head == null) {
			Node newNode = new Node();
			newNode.value = filePath;
			newNode.next = newNode.previous = newNode;
			head = newNode;
			return;
		}
	}
	
	public void loopList() {
		Node last = head.previous;
	}
}