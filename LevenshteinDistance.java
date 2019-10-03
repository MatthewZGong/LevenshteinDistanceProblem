
//					 if(dictionary.containsKey(edit2)) {
//							GraphNode tC = dictionary.get(edit2);
//							tC.addNeighbors(currentNode);
//							currentNode.addNeighbors(tC);
//					 }
//			 for(int i =0; i<current.length();i++) {
//				 for(int j =0; j<26;j++) {
//					 String edit = current.substring(0,i)+(char)(97+j)+current.substring(i+1);
//					 if(dictionary.containsKey(edit)) {
//						GraphNode tC = dictionary.get(edit);
//						tC.addNeighbors(currentNode);
//						currentNode.addNeighbors(tC);
//					 }
//		
//			  }
//			dictionary.put(current,currentNode);
//			}

import java.util.*;
import java.io.*;

public class LevenshteinDistance {
	public static void main(String[] args) throws IOException {
		File file = new File("dictionary words.txt");
		FileInputStream Fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(Fis);
		DataInputStream dis = dis = new DataInputStream(bis);
		String current = "hello";
		HashMap<String, GraphNode> dictionary = new HashMap<String, GraphNode>();
		int max = 0;
		long startTime = System.nanoTime();
		int[] allWordLengths = new int[29];
		while (dis.available() != 0) {
			current = dis.readLine();
			//allWordLengths[current.length()] +=1;
			if (current.length() > max) {
				System.out.println(current);
				max = current.length();
			}
			GraphNode currentNode = new GraphNode(current);
			for (int i = 0; i < current.length(); i++) {
				String edit3 = current.substring(0, i) + current.substring(i + 1);
				if (dictionary.containsKey(edit3)) {
					GraphNode tC = dictionary.get(edit3);
					tC.addNeighbors(currentNode);
					currentNode.addNeighbors(tC);
				}
				for (int j = 0; j < 26; j++) {
					String edit = current.substring(0, i) + (char) (97 + j) + current.substring(i + 1);
					String edit2 = current.substring(0, i + 1) + (char) (97 + j) + current.substring(i + 1);
					if (dictionary.containsKey(edit)) {
						GraphNode tC = dictionary.get(edit);
						tC.addNeighbors(currentNode);
						currentNode.addNeighbors(tC);
					}
					if (dictionary.containsKey(edit2)) {
						GraphNode tC = dictionary.get(edit2);
						tC.addNeighbors(currentNode);
						currentNode.addNeighbors(tC);
					}
				}
				// dictionary.put(current, currentNode);
			}

			dictionary.put(current, currentNode);

		}
//		for(int i = 0; i< allWordLengths.length; i++) {
//			System.out.println(i+": "+allWordLengths[i]);
//		}
		Scanner s = new Scanner(System.in);
		boolean end = false;
		long endTime = System.nanoTime();
		System.out.println((endTime - startTime) / 1000000 + " ms");
//		for (GraphNode g : dictionary.get("aah").getNeighbors()) {
//			System.out.println(g.getValue());
//		}
		while(end == false) {
			boolean inDict = false;
			String startWord = null;
			while(inDict == false) {
			System.out.print("Start word: ");
			startWord = s.nextLine();
				if(dictionary.containsKey(startWord)) {
					inDict = true;
				}else {
					System.out.println();
					System.out.println("not found in dictionary try again");
				}
			}
			System.out.println();
			String endWord = null;
			inDict = false;
			while(inDict == false) {
				System.out.print("End word: ");
				endWord = s.nextLine();
					if(dictionary.containsKey(endWord)) {
						inDict = true;
					}else {
						System.out.println();
						System.out.println("not found in dictionary try again");
					}
				}
			System.out.println();
			HashSet<String> visited = new HashSet<String>();
		
			GraphNode startNode = dictionary.get(startWord);
			startNode.setDistance(0);
			boolean found = false;
			int distance = 0;
			Deque<iteration> bfs = new ArrayDeque<iteration>();
			bfs.add(new iteration(0, startNode));
			startTime = System.nanoTime();
			GraphNode endNode = null;
			ArrayList<GraphNode> pastNodes = null;
			while (bfs.isEmpty() == false && found == false) {
				iteration cIteration = bfs.poll();
				GraphNode cGraphNode = cIteration.getGraphNode();

				for (GraphNode GN : cGraphNode.getNeighbors()) {
					if (GN.getValue().equals(endWord)) {
						distance = cIteration.getDistance()+ 1;
						found = true;
						endNode = GN;
						cIteration.addPostNodes(cGraphNode);
						pastNodes = cIteration.getPast();
						break;
					}
					if (visited.contains(GN.getValue()) == false) {
						iteration newIteration = new iteration(cIteration.getDistance() + 1, GN);
						newIteration.addPostNodes(cIteration.getPast());
						newIteration.addPostNodes(cIteration.getGraphNode());
						bfs.add(newIteration);
					}
					visited.add(GN.getValue());
				}
	
			}
	
			endTime = System.nanoTime();
	//		for(GraphNode t: dictionary.get("dog").getNeighbors()) {
	//		System.out.println(t.getValue());
	//	}	
			if(found == false) {
				System.out.println("no connections");
				System.out.println();
			}else {
			for (GraphNode t : pastNodes) {
				System.out.print(t.getValue() + "-->");
			}
			System.out.println(endNode.getValue());
			System.out.println(distance);
	//		 System.out.println(lastIteration.past);
			System.out.println((endTime - startTime) / 1000000 + " ms");
	//		 System.out.println((endTime-startTime)+" ns");
			}
			System.out.print("Continue?(yes or no):");
			String continueW = s.nextLine();
			System.out.println();
			
			if(continueW.equals("no")) {
				end = true;
			}
		}

	}
}

class iteration {
	private ArrayList<GraphNode> past;
	private int distance;
	private GraphNode current;

	iteration(int distance, GraphNode t) {
		this.current = t;
		this.distance = distance;
		past = new ArrayList<GraphNode>();
	}

	iteration(int distance, GraphNode t, ArrayList<GraphNode> past) {
		this.current = t;
		this.distance = distance;
		this.past = new ArrayList<GraphNode>();
		this.past.addAll(past);
	}

	public ArrayList<GraphNode> getPast() {
		return this.past;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void addPostNodes(ArrayList<GraphNode> t) {
		this.past.addAll(t);
	}

	public void addPostNodes(GraphNode t) {
		this.past.add(t);
	}

	public GraphNode getGraphNode() {
		return this.current;
	}
}

class GraphNode {
	private HashSet<GraphNode> neighbors;
	private String value;
	private int distance;

	GraphNode(String value) {
		this.value = value;
		this.neighbors = new HashSet<GraphNode>();
	}

	public String getValue() {
		return this.value;
	}

	public void addNeighbors(GraphNode t) {
		this.neighbors.add(t);
	}

	public HashSet<GraphNode> getNeighbors() {
		return this.neighbors;
	}

	public boolean equals(GraphNode t) {
		return this.value.equals(t.getValue());
	}

	public void setDistance(int a) {
		this.distance = a;
	}

	public int getDistance() {
		return this.distance;
	}
}
