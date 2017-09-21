//Nicolas Stoian

public class Node{

	private String ch;
	private double prob;
	private String code;
	private Node left;
	private Node right;
	private Node next;

	public Node(){
		ch = "dummy";
		prob = 0;
		code = "";
		left = null;
		right = null;
		next = null;
	}

	public Node(String ch, double prob){
		this.ch = ch;
		this.prob = prob;
		code = "";
		left = null;
		right = null;
		next = null;
	}
	public String printNode(){
		String toReturn = "";
	    toReturn = toReturn + "(" + ch + ", " + prob + ", ";
	    if (next == null){
	        toReturn = toReturn + "NULL)-->NULL";
	    }
	    else{
	        toReturn = toReturn + next.ch + ")";
	    }
	    return toReturn;
	}

	public String getCh(){
		return ch;
	}

	public double getProb(){
		return prob;
	}

	public String getCode(){
		return code;
	}

	public Node getRight(){
		return right;
	}

	public Node getLeft(){
		return left;
	}

	public Node getNext(){
		return next;
	}

	public void setCh(String ch){
		this.ch = ch;
	}

	public void setProb(double prob){
		this.prob = prob;
	}

	public void setCode(String code){
		this.code = code;
	}

	public void setRight(Node right){
		this.right = right;
	}

	public void setLeft(Node left){
		this.left = left;
	}

	public void setNext(Node next){
		this.next = next;
	}
}
