//Nicolas Stoian
//This program needs 6 command line arguments
//args[0] "input1" for Huffman text probability
//args[1] "input2" for Huffman encode data
//args[2] "output1" for char-prob pairs
//args[3] "output2" for linked list, binary tree, and entropy table
//args[4] "output3" for encoded data
//args[5] "output4" for decoded data

import java.io.*;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class HuffmanEncodeDecode{

	public static void main(String args[]){
		try{
			BufferedReader inFile = new BufferedReader(new FileReader(args[0]));
			PrintWriter outFile1 = new PrintWriter(new FileWriter(args[2]));
			ComputeHistogram(inFile, outFile1);
			inFile.close();
			outFile1.close();
			Scanner inFileHist = new Scanner(new FileReader(args[2]));
			PrintWriter outFile2 = new PrintWriter(new FileWriter(args[3]));
			LinkedList  workspaceList = new LinkedList();
			int[] numChars = new int[1];
			numChars[0] = 0;
			HuffmanLinkedList(workspaceList, numChars, inFileHist, outFile2);
			outFile2.println(workspaceList.printList());
			outFile2.println();
			inFileHist.close();
			Node treeRoot = HuffmanBinaryTree(workspaceList, outFile2);
			HuffmanCode(treeRoot, "");
			outFile2.println(workspaceList.printList());
			outFile2.println();
			EntropyTable workspaceTable = new EntropyTable(workspaceList, numChars);
			outFile2.println(workspaceTable.printEntropyTable(numChars));
			outFile2.println();
			outFile2.close();
			BufferedReader inFile2 = new BufferedReader(new FileReader(args[1]));
			PrintWriter outFile3 = new PrintWriter(new FileWriter(args[4]));
			EncodeFile(workspaceTable, numChars, inFile2, outFile3);
			inFile2.close();
			outFile3.close();
			BufferedReader inFileDecode = new BufferedReader(new FileReader(args[4]));
			PrintWriter outFile4 = new PrintWriter(new FileWriter(args[5]));
			DecodeFile(workspaceTable, numChars, inFileDecode, outFile4);
			inFileDecode.close();
			outFile4.close();
		}
		catch(NoSuchElementException e){
			System.err.println("Error in input file format, check the input file and try again.");
            return;
		}
		catch(FileNotFoundException e){
			System.err.println("File not found exception, check arguements and try again.");
            return;
		}
		catch(IOException e){
			System.err.println("IO exception, check arguements and try again.");
            return;
		}
	}

	public static void ComputeHistogram(BufferedReader inFile, PrintWriter outFile){
		try{
			int size = 256;
			int[] countArray = new int [size];
			for(int i = 0; i < size; i++){
				countArray[i] = 0;
			}
			int charTotal = 0;
			int index;
			while((index = inFile.read()) != -1){
				if((char)index == '—'){
					index = (int)'ú';
				}
				countArray[index]++;
				charTotal++;
			}
			index = 0;
			while(index < size){
				if(countArray[index] > 0){
					double prob = ((double)countArray[index]/charTotal)*100;
					if((char)index == '\n'){
						outFile.println("ñ" + " " + prob);
					}
					else if((char)index == '\r'){
						outFile.println("ò" + " " + prob);
					}
					else if((char)index == ' '){
						outFile.println("è" + " " + prob);
					}
					else{
						outFile.println((char)index + " " + prob);
					}
				}
				index++;
			}
		}
		catch(IOException e){
			System.err.println("IO exception, check arguements and try again.");
            return;
		}
	}

	public static void HuffmanLinkedList(LinkedList workspaceList, int[] numChars, Scanner inFile, PrintWriter outFile){
		//int iterationNum = 0;
		//outFile.println("Iteration " + iterationNum + ": " + workspaceList.printList());
		//outFile.println();
		//System.out.print("Iteration " + iterationNum++ + ": " + workspaceList.printList() + "\n\n");
		while(inFile.hasNext()){
			String charItem;
			charItem = inFile.next();
			double probItem;
			probItem = inFile.nextDouble();
			Node inNode = new Node(charItem, probItem);
			workspaceList.insertNode(inNode);
			numChars[0]++;
			//outFile.println("Iteration " + iterationNum + ": " + workspaceList.printList());
			//outFile.println();
			//System.out.print("Iteration " + iterationNum++ + ": " + workspaceList.printList() + "\n\n");
		}
		//outFile2.print(workspaceList.printSortedList());
	}

	public static Node HuffmanBinaryTree(LinkedList workspaceList, PrintWriter outFile){
		//int iterationNum = 0;
		Node oldListHead = new Node(); //Step 3.1
	    oldListHead.setNext(workspaceList.getListHead().getNext());
	    //outFile.println("Iteration " + iterationNum + ": " + workspaceList.printList());
	    //outFile.println();
	    //System.out.print("Iteration " + iterationNum++ + ": " + workspaceList.printList() + "\n\n");
	    while (workspaceList.getListHead().getNext().getNext() != null){	//Step 3.8
	        Node newNode = new Node();	//Step 3.2
	        newNode.setCh(workspaceList.getListHead().getNext().getCh() + workspaceList.getListHead().getNext().getNext().getCh());
	        newNode.setProb(workspaceList.getListHead().getNext().getProb() + workspaceList.getListHead().getNext().getNext().getProb());
	        newNode.setLeft(workspaceList.getListHead().getNext());
	        newNode.setRight(workspaceList.getListHead().getNext().getNext());
	        workspaceList.getListHead().setNext(workspaceList.getListHead().getNext().getNext().getNext());
	        workspaceList.insertNode(newNode);	//Step 3.3-3.6
	        //outFile.println("Iteration " + iterationNum + ": " + workspaceList.printList()); //Step 3.7
	        //outFile.println();
	        //System.out.print("Iteration " + iterationNum++ + ": " + workspaceList.printList() + "\n\n");
	    }
	    outFile.println(workspaceList.printList());
		outFile.println();
	    Node root = workspaceList.getListHead().getNext();	//Step 3.9
	    workspaceList.getListHead().setNext(oldListHead.getNext());
	    return root;
	}

	public static void HuffmanCode(Node treeRoot, String code){
		Node walker = treeRoot; //Step 4.1
	    if(walker == null){ //Step 4.2
	        return;
	    }
	    if(walker.getCh().length() == 1){  //Step 4.3
	        walker.setCode(code);
	        return;
	    }
	    HuffmanCode(walker.getLeft(), code + "0"); //Step 4.4
	    HuffmanCode(walker.getRight(), code + "1");
	}

	public static void EncodeFile(EntropyTable workspaceTable, int[] size, BufferedReader inFile, PrintWriter outFile){
		try{
			String charCmp;
			String[] chArray = workspaceTable.getChArray();
			String[] codeArray = workspaceTable.getCodeArray();
			int charItem;
			while((charItem = inFile.read()) != -1){
				if((char)charItem == '\n'){
					charCmp = "ñ";
				}
				else if((char)charItem == '\r'){
					charCmp = "ò";
				}
				else if((char)charItem == ' '){
					charCmp = "è";
				}
				else if((char)charItem == '—'){
					charCmp = "ú";
				}
				else{
					charCmp = String.valueOf((char)charItem);
				}
				for(int i = 0; i < size[0]; i++){
					if(charCmp.equals(chArray[i])){
						outFile.print(codeArray[i]);
					}
				}
			}
		}
		catch(IOException e){
			System.err.println("IO exception, check arguements and try again.");
            return;
		}
	}

	public static void DecodeFile(EntropyTable workspaceTable, int[] size, BufferedReader inFile, PrintWriter outFile){
		try{
			String charCmp = "";
			String[] chArray = workspaceTable.getChArray();
			String[] codeArray = workspaceTable.getCodeArray();
			int charItem;
			while((charItem = inFile.read()) != -1){
				charCmp += (char)charItem;
				for(int i = 0; i < size[0]; i++){
					if(charCmp.equals(codeArray[i])){
						if(chArray[i].equals("è")){
							outFile.print(" ");
						}
						else if(chArray[i].equals("ñ")){
							outFile.print("\n");
						}
						else if(chArray[i].equals("ò")){
							outFile.print("\r");
						}
						else if(chArray[i].equals("ú")){
							outFile.print("—");
						}
						else{
							outFile.print(chArray[i]);
						}
						charCmp = "";
					}
				}
			}
		}
		catch(IOException e){
			System.err.println("IO exception, check arguements and try again.");
            return;
		}
	}
}


