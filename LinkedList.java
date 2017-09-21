//Nicolas Stoian

public class LinkedList{

	private Node listHead;
	private Node walker;

	public LinkedList(){
		listHead = new Node();
	    walker = listHead;
    }

	public String printList(){
		String toReturn = "";
	    toReturn = toReturn + "listHead -->";
	    while(walker.getNext() != null){
	        toReturn = toReturn + walker.printNode();
	        toReturn = toReturn + "-->";
	        walker = walker.getNext();
	    }
	    toReturn = toReturn + walker.printNode();
	    walker = listHead;
	    return toReturn;
    }

	public void insertNode(Node nodeToInsert){
		while(walker.getNext() != null && walker.getNext().getProb() < nodeToInsert.getProb()){
	        walker = walker.getNext();
	    }
	    nodeToInsert.setNext(walker.getNext());
	    walker.setNext(nodeToInsert);
	    walker = listHead;
    }

	public String printSortedList(){
		String toReturn = "";
	    walker = walker.getNext();
	    while(walker.getNext() != null){
	        toReturn = toReturn + walker.getCh() + " " + walker.getProb() + System.getProperty("line.separator");
	        walker = walker.getNext();
	    }
	    toReturn = toReturn + walker.getCh() + " " + walker.getProb() + System.getProperty("line.separator");
	    walker = listHead;
	    return toReturn;
    }

	public Node getListHead(){
		return listHead;
	}
}
