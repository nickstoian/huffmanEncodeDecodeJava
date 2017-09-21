//Nicolas Stoian

public class EntropyTable {

	private String[] chArray;
	private double[] probArray;
	private String[] codeArray;
	private int[] numBitsArray;
	private double[] entropyArray;

	public EntropyTable(LinkedList workspaceList, int[] size){
		chArray = new String [size[0] + 1];
	    probArray = new double [size[0] + 1];
	    codeArray = new String [size[0] + 1];
	    numBitsArray = new int [size[0] + 1];
	    entropyArray = new double [size[0] + 1];
	    int i = 0;
	    Node walker = workspaceList.getListHead();
	    walker = walker.getNext();
	    while(walker.getNext() != null){
	        if(walker.getCh().length() == 1){
	            chArray[i] = walker.getCh();
	            probArray[i] = walker.getProb();
	            codeArray[i] = walker.getCode();
	            numBitsArray[i] = walker.getCode().length();
	            entropyArray[i] = walker.getProb() * walker.getCode().length();
	            i++;
	        }
	        walker = walker.getNext();
	    }
	}

	public String printEntropyTable(int[] size){
		String toReturn = "";
	    String line = String.format("%-8s%-15s%-15s%-8s%-15s", "Char","Prob","Code","#Bits","Entropy");
	    toReturn += line + System.getProperty("line.separator");
	    toReturn = toReturn + "=====================================================" + System.getProperty("line.separator");
	    for(int i = 0; i < size[0]; i++){
	    	line = String.format("%-8s%-15f%-15s%-8d%-15f", chArray[i],probArray[i],codeArray[i],numBitsArray[i],entropyArray[i]);
	    	toReturn += line + System.getProperty("line.separator");
	    }
	    return toReturn;
	}

	public String[] getChArray(){
		return chArray;
	}

	public String[] getCodeArray(){
		return codeArray;
	}

}
