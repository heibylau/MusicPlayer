public class Node {
	protected Node next;
	protected Node previous;
	protected Object value;

	public Node(Object value, Node previous, Node next)
	{
		this.value = value;
		this.previous = previous;
		this.next = next;
	}
	
}